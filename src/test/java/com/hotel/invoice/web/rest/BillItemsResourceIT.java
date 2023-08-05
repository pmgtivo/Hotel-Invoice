package com.hotel.invoice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hotel.invoice.IntegrationTest;
import com.hotel.invoice.domain.BillItems;
import com.hotel.invoice.repository.BillItemsRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BillItemsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BillItemsResourceIT {

    private static final Integer DEFAULT_ITEM_COUNT = 1;
    private static final Integer UPDATED_ITEM_COUNT = 2;

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final String ENTITY_API_URL = "/api/bill-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BillItemsRepository billItemsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillItemsMockMvc;

    private BillItems billItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillItems createEntity(EntityManager em) {
        BillItems billItems = new BillItems().itemCount(DEFAULT_ITEM_COUNT).amount(DEFAULT_AMOUNT);
        return billItems;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillItems createUpdatedEntity(EntityManager em) {
        BillItems billItems = new BillItems().itemCount(UPDATED_ITEM_COUNT).amount(UPDATED_AMOUNT);
        return billItems;
    }

    @BeforeEach
    public void initTest() {
        billItems = createEntity(em);
    }

    @Test
    @Transactional
    void createBillItems() throws Exception {
        int databaseSizeBeforeCreate = billItemsRepository.findAll().size();
        // Create the BillItems
        restBillItemsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billItems)))
            .andExpect(status().isCreated());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeCreate + 1);
        BillItems testBillItems = billItemsList.get(billItemsList.size() - 1);
        assertThat(testBillItems.getItemCount()).isEqualTo(DEFAULT_ITEM_COUNT);
        assertThat(testBillItems.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createBillItemsWithExistingId() throws Exception {
        // Create the BillItems with an existing ID
        billItems.setId(1L);

        int databaseSizeBeforeCreate = billItemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillItemsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billItems)))
            .andExpect(status().isBadRequest());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBillItems() throws Exception {
        // Initialize the database
        billItemsRepository.saveAndFlush(billItems);

        // Get all the billItemsList
        restBillItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemCount").value(hasItem(DEFAULT_ITEM_COUNT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)));
    }

    @Test
    @Transactional
    void getBillItems() throws Exception {
        // Initialize the database
        billItemsRepository.saveAndFlush(billItems);

        // Get the billItems
        restBillItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, billItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billItems.getId().intValue()))
            .andExpect(jsonPath("$.itemCount").value(DEFAULT_ITEM_COUNT))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT));
    }

    @Test
    @Transactional
    void getNonExistingBillItems() throws Exception {
        // Get the billItems
        restBillItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBillItems() throws Exception {
        // Initialize the database
        billItemsRepository.saveAndFlush(billItems);

        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();

        // Update the billItems
        BillItems updatedBillItems = billItemsRepository.findById(billItems.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBillItems are not directly saved in db
        em.detach(updatedBillItems);
        updatedBillItems.itemCount(UPDATED_ITEM_COUNT).amount(UPDATED_AMOUNT);

        restBillItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBillItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBillItems))
            )
            .andExpect(status().isOk());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
        BillItems testBillItems = billItemsList.get(billItemsList.size() - 1);
        assertThat(testBillItems.getItemCount()).isEqualTo(UPDATED_ITEM_COUNT);
        assertThat(testBillItems.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingBillItems() throws Exception {
        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();
        billItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billItems.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBillItems() throws Exception {
        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();
        billItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBillItems() throws Exception {
        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();
        billItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillItemsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billItems)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBillItemsWithPatch() throws Exception {
        // Initialize the database
        billItemsRepository.saveAndFlush(billItems);

        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();

        // Update the billItems using partial update
        BillItems partialUpdatedBillItems = new BillItems();
        partialUpdatedBillItems.setId(billItems.getId());

        partialUpdatedBillItems.itemCount(UPDATED_ITEM_COUNT);

        restBillItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillItems))
            )
            .andExpect(status().isOk());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
        BillItems testBillItems = billItemsList.get(billItemsList.size() - 1);
        assertThat(testBillItems.getItemCount()).isEqualTo(UPDATED_ITEM_COUNT);
        assertThat(testBillItems.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateBillItemsWithPatch() throws Exception {
        // Initialize the database
        billItemsRepository.saveAndFlush(billItems);

        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();

        // Update the billItems using partial update
        BillItems partialUpdatedBillItems = new BillItems();
        partialUpdatedBillItems.setId(billItems.getId());

        partialUpdatedBillItems.itemCount(UPDATED_ITEM_COUNT).amount(UPDATED_AMOUNT);

        restBillItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillItems))
            )
            .andExpect(status().isOk());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
        BillItems testBillItems = billItemsList.get(billItemsList.size() - 1);
        assertThat(testBillItems.getItemCount()).isEqualTo(UPDATED_ITEM_COUNT);
        assertThat(testBillItems.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingBillItems() throws Exception {
        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();
        billItems.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, billItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBillItems() throws Exception {
        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();
        billItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billItems))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBillItems() throws Exception {
        int databaseSizeBeforeUpdate = billItemsRepository.findAll().size();
        billItems.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillItemsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(billItems))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BillItems in the database
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBillItems() throws Exception {
        // Initialize the database
        billItemsRepository.saveAndFlush(billItems);

        int databaseSizeBeforeDelete = billItemsRepository.findAll().size();

        // Delete the billItems
        restBillItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, billItems.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillItems> billItemsList = billItemsRepository.findAll();
        assertThat(billItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
