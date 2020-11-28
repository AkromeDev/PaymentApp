<template>
    <div>
        <h2 id="page-heading">
            <span id="my-transaction-heading">My Transactions</span>
            <router-link :to="{name: 'MyTransactionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-my-transaction">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span >
                    Create a new My Transaction
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
        <div class="alert alert-warning" v-if="!isFetching && myTransactions && myTransactions.length === 0">
            <span>No myTransactions found</span>
        </div>
        <div class="table-responsive" v-if="myTransactions && myTransactions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>ID</span></th>
                    <th><span>Date</span></th>
                    <th><span>Amount</span></th>
                    <th><span>Description</span></th>
                    <th><span>Contact Relationship</span></th>
                    <th><span>My Buddy Account</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="myTransaction in myTransactions"
                    :key="myTransaction.id">
                    <td>
                        <router-link :to="{name: 'MyTransactionView', params: {myTransactionId: myTransaction.id}}">{{myTransaction.id}}</router-link>
                    </td>
                    <td>{{myTransaction.date | formatDate}}</td>
                    <td>{{myTransaction.amount}}</td>
                    <td>{{myTransaction.description}}</td>
                    <td>
                        <div v-if="myTransaction.contactRelationship">
                            <router-link :to="{name: 'ContactRelationshipView', params: {contactRelationshipId: myTransaction.contactRelationship.id}}">{{myTransaction.contactRelationship.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="myTransaction.myBuddyAccount">
                            <router-link :to="{name: 'MyBuddyAccountView', params: {myBuddyAccountId: myTransaction.myBuddyAccount.id}}">{{myTransaction.myBuddyAccount.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MyTransactionView', params: {myTransactionId: myTransaction.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline">View</span>
                            </router-link>
                            <router-link :to="{name: 'MyTransactionEdit', params: {myTransactionId: myTransaction.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(myTransaction)"
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
            <span slot="modal-title"><span id="payMyBuddyApp.myTransaction.delete.question">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-myTransaction-heading">Are you sure you want to delete this My Transaction?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-myTransaction" v-on:click="removeMyTransaction()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./my-transaction.component.ts">
</script>
