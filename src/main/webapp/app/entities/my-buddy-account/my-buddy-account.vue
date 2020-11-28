<template>
    <div>
        <h2 id="page-heading">
            <span id="my-buddy-account-heading">My Buddy Accounts</span>
            <router-link :to="{name: 'MyBuddyAccountCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-my-buddy-account">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new My Buddy Account
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && myBuddyAccounts && myBuddyAccounts.length === 0">
            <span>No myBuddyAccounts found</span>
        </div>
        <div class="table-responsive" v-if="myBuddyAccounts && myBuddyAccounts.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>Balance</span></th>
                    <th><span>Profile Picture</span></th>
                    <th><span>User</span></th>
                    <th><span>Bank Account</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="myBuddyAccount in myBuddyAccounts"
                    :key="myBuddyAccount.id">
                    <td>
                        <router-link :to="{name: 'MyBuddyAccountView', params: {myBuddyAccountId: myBuddyAccount.id}}">{{myBuddyAccount.id}}</router-link>
                    </td>
                    <td>{{myBuddyAccount.balance}}</td>
                    <td>{{myBuddyAccount.profilePicture}}</td>
                    <td>
                        {{myBuddyAccount.user ? myBuddyAccount.user.id : ''}}
                    </td>
                    <td>
                        <div v-if="myBuddyAccount.bankAccount">
                            <router-link :to="{name: 'BankAccountView', params: {bankAccountId: myBuddyAccount.bankAccount.id}}">{{myBuddyAccount.bankAccount.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MyBuddyAccountView', params: {myBuddyAccountId: myBuddyAccount.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'MyBuddyAccountEdit', params: {myBuddyAccountId: myBuddyAccount.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(myBuddyAccount)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="payMyBuddyApp.myBuddyAccount.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-myBuddyAccount-heading">Are you sure you want to delete this My Buddy Account?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-myBuddyAccount" v-on:click="removeMyBuddyAccount()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./my-buddy-account.component.ts">
</script>
