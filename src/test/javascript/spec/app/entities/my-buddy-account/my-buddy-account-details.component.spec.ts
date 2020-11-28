/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MyBuddyAccountDetailComponent from '@/entities/my-buddy-account/my-buddy-account-details.vue';
import MyBuddyAccountClass from '@/entities/my-buddy-account/my-buddy-account-details.component';
import MyBuddyAccountService from '@/entities/my-buddy-account/my-buddy-account.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MyBuddyAccount Management Detail Component', () => {
    let wrapper: Wrapper<MyBuddyAccountClass>;
    let comp: MyBuddyAccountClass;
    let myBuddyAccountServiceStub: SinonStubbedInstance<MyBuddyAccountService>;

    beforeEach(() => {
      myBuddyAccountServiceStub = sinon.createStubInstance<MyBuddyAccountService>(MyBuddyAccountService);

      wrapper = shallowMount<MyBuddyAccountClass>(MyBuddyAccountDetailComponent, {
        store,
        localVue,
        provide: { myBuddyAccountService: () => myBuddyAccountServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMyBuddyAccount = { id: 123 };
        myBuddyAccountServiceStub.find.resolves(foundMyBuddyAccount);

        // WHEN
        comp.retrieveMyBuddyAccount(123);
        await comp.$nextTick();

        // THEN
        expect(comp.myBuddyAccount).toBe(foundMyBuddyAccount);
      });
    });
  });
});
