package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyBuddyAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MyBuddyAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyBuddyAccountRepository extends JpaRepository<MyBuddyAccount, Long> {
}
