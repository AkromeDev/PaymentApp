<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="contactRelationship">
                <h2 class="jh-entity-heading"><span>ContactRelationship</span> {{contactRelationship.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span>User Id 1</span>
                    </dt>
                    <dd>
                        <span>{{contactRelationship.userId1}}</span>
                    </dd>
                    <dt>
                        <span>User Id 2</span>
                    </dt>
                    <dd>
                        <span>{{contactRelationship.userId2}}</span>
                    </dd>
                    <dt>
                        <span>User</span>
                    </dt>
                    <dd>
                        <span v-for="(user, i) in contactRelationship.users" :key="user.id">{{i > 0 ? ', ' : ''}}
                            {{user.id}}
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
                </button>
                <router-link v-if="contactRelationship.id" :to="{name: 'ContactRelationshipEdit', params: {contactRelationshipId: contactRelationship.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./contact-relationship-details.component.ts">
</script>
