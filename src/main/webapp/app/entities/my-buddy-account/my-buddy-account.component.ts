import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMyBuddyAccount } from '@/shared/model/my-buddy-account.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import MyBuddyAccountService from './my-buddy-account.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MyBuddyAccount extends mixins(AlertMixin) {
  @Inject('myBuddyAccountService') private myBuddyAccountService: () => MyBuddyAccountService;
  private removeId: number = null;

  public myBuddyAccounts: IMyBuddyAccount[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMyBuddyAccounts();
  }

  public clear(): void {
    this.retrieveAllMyBuddyAccounts();
  }

  public retrieveAllMyBuddyAccounts(): void {
    this.isFetching = true;

    this.myBuddyAccountService()
      .retrieve()
      .then(
        res => {
          this.myBuddyAccounts = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IMyBuddyAccount): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMyBuddyAccount(): void {
    this.myBuddyAccountService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A MyBuddyAccount is deleted with identifier ' + this.removeId;
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllMyBuddyAccounts();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
