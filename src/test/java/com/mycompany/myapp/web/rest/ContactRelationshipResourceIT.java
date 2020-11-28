package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.PayMyBuddyApp;
import com.mycompany.myapp.domain.ContactRelationship;
import com.mycompany.myapp.repository.ContactRelationshipRepository;

/**
 * Integration tests for the {@link ContactRelationshipResource} REST controller.
 */
@SpringBootTest(classes = PayMyBuddyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactRelationshipResourceIT {

    private static final Long DEFAULT_USER_ID_1 = 1L;
    private static final Long UPDATED_USER_ID_1 = 2L;

    private static final Long DEFAULT_USER_ID_2 = 1L;
    private static final Long UPDATED_USER_ID_2 = 2L;

    @Autowired
    private ContactRelationshipRepository contactRelationshipRepository;

    @Mock
    private ContactRelationshipRepository contactRelationshipRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactRelationshipMockMvc;

    private ContactRelationship contactRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactRelationship createEntity(EntityManager em) {
        ContactRelationship contactRelationship = new ContactRelationship()
            .userId1(DEFAULT_USER_ID_1)
            .userId2(DEFAULT_USER_ID_2);
        return contactRelationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactRelationship createUpdatedEntity(EntityManager em) {
        ContactRelationship contactRelationship = new ContactRelationship()
            .userId1(UPDATED_USER_ID_1)
            .userId2(UPDATED_USER_ID_2);
        return contactRelationship;
    }

    @BeforeEach
    public void initTest() {
        contactRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactRelationship() throws Exception {
        int databaseSizeBeforeCreate = contactRelationshipRepository.findAll().size();
        // Create the ContactRelationship
        restContactRelationshipMockMvc.perform(post("/api/contact-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactRelationship)))
            .andExpect(status().isCreated());

        // Validate the ContactRelationship in the database
        List<ContactRelationship> contactRelationshipList = contactRelationshipRepository.findAll();
        assertThat(contactRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        ContactRelationship testContactRelationship = contactRelationshipList.get(contactRelationshipList.size() - 1);
        assertThat(testContactRelationship.getUserId1()).isEqualTo(DEFAULT_USER_ID_1);
        assertThat(testContactRelationship.getUserId2()).isEqualTo(DEFAULT_USER_ID_2);
    }

    @Test
    @Transactional
    public void createContactRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactRelationshipRepository.findAll().size();

        // Create the ContactRelationship with an existing ID
        contactRelationship.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactRelationshipMockMvc.perform(post("/api/contact-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the ContactRelationship in the database
        List<ContactRelationship> contactRelationshipList = contactRelationshipRepository.findAll();
        assertThat(contactRelationshipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactRelationships() throws Exception {
        // Initialize the database
        contactRelationshipRepository.saveAndFlush(contactRelationship);

        // Get all the contactRelationshipList
        restContactRelationshipMockMvc.perform(get("/api/contact-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId1").value(hasItem(DEFAULT_USER_ID_1.intValue())))
            .andExpect(jsonPath("$.[*].userId2").value(hasItem(DEFAULT_USER_ID_2.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllContactRelationshipsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contactRelationshipRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactRelationshipMockMvc.perform(get("/api/contact-relationships?eagerload=true"))
            .andExpect(status().isOk());

        verify(contactRelationshipRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllContactRelationshipsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contactRelationshipRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContactRelationshipMockMvc.perform(get("/api/contact-relationships?eagerload=true"))
            .andExpect(status().isOk());

        verify(contactRelationshipRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getContactRelationship() throws Exception {
        // Initialize the database
        contactRelationshipRepository.saveAndFlush(contactRelationship);

        // Get the contactRelationship
        restContactRelationshipMockMvc.perform(get("/api/contact-relationships/{id}", contactRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactRelationship.getId().intValue()))
            .andExpect(jsonPath("$.userId1").value(DEFAULT_USER_ID_1.intValue()))
            .andExpect(jsonPath("$.userId2").value(DEFAULT_USER_ID_2.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingContactRelationship() throws Exception {
        // Get the contactRelationship
        restContactRelationshipMockMvc.perform(get("/api/contact-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactRelationship() throws Exception {
        // Initialize the database
        contactRelationshipRepository.saveAndFlush(contactRelationship);

        int databaseSizeBeforeUpdate = contactRelationshipRepository.findAll().size();

        // Update the contactRelationship
        ContactRelationship updatedContactRelationship = contactRelationshipRepository.findById(contactRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedContactRelationship are not directly saved in db
        em.detach(updatedContactRelationship);
        updatedContactRelationship
            .userId1(UPDATED_USER_ID_1)
            .userId2(UPDATED_USER_ID_2);

        restContactRelationshipMockMvc.perform(put("/api/contact-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactRelationship)))
            .andExpect(status().isOk());

        // Validate the ContactRelationship in the database
        List<ContactRelationship> contactRelationshipList = contactRelationshipRepository.findAll();
        assertThat(contactRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ContactRelationship testContactRelationship = contactRelationshipList.get(contactRelationshipList.size() - 1);
        assertThat(testContactRelationship.getUserId1()).isEqualTo(UPDATED_USER_ID_1);
        assertThat(testContactRelationship.getUserId2()).isEqualTo(UPDATED_USER_ID_2);
    }

    @Test
    @Transactional
    public void updateNonExistingContactRelationship() throws Exception {
        int databaseSizeBeforeUpdate = contactRelationshipRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactRelationshipMockMvc.perform(put("/api/contact-relationships")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the ContactRelationship in the database
        List<ContactRelationship> contactRelationshipList = contactRelationshipRepository.findAll();
        assertThat(contactRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactRelationship() throws Exception {
        // Initialize the database
        contactRelationshipRepository.saveAndFlush(contactRelationship);

        int databaseSizeBeforeDelete = contactRelationshipRepository.findAll().size();

        // Delete the contactRelationship
        restContactRelationshipMockMvc.perform(delete("/api/contact-relationships/{id}", contactRelationship.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactRelationship> contactRelationshipList = contactRelationshipRepository.findAll();
        assertThat(contactRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
