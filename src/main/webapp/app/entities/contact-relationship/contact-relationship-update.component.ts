import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IContactRelationship, ContactRelationship } from '@/shared/model/contact-relationship.model';
import ContactRelationshipService from './contact-relationship.service';

const validations: any = {
  contactRelationship: {
    userId1: {},
    userId2: {},
  },
};

@Component({
  validations,
})
export default class ContactRelationshipUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('contactRelationshipService') private contactRelationshipService: () => ContactRelationshipService;
  public contactRelationship: IContactRelationship = new ContactRelationship();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.contactRelationshipId) {
        vm.retrieveContactRelationship(to.params.contactRelationshipId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.contactRelationship.users = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.contactRelationship.id) {
      this.contactRelationshipService()
        .update(this.contactRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A ContactRelationship is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.contactRelationshipService()
        .create(this.contactRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A ContactRelationship is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveContactRelationship(contactRelationshipId): void {
    this.contactRelationshipService()
      .find(contactRelationshipId)
      .then(res => {
        this.contactRelationship = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}