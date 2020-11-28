import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import BankAccountService from '../bank-account/bank-account.service';
import { IBankAccount } from '@/shared/model/bank-account.model';

import MyTransactionService from '../my-transaction/my-transaction.service';
import { IMyTransaction } from '@/shared/model/my-transaction.model';

import AlertService from '@/shared/alert/alert.service';
import { IMyBuddyAccount, MyBuddyAccount } from '@/shared/model/my-buddy-account.model';
import MyBuddyAccountService from './my-buddy-account.service';

const validations: any = {
  myBuddyAccount: {
    balance: {},
    profilePicture: {},
  },
};

@Component({
  validations,
})
export default class MyBuddyAccountUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('myBuddyAccountService') private myBuddyAccountService: () => MyBuddyAccountService;
  public myBuddyAccount: IMyBuddyAccount = new MyBuddyAccount();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;

  public bankAccounts: IBankAccount[] = [];

  @Inject('myTransactionService') private myTransactionService: () => MyTransactionService;

  public myTransactions: IMyTransaction[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.myBuddyAccountId) {
        vm.retrieveMyBuddyAccount(to.params.myBuddyAccountId);
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
    if (this.myBuddyAccount.id) {
      this.myBuddyAccountService()
        .update(this.myBuddyAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MyBuddyAccount is updated with identifier ' + param.id;
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.myBuddyAccountService()
        .create(this.myBuddyAccount)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MyBuddyAccount is created with identifier ' + param.id;
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMyBuddyAccount(myBuddyAccountId): void {
    this.myBuddyAccountService()
      .find(myBuddyAccountId)
      .then(res => {
        this.myBuddyAccount = res;
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
    this.bankAccountService()
      .retrieve()
      .then(res => {
        this.bankAccounts = res.data;
      });
    this.myTransactionService()
      .retrieve()
      .then(res => {
        this.myTransactions = res.data;
      });
  }
}
