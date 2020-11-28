import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import ContactRelationshipService from '../contact-relationship/contact-relationship.service';
import { IContactRelationship } from '@/shared/model/contact-relationship.model';

import MyBuddyAccountService from '../my-buddy-account/my-buddy-account.service';
import { IMyBuddyAccount } from '@/shared/model/my-buddy-account.model';

import AlertService from '@/shared/alert/alert.service';
import { IMyTransaction, MyTransaction } from '@/shared/model/my-transaction.model';
import MyTransactionService from './my-transaction.service';

const validations: any = {
  myTransaction: {
    date: {
      required,
    },
    amount: {
      required,
      numeric,
    },
    description: {},
  },
};

@Component({
  validations,
})
export default class MyTransactionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('myTransactionService') private myTransactionService: () => MyTransactionService;
  public myTransaction: IMyTransaction = new MyTransaction();

  @Inject('contactRelationshipService') private contactRelationshipService: () => ContactRelationshipService;

  public contactRelationships: IContactRelationship[] = [];

  @Inject('myBuddyAccountService') private myBuddyAccountService: () => MyBuddyAccountService;

  public myBuddyAccounts: IMyBuddyAccount[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.myTransactionId) {
        vm.retrieveMyTransaction(to.params.myTransactionId);
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
  }

  public save(): void {
    this.isSaving = true;
    if (this.myTransaction.id) {
      this.myTransactionService()
        .update(this.myTransaction)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MyTransaction is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.myTransactionService()
        .create(this.myTransaction)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MyTransaction is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.myTransaction[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.myTransaction[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.myTransaction[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.myTransaction[field] = null;
    }
  }

  public retrieveMyTransaction(myTransactionId): void {
    this.myTransactionService()
      .find(myTransactionId)
      .then(res => {
        res.date = new Date(res.date);
        this.myTransaction = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.contactRelationshipService()
      .retrieve()
      .then(res => {
        this.contactRelationships = res.data;
      });
    this.myBuddyAccountService()
      .retrieve()
      .then(res => {
        this.myBuddyAccounts = res.data;
      });
  }
}
