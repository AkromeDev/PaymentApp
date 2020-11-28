package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ContactRelationship.
 */
@Entity
@Table(name = "contact_relationship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id_1")
    private Long userId1;

    @Column(name = "user_id_2")
    private Long userId2;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "contact_relationship_user",
               joinColumns = @JoinColumn(name = "contact_relationship_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId1() {
        return userId1;
    }

    public ContactRelationship userId1(Long userId1) {
        this.userId1 = userId1;
        return this;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public ContactRelationship userId2(Long userId2) {
        this.userId2 = userId2;
        return this;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    public Set<User> getUsers() {
        return users;
    }

    public ContactRelationship users(Set<User> users) {
        this.users = users;
        return this;
    }

    public ContactRelationship addUser(User user) {
        this.users.add(user);
        return this;
    }

    public ContactRelationship removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactRelationship)) {
            return false;
        }
        return id != null && id.equals(((ContactRelationship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactRelationship{" +
            "id=" + getId() +
            ", userId1=" + getUserId1() +
            ", userId2=" + getUserId2() +
            "}";
    }
}
