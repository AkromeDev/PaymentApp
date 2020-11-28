/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import MyBuddyAccountUpdateComponent from '@/entities/my-buddy-account/my-buddy-account-update.vue';
import MyBuddyAccountClass from '@/entities/my-buddy-account/my-buddy-account-update.component';
import MyBuddyAccountService from '@/entities/my-buddy-account/my-buddy-account.service';

import UserService from '@/admin/user-management/user-management.service';

import BankAccountService from '@/entities/bank-account/bank-account.service';

import MyTransactionService from '@/entities/my-transaction/my-transaction.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('MyBuddyAccount Management Update Component', () => {
    let wrapper: Wrapper<MyBuddyAccountClass>;
    let comp: MyBuddyAccountClass;
    let myBuddyAccountServiceStub: SinonStubbedInstance<MyBuddyAccountService>;

    beforeEach(() => {
      myBuddyAccountServiceStub = sinon.createStubInstance<MyBuddyAccountService>(MyBuddyAccountService);

      wrapper = shallowMount<MyBuddyAccountClass>(MyBuddyAccountUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          myBuddyAccountService: () => myBuddyAccountServiceStub,

          userService: () => new UserService(),

          bankAccountService: () => new BankAccountService(),

          myTransactionService: () => new MyTransactionService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.myBuddyAccount = entity;
        myBuddyAccountServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(myBuddyAccountServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.myBuddyAccount = entity;
        myBuddyAccountServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(myBuddyAccountServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
