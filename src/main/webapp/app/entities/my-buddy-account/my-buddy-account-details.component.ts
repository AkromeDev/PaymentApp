import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMyBuddyAccount } from '@/shared/model/my-buddy-account.model';
import MyBuddyAccountService from './my-buddy-account.service';

@Component
export default class MyBuddyAccountDetails extends Vue {
  @Inject('myBuddyAccountService') private myBuddyAccountService: () => MyBuddyAccountService;
  public myBuddyAccount: IMyBuddyAccount = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.myBuddyAccountId) {
        vm.retrieveMyBuddyAccount(to.params.myBuddyAccountId);
      }
    });
  }

  public retrieveMyBuddyAccount(myBuddyAccountId) {
    this.myBuddyAccountService()
      .find(myBuddyAccountId)
      .then(res => {
        this.myBuddyAccount = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
