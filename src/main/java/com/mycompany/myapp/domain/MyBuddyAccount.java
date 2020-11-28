package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The MyBuddyAccount entity.
 */
@ApiModel(description = "The MyBuddyAccount entity.")
@Entity
@Table(name = "my_buddy_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyBuddyAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "profile_picture")
    private String profilePicture;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "myBuddyAccount")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MyTransaction> myTransactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public MyBuddyAccount balance(Long balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public MyBuddyAccount profilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public User getUser() {
        return user;
    }

    public MyBuddyAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public MyBuddyAccount bankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Set<MyTransaction> getMyTransactions() {
        return myTransactions;
    }

    public MyBuddyAccount myTransactions(Set<MyTransaction> myTransactions) {
        this.myTransactions = myTransactions;
        return this;
    }

    public MyBuddyAccount addMyTransaction(MyTransaction myTransaction) {
        this.myTransactions.add(myTransaction);
        myTransaction.setMyBuddyAccount(this);
        return this;
    }

    public MyBuddyAccount removeMyTransaction(MyTransaction myTransaction) {
        this.myTransactions.remove(myTransaction);
        myTransaction.setMyBuddyAccount(null);
        return this;
    }

    public void setMyTransactions(Set<MyTransaction> myTransactions) {
        this.myTransactions = myTransactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyBuddyAccount)) {
            return false;
        }
        return id != null && id.equals(((MyBuddyAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyBuddyAccount{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", profilePicture='" + getProfilePicture() + "'" +
            "}";
    }
}
