package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MyBuddyAccount;
import com.mycompany.myapp.repository.MyBuddyAccountRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.MyBuddyAccount}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MyBuddyAccountResource {

    private final Logger log = LoggerFactory.getLogger(MyBuddyAccountResource.class);

    private static final String ENTITY_NAME = "myBuddyAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyBuddyAccountRepository myBuddyAccountRepository;

    public MyBuddyAccountResource(MyBuddyAccountRepository myBuddyAccountRepository) {
        this.myBuddyAccountRepository = myBuddyAccountRepository;
    }

    /**
     * {@code POST  /my-buddy-accounts} : Create a new myBuddyAccount.
     *
     * @param myBuddyAccount the myBuddyAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myBuddyAccount, or with status {@code 400 (Bad Request)} if the myBuddyAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-buddy-accounts")
    public ResponseEntity<MyBuddyAccount> createMyBuddyAccount(@RequestBody MyBuddyAccount myBuddyAccount) throws URISyntaxException {
        log.debug("REST request to save MyBuddyAccount : {}", myBuddyAccount);
        if (myBuddyAccount.getId() != null) {
            throw new BadRequestAlertException("A new myBuddyAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyBuddyAccount result = myBuddyAccountRepository.save(myBuddyAccount);
        return ResponseEntity.created(new URI("/api/my-buddy-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-buddy-accounts} : Updates an existing myBuddyAccount.
     *
     * @param myBuddyAccount the myBuddyAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myBuddyAccount,
     * or with status {@code 400 (Bad Request)} if the myBuddyAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myBuddyAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-buddy-accounts")
    public ResponseEntity<MyBuddyAccount> updateMyBuddyAccount(@RequestBody MyBuddyAccount myBuddyAccount) throws URISyntaxException {
        log.debug("REST request to update MyBuddyAccount : {}", myBuddyAccount);
        if (myBuddyAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MyBuddyAccount result = myBuddyAccountRepository.save(myBuddyAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myBuddyAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /my-buddy-accounts} : get all the myBuddyAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myBuddyAccounts in body.
     */
    @GetMapping("/my-buddy-accounts")
    public List<MyBuddyAccount> getAllMyBuddyAccounts() {
        log.debug("REST request to get all MyBuddyAccounts");
        return myBuddyAccountRepository.findAll();
    }

    /**
     * {@code GET  /my-buddy-accounts/:id} : get the "id" myBuddyAccount.
     *
     * @param id the id of the myBuddyAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myBuddyAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-buddy-accounts/{id}")
    public ResponseEntity<MyBuddyAccount> getMyBuddyAccount(@PathVariable Long id) {
        log.debug("REST request to get MyBuddyAccount : {}", id);
        Optional<MyBuddyAccount> myBuddyAccount = myBuddyAccountRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(myBuddyAccount);
    }

    /**
     * {@code DELETE  /my-buddy-accounts/:id} : delete the "id" myBuddyAccount.
     *
     * @param id the id of the myBuddyAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-buddy-accounts/{id}")
    public ResponseEntity<Void> deleteMyBuddyAccount(@PathVariable Long id) {
        log.debug("REST request to delete MyBuddyAccount : {}", id);
        myBuddyAccountRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
