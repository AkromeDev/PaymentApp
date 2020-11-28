<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="payMyBuddyApp.myBuddyAccount.home.createOrEditLabel">Create or edit a MyBuddyAccount</h2>
                <div>
                    <div class="form-group" v-if="myBuddyAccount.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="myBuddyAccount.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-buddy-account-balance">Balance</label>
                        <input type="number" class="form-control" name="balance" id="my-buddy-account-balance"
                            :class="{'valid': !$v.myBuddyAccount.balance.$invalid, 'invalid': $v.myBuddyAccount.balance.$invalid }" v-model.number="$v.myBuddyAccount.balance.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-buddy-account-profilePicture">Profile Picture</label>
                        <input type="text" class="form-control" name="profilePicture" id="my-buddy-account-profilePicture"
                            :class="{'valid': !$v.myBuddyAccount.profilePicture.$invalid, 'invalid': $v.myBuddyAccount.profilePicture.$invalid }" v-model="$v.myBuddyAccount.profilePicture.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-buddy-account-user">User</label>
                        <select class="form-control" id="my-buddy-account-user" name="user" v-model="myBuddyAccount.user">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="myBuddyAccount.user && userOption.id === myBuddyAccount.user.id ? myBuddyAccount.user : userOption" v-for="userOption in users" :key="userOption.id">{{userOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-buddy-account-bankAccount">Bank Account</label>
                        <select class="form-control" id="my-buddy-account-bankAccount" name="bankAccount" v-model="myBuddyAccount.bankAccount">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="myBuddyAccount.bankAccount && bankAccountOption.id === myBuddyAccount.bankAccount.id ? myBuddyAccount.bankAccount : bankAccountOption" v-for="bankAccountOption in bankAccounts" :key="bankAccountOption.id">{{bankAccountOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.myBuddyAccount.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./my-buddy-account-update.component.ts">
</script>
