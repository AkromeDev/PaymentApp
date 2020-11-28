<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="payMyBuddyApp.myTransaction.home.createOrEditLabel">Create or edit a MyTransaction</h2>
                <div>
                    <div class="form-group" v-if="myTransaction.id">
                        <label for="id">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="myTransaction.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-transaction-date">Date</label>
                        <div class="d-flex">
                            <input id="my-transaction-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.myTransaction.date.$invalid, 'invalid': $v.myTransaction.date.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.myTransaction.date.$model)"
                            @change="updateZonedDateTimeField('date', $event)"/>
                        </div>
                        <div v-if="$v.myTransaction.date.$anyDirty && $v.myTransaction.date.$invalid">
                            <small class="form-text text-danger" v-if="!$v.myTransaction.date.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.myTransaction.date.ZonedDateTimelocal">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-transaction-amount">Amount</label>
                        <input type="number" class="form-control" name="amount" id="my-transaction-amount"
                            :class="{'valid': !$v.myTransaction.amount.$invalid, 'invalid': $v.myTransaction.amount.$invalid }" v-model.number="$v.myTransaction.amount.$model"  required/>
                        <div v-if="$v.myTransaction.amount.$anyDirty && $v.myTransaction.amount.$invalid">
                            <small class="form-text text-danger" v-if="!$v.myTransaction.amount.required">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.myTransaction.amount.numeric">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-transaction-description">Description</label>
                        <input type="text" class="form-control" name="description" id="my-transaction-description"
                            :class="{'valid': !$v.myTransaction.description.$invalid, 'invalid': $v.myTransaction.description.$invalid }" v-model="$v.myTransaction.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-transaction-contactRelationship">Contact Relationship</label>
                        <select class="form-control" id="my-transaction-contactRelationship" name="contactRelationship" v-model="myTransaction.contactRelationship">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="myTransaction.contactRelationship && contactRelationshipOption.id === myTransaction.contactRelationship.id ? myTransaction.contactRelationship : contactRelationshipOption" v-for="contactRelationshipOption in contactRelationships" :key="contactRelationshipOption.id">{{contactRelationshipOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" for="my-transaction-myBuddyAccount">My Buddy Account</label>
                        <select class="form-control" id="my-transaction-myBuddyAccount" name="myBuddyAccount" v-model="myTransaction.myBuddyAccount">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="myTransaction.myBuddyAccount && myBuddyAccountOption.id === myTransaction.myBuddyAccount.id ? myTransaction.myBuddyAccount : myBuddyAccountOption" v-for="myBuddyAccountOption in myBuddyAccounts" :key="myBuddyAccountOption.id">{{myBuddyAccountOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.myTransaction.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./my-transaction-update.component.ts">
</script>
