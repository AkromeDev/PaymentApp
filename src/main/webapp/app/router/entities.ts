import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const ContactRelationship = () => import('@/entities/contact-relationship/contact-relationship.vue');
// prettier-ignore
const ContactRelationshipUpdate = () => import('@/entities/contact-relationship/contact-relationship-update.vue');
// prettier-ignore
const ContactRelationshipDetails = () => import('@/entities/contact-relationship/contact-relationship-details.vue');
// prettier-ignore
const MyBuddyAccount = () => import('@/entities/my-buddy-account/my-buddy-account.vue');
// prettier-ignore
const MyBuddyAccountUpdate = () => import('@/entities/my-buddy-account/my-buddy-account-update.vue');
// prettier-ignore
const MyBuddyAccountDetails = () => import('@/entities/my-buddy-account/my-buddy-account-details.vue');
// prettier-ignore
const BankAccount = () => import('@/entities/bank-account/bank-account.vue');
// prettier-ignore
const BankAccountUpdate = () => import('@/entities/bank-account/bank-account-update.vue');
// prettier-ignore
const BankAccountDetails = () => import('@/entities/bank-account/bank-account-details.vue');
// prettier-ignore
const MyTransaction = () => import('@/entities/my-transaction/my-transaction.vue');
// prettier-ignore
const MyTransactionUpdate = () => import('@/entities/my-transaction/my-transaction-update.vue');
// prettier-ignore
const MyTransactionDetails = () => import('@/entities/my-transaction/my-transaction-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/contact-relationship',
    name: 'ContactRelationship',
    component: ContactRelationship,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/contact-relationship/new',
    name: 'ContactRelationshipCreate',
    component: ContactRelationshipUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/contact-relationship/:contactRelationshipId/edit',
    name: 'ContactRelationshipEdit',
    component: ContactRelationshipUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/contact-relationship/:contactRelationshipId/view',
    name: 'ContactRelationshipView',
    component: ContactRelationshipDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-buddy-account',
    name: 'MyBuddyAccount',
    component: MyBuddyAccount,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-buddy-account/new',
    name: 'MyBuddyAccountCreate',
    component: MyBuddyAccountUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-buddy-account/:myBuddyAccountId/edit',
    name: 'MyBuddyAccountEdit',
    component: MyBuddyAccountUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-buddy-account/:myBuddyAccountId/view',
    name: 'MyBuddyAccountView',
    component: MyBuddyAccountDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/bank-account',
    name: 'BankAccount',
    component: BankAccount,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/bank-account/new',
    name: 'BankAccountCreate',
    component: BankAccountUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/bank-account/:bankAccountId/edit',
    name: 'BankAccountEdit',
    component: BankAccountUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/bank-account/:bankAccountId/view',
    name: 'BankAccountView',
    component: BankAccountDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-transaction',
    name: 'MyTransaction',
    component: MyTransaction,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-transaction/new',
    name: 'MyTransactionCreate',
    component: MyTransactionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-transaction/:myTransactionId/edit',
    name: 'MyTransactionEdit',
    component: MyTransactionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/my-transaction/:myTransactionId/view',
    name: 'MyTransactionView',
    component: MyTransactionDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
