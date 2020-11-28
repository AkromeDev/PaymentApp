package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A MyTransaction.
 */
@Entity
@Table(name = "my_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private ContactRelationship contactRelationship;

    @ManyToOne
    @JsonIgnoreProperties(value = "myTransactions", allowSetters = true)
    private MyBuddyAccount myBuddyAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public MyTransaction date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public MyTransaction amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public MyTransaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContactRelationship getContactRelationship() {
        return contactRelationship;
    }

    public MyTransaction contactRelationship(ContactRelationship contactRelationship) {
        this.contactRelationship = contactRelationship;
        return this;
    }

    public void setContactRelationship(ContactRelationship contactRelationship) {
        this.contactRelationship = contactRelationship;
    }

    public MyBuddyAccount getMyBuddyAccount() {
        return myBuddyAccount;
    }

    public MyTransaction myBuddyAccount(MyBuddyAccount myBuddyAccount) {
        this.myBuddyAccount = myBuddyAccount;
        return this;
    }

    public void setMyBuddyAccount(MyBuddyAccount myBuddyAccount) {
        this.myBuddyAccount = myBuddyAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyTransaction)) {
            return false;
        }
        return id != null && id.equals(((MyTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyTransaction{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
