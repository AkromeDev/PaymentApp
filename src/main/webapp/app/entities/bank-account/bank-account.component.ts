import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBankAccount } from '@/shared/model/bank-account.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import BankAccountService from './bank-account.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class BankAccount extends mixins(AlertMixin) {
  @Inject('bankAccountService') private bankAccountService: () => BankAccountService;
  private removeId: number = null;

  public bankAccounts: IBankAccount[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBankAccounts();
  }

  public clear(): void {
    this.retrieveAllBankAccounts();
  }

  public retrieveAllBankAccounts(): void {
    this.isFetching = true;

    this.bankAccountService()
      .retrieve()
      .then(
        res => {
          this.bankAccounts = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IBankAccount): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeBankAccount(): void {
    this.bankAccountService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A BankAccount is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllBankAccounts();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
