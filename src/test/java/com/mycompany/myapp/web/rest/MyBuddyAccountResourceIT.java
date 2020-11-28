package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PayMyBuddyApp;
import com.mycompany.myapp.domain.MyBuddyAccount;
import com.mycompany.myapp.repository.MyBuddyAccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MyBuddyAccountResource} REST controller.
 */
@SpringBootTest(classes = PayMyBuddyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MyBuddyAccountResourceIT {

    private static final Long DEFAULT_BALANCE = 1L;
    private static final Long UPDATED_BALANCE = 2L;

    private static final String DEFAULT_PROFILE_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PICTURE = "BBBBBBBBBB";

    @Autowired
    private MyBuddyAccountRepository myBuddyAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyBuddyAccountMockMvc;

    private MyBuddyAccount myBuddyAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyBuddyAccount createEntity(EntityManager em) {
        MyBuddyAccount myBuddyAccount = new MyBuddyAccount()
            .balance(DEFAULT_BALANCE)
            .profilePicture(DEFAULT_PROFILE_PICTURE);
        return myBuddyAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyBuddyAccount createUpdatedEntity(EntityManager em) {
        MyBuddyAccount myBuddyAccount = new MyBuddyAccount()
            .balance(UPDATED_BALANCE)
            .profilePicture(UPDATED_PROFILE_PICTURE);
        return myBuddyAccount;
    }

    @BeforeEach
    public void initTest() {
        myBuddyAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyBuddyAccount() throws Exception {
        int databaseSizeBeforeCreate = myBuddyAccountRepository.findAll().size();
        // Create the MyBuddyAccount
        restMyBuddyAccountMockMvc.perform(post("/api/my-buddy-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myBuddyAccount)))
            .andExpect(status().isCreated());

        // Validate the MyBuddyAccount in the database
        List<MyBuddyAccount> myBuddyAccountList = myBuddyAccountRepository.findAll();
        assertThat(myBuddyAccountList).hasSize(databaseSizeBeforeCreate + 1);
        MyBuddyAccount testMyBuddyAccount = myBuddyAccountList.get(myBuddyAccountList.size() - 1);
        assertThat(testMyBuddyAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testMyBuddyAccount.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void createMyBuddyAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myBuddyAccountRepository.findAll().size();

        // Create the MyBuddyAccount with an existing ID
        myBuddyAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyBuddyAccountMockMvc.perform(post("/api/my-buddy-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myBuddyAccount)))
            .andExpect(status().isBadRequest());

        // Validate the MyBuddyAccount in the database
        List<MyBuddyAccount> myBuddyAccountList = myBuddyAccountRepository.findAll();
        assertThat(myBuddyAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMyBuddyAccounts() throws Exception {
        // Initialize the database
        myBuddyAccountRepository.saveAndFlush(myBuddyAccount);

        // Get all the myBuddyAccountList
        restMyBuddyAccountMockMvc.perform(get("/api/my-buddy-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myBuddyAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)));
    }
    
    @Test
    @Transactional
    public void getMyBuddyAccount() throws Exception {
        // Initialize the database
        myBuddyAccountRepository.saveAndFlush(myBuddyAccount);

        // Get the myBuddyAccount
        restMyBuddyAccountMockMvc.perform(get("/api/my-buddy-accounts/{id}", myBuddyAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myBuddyAccount.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.profilePicture").value(DEFAULT_PROFILE_PICTURE));
    }
    @Test
    @Transactional
    public void getNonExistingMyBuddyAccount() throws Exception {
        // Get the myBuddyAccount
        restMyBuddyAccountMockMvc.perform(get("/api/my-buddy-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyBuddyAccount() throws Exception {
        // Initialize the database
        myBuddyAccountRepository.saveAndFlush(myBuddyAccount);

        int databaseSizeBeforeUpdate = myBuddyAccountRepository.findAll().size();

        // Update the myBuddyAccount
        MyBuddyAccount updatedMyBuddyAccount = myBuddyAccountRepository.findById(myBuddyAccount.getId()).get();
        // Disconnect from session so that the updates on updatedMyBuddyAccount are not directly saved in db
        em.detach(updatedMyBuddyAccount);
        updatedMyBuddyAccount
            .balance(UPDATED_BALANCE)
            .profilePicture(UPDATED_PROFILE_PICTURE);

        restMyBuddyAccountMockMvc.perform(put("/api/my-buddy-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyBuddyAccount)))
            .andExpect(status().isOk());

        // Validate the MyBuddyAccount in the database
        List<MyBuddyAccount> myBuddyAccountList = myBuddyAccountRepository.findAll();
        assertThat(myBuddyAccountList).hasSize(databaseSizeBeforeUpdate);
        MyBuddyAccount testMyBuddyAccount = myBuddyAccountList.get(myBuddyAccountList.size() - 1);
        assertThat(testMyBuddyAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testMyBuddyAccount.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void updateNonExistingMyBuddyAccount() throws Exception {
        int databaseSizeBeforeUpdate = myBuddyAccountRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyBuddyAccountMockMvc.perform(put("/api/my-buddy-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myBuddyAccount)))
            .andExpect(status().isBadRequest());

        // Validate the MyBuddyAccount in the database
        List<MyBuddyAccount> myBuddyAccountList = myBuddyAccountRepository.findAll();
        assertThat(myBuddyAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyBuddyAccount() throws Exception {
        // Initialize the database
        myBuddyAccountRepository.saveAndFlush(myBuddyAccount);

        int databaseSizeBeforeDelete = myBuddyAccountRepository.findAll().size();

        // Delete the myBuddyAccount
        restMyBuddyAccountMockMvc.perform(delete("/api/my-buddy-accounts/{id}", myBuddyAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyBuddyAccount> myBuddyAccountList = myBuddyAccountRepository.findAll();
        assertThat(myBuddyAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
