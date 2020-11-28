<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="myTransaction">
                <h2 class="jh-entity-heading"><span>MyTransaction</span> {{myTransaction.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Date</span>
                    </dt>
                    <dd>
                        <span>{{myTransaction.date | formatDate}}</span>
                    </dd>
                    <dt>
                        <span>Amount</span>
                    </dt>
                    <dd>
                        <span>{{myTransaction.amount}}</span>
                    </dd>
                    <dt>
                        <span>Description</span>
                    </dt>
                    <dd>
                        <span>{{myTransaction.description}}</span>
                    </dd>
                    <dt>
                        <span>Contact Relationship</span>
                    </dt>
                    <dd>
                        <div v-if="myTransaction.contactRelationship">
                            <router-link :to="{name: 'ContactRelationshipView', params: {contactRelationshipId: myTransaction.contactRelationship.id}}">{{myTransaction.contactRelationship.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span>My Buddy Account</span>
                    </dt>
                    <dd>
                        <div v-if="myTransaction.myBuddyAccount">
                            <router-link :to="{name: 'MyBuddyAccountView', params: {myBuddyAccountId: myTransaction.myBuddyAccount.id}}">{{myTransaction.myBuddyAccount.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="myTransaction.id" :to="{name: 'MyTransactionEdit', params: {myTransactionId: myTransaction.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./my-transaction-details.component.ts">
</script>
