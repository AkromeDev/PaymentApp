package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyTransaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MyTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyTransactionRepository extends JpaRepository<MyTransaction, Long> {
}
