package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MyTransaction;
import com.mycompany.myapp.repository.MyTransactionRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.MyTransaction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MyTransactionResource {

    private final Logger log = LoggerFactory.getLogger(MyTransactionResource.class);

    private static final String ENTITY_NAME = "myTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyTransactionRepository myTransactionRepository;

    public MyTransactionResource(MyTransactionRepository myTransactionRepository) {
        this.myTransactionRepository = myTransactionRepository;
    }

    /**
     * {@code POST  /my-transactions} : Create a new myTransaction.
     *
     * @param myTransaction the myTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myTransaction, or with status {@code 400 (Bad Request)} if the myTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-transactions")
    public ResponseEntity<MyTransaction> createMyTransaction(@Valid @RequestBody MyTransaction myTransaction) throws URISyntaxException {
        log.debug("REST request to save MyTransaction : {}", myTransaction);
        if (myTransaction.getId() != null) {
            throw new BadRequestAlertException("A new myTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyTransaction result = myTransactionRepository.save(myTransaction);
        return ResponseEntity.created(new URI("/api/my-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-transactions} : Updates an existing myTransaction.
     *
     * @param myTransaction the myTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myTransaction,
     * or with status {@code 400 (Bad Request)} if the myTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-transactions")
    public ResponseEntity<MyTransaction> updateMyTransaction(@Valid @RequestBody MyTransaction myTransaction) throws URISyntaxException {
        log.debug("REST request to update MyTransaction : {}", myTransaction);
        if (myTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MyTransaction result = myTransactionRepository.save(myTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, myTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /my-transactions} : get all the myTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myTransactions in body.
     */
    @GetMapping("/my-transactions")
    public List<MyTransaction> getAllMyTransactions() {
        log.debug("REST request to get all MyTransactions");
        return myTransactionRepository.findAll();
    }

    /**
     * {@code GET  /my-transactions/:id} : get the "id" myTransaction.
     *
     * @param id the id of the myTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-transactions/{id}")
    public ResponseEntity<MyTransaction> getMyTransaction(@PathVariable Long id) {
        log.debug("REST request to get MyTransaction : {}", id);
        Optional<MyTransaction> myTransaction = myTransactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(myTransaction);
    }

    /**
     * {@code DELETE  /my-transactions/:id} : delete the "id" myTransaction.
     *
     * @param id the id of the myTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-transactions/{id}")
    public ResponseEntity<Void> deleteMyTransaction(@PathVariable Long id) {
        log.debug("REST request to delete MyTransaction : {}", id);
        myTransactionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
