import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import MyBuddyAccountService from '../my-buddy-account/my-buddy-account.service';
import { IMyBuddyAccount } from '@/shared/model/my-buddy-account.model';

import AlertService from '@/shared/alert/alert.service';
import { IBankAccount, BankAccount } from '@/shared/model/bank-account.model';
import BankAccountService from './bank-account.service';

const validations: any = {
  bankAccount: {
    name: {
      required,
    },
    iban: {
      required,
      numeric,
    },
    bic: {
      required,
      numeric,
    },
  },
};

@Component({
  validations,
})
export default class BankAccountUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;
  public bankAccount: IBankAccount = new BankAccount();

  @Inject('myBuddyAccountService') private myBuddyAccountService: () => MyBuddyAccountService;

  public myBuddyAccounts: IMyBuddyAccount[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bankAccountId) {
        vm.retrieveBankAccount(to.params.bankAccountId);
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
    if (this.bankAccount.id) {
      this.bankAccountService()
        .update(this.bankAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A BankAccount is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.bankAccountService()
        .create(this.bankAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A BankAccount is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveBankAccount(bankAccountId): void {
    this.bankAccountService()
      .find(bankAccountId)
      .then(res => {
        this.bankAccount = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.myBuddyAccountService()
      .retrieve()
      .then(res => {
        this.myBuddyAccounts = res.data;
      });
  }
}
