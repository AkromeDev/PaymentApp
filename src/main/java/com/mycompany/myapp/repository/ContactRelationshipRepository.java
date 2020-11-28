package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContactRelationship;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ContactRelationship entity.
 */
@Repository
public interface ContactRelationshipRepository extends JpaRepository<ContactRelationship, Long> {

    @Query(value = "select distinct contactRelationship from ContactRelationship contactRelationship left join fetch contactRelationship.users",
        countQuery = "select count(distinct contactRelationship) from ContactRelationship contactRelationship")
    Page<ContactRelationship> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct contactRelationship from ContactRelationship contactRelationship left join fetch contactRelationship.users")
    List<ContactRelationship> findAllWithEagerRelationships();

    @Query("select contactRelationship from ContactRelationship contactRelationship left join fetch contactRelationship.users where contactRelationship.id =:id")
    Optional<ContactRelationship> findOneWithEagerRelationships(@Param("id") Long id);
}
