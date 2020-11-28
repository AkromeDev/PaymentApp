/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MyBuddyAccountComponent from '@/entities/my-buddy-account/my-buddy-account.vue';
import MyBuddyAccountClass from '@/entities/my-buddy-account/my-buddy-account.component';
import MyBuddyAccountService from '@/entities/my-buddy-account/my-buddy-account.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('MyBuddyAccount Management Component', () => {
    let wrapper: Wrapper<MyBuddyAccountClass>;
    let comp: MyBuddyAccountClass;
    let myBuddyAccountServiceStub: SinonStubbedInstance<MyBuddyAccountService>;

    beforeEach(() => {
      myBuddyAccountServiceStub = sinon.createStubInstance<MyBuddyAccountService>(MyBuddyAccountService);
      myBuddyAccountServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MyBuddyAccountClass>(MyBuddyAccountComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          myBuddyAccountService: () => myBuddyAccountServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      myBuddyAccountServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMyBuddyAccounts();
      await comp.$nextTick();

      // THEN
      expect(myBuddyAccountServiceStub.retrieve.called).toBeTruthy();
      expect(comp.myBuddyAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      myBuddyAccountServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMyBuddyAccount();
      await comp.$nextTick();

      // THEN
      expect(myBuddyAccountServiceStub.delete.called).toBeTruthy();
      expect(myBuddyAccountServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
