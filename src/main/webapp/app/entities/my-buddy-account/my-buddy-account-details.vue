<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="myBuddyAccount">
                <h2 class="jh-entity-heading"><span>MyBuddyAccount</span> {{myBuddyAccount.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>Balance</span>
                    </dt>
                    <dd>
                        <span>{{myBuddyAccount.balance}}</span>
                    </dd>
                    <dt>
                        <span>Profile Picture</span>
                    </dt>
                    <dd>
                        <span>{{myBuddyAccount.profilePicture}}</span>
                    </dd>
                    <dt>
                        <span>User</span>
                    </dt>
                    <dd>
                        {{myBuddyAccount.user ? myBuddyAccount.user.id : ''}}
                    </dd>
                    <dt>
                        <span>Bank Account</span>
                    </dt>
                    <dd>
                        <div v-if="myBuddyAccount.bankAccount">
                            <router-link :to="{name: 'BankAccountView', params: {bankAccountId: myBuddyAccount.bankAccount.id}}">{{myBuddyAccount.bankAccount.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="myBuddyAccount.id" :to="{name: 'MyBuddyAccountEdit', params: {myBuddyAccountId: myBuddyAccount.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./my-buddy-account-details.component.ts">
</script>
