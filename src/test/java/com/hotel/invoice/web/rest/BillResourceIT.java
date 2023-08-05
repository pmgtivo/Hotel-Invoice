package com.hotel.invoice.web.rest;

import static com.hotel.invoice.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hotel.invoice.IntegrationTest;
import com.hotel.invoice.domain.Bill;
import com.hotel.invoice.repository.BillRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link BillResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BillResourceIT {

    private static final String DEFAULT_TABLE_NO = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_CGST = "AAAAAAAAAA";
    private static final String UPDATED_CGST = "BBBBBBBBBB";

    private static final String DEFAULT_SGST = "AAAAAAAAAA";
    private static final String UPDATED_SGST = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_AMOUNT = 1;
    private static final Integer UPDATED_TOTAL_AMOUNT = 2;

    private static final Boolean DEFAULT_IS_PARCEL = false;
    private static final Boolean UPDATED_IS_PARCEL = true;

    private static final Integer DEFAULT_PARCEL_CHARGES = 1;
    private static final Integer UPDATED_PARCEL_CHARGES = 2;

    private static final Integer DEFAULT_ADJUST_AMOUNT = 1;
    private static final Integer UPDATED_ADJUST_AMOUNT = 2;

    private static final Double DEFAULT_DISCOUNT_PERCENTAGE = 1D;
    private static final Double UPDATED_DISCOUNT_PERCENTAGE = 2D;

    private static final String DEFAULT_PAID_BY = "AAAAAAAAAA";
    private static final String UPDATED_PAID_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/bills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillMockMvc;

    private Bill bill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bill createEntity(EntityManager em) {
        Bill bill = new Bill()
            .tableNo(DEFAULT_TABLE_NO)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .customerContact(DEFAULT_CUSTOMER_CONTACT)
            .cgst(DEFAULT_CGST)
            .sgst(DEFAULT_SGST)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .isParcel(DEFAULT_IS_PARCEL)
            .parcelCharges(DEFAULT_PARCEL_CHARGES)
            .adjustAmount(DEFAULT_ADJUST_AMOUNT)
            .discountPercentage(DEFAULT_DISCOUNT_PERCENTAGE)
            .paidBy(DEFAULT_PAID_BY)
            .createDateTime(DEFAULT_CREATE_DATE_TIME);
        return bill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bill createUpdatedEntity(EntityManager em) {
        Bill bill = new Bill()
            .tableNo(UPDATED_TABLE_NO)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerContact(UPDATED_CUSTOMER_CONTACT)
            .cgst(UPDATED_CGST)
            .sgst(UPDATED_SGST)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .isParcel(UPDATED_IS_PARCEL)
            .parcelCharges(UPDATED_PARCEL_CHARGES)
            .adjustAmount(UPDATED_ADJUST_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .paidBy(UPDATED_PAID_BY)
            .createDateTime(UPDATED_CREATE_DATE_TIME);
        return bill;
    }

    @BeforeEach
    public void initTest() {
        bill = createEntity(em);
    }

    @Test
    @Transactional
    void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();
        // Create the Bill
        restBillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getTableNo()).isEqualTo(DEFAULT_TABLE_NO);
        assertThat(testBill.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testBill.getCustomerContact()).isEqualTo(DEFAULT_CUSTOMER_CONTACT);
        assertThat(testBill.getCgst()).isEqualTo(DEFAULT_CGST);
        assertThat(testBill.getSgst()).isEqualTo(DEFAULT_SGST);
        assertThat(testBill.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testBill.getIsParcel()).isEqualTo(DEFAULT_IS_PARCEL);
        assertThat(testBill.getParcelCharges()).isEqualTo(DEFAULT_PARCEL_CHARGES);
        assertThat(testBill.getAdjustAmount()).isEqualTo(DEFAULT_ADJUST_AMOUNT);
        assertThat(testBill.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testBill.getPaidBy()).isEqualTo(DEFAULT_PAID_BY);
        assertThat(testBill.getCreateDateTime()).isEqualTo(DEFAULT_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    void createBillWithExistingId() throws Exception {
        // Create the Bill with an existing ID
        bill.setId(1L);

        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setTotalAmount(null);

        // Create the Bill, which fails.

        restBillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList
        restBillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableNo").value(hasItem(DEFAULT_TABLE_NO)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerContact").value(hasItem(DEFAULT_CUSTOMER_CONTACT)))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST)))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.[*].isParcel").value(hasItem(DEFAULT_IS_PARCEL.booleanValue())))
            .andExpect(jsonPath("$.[*].parcelCharges").value(hasItem(DEFAULT_PARCEL_CHARGES)))
            .andExpect(jsonPath("$.[*].adjustAmount").value(hasItem(DEFAULT_ADJUST_AMOUNT)))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].paidBy").value(hasItem(DEFAULT_PAID_BY)))
            .andExpect(jsonPath("$.[*].createDateTime").value(hasItem(sameInstant(DEFAULT_CREATE_DATE_TIME))));
    }

    @Test
    @Transactional
    void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc
            .perform(get(ENTITY_API_URL_ID, bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.tableNo").value(DEFAULT_TABLE_NO))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.customerContact").value(DEFAULT_CUSTOMER_CONTACT))
            .andExpect(jsonPath("$.cgst").value(DEFAULT_CGST))
            .andExpect(jsonPath("$.sgst").value(DEFAULT_SGST))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT))
            .andExpect(jsonPath("$.isParcel").value(DEFAULT_IS_PARCEL.booleanValue()))
            .andExpect(jsonPath("$.parcelCharges").value(DEFAULT_PARCEL_CHARGES))
            .andExpect(jsonPath("$.adjustAmount").value(DEFAULT_ADJUST_AMOUNT))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.paidBy").value(DEFAULT_PAID_BY))
            .andExpect(jsonPath("$.createDateTime").value(sameInstant(DEFAULT_CREATE_DATE_TIME)));
    }

    @Test
    @Transactional
    void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        Bill updatedBill = billRepository.findById(bill.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBill are not directly saved in db
        em.detach(updatedBill);
        updatedBill
            .tableNo(UPDATED_TABLE_NO)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerContact(UPDATED_CUSTOMER_CONTACT)
            .cgst(UPDATED_CGST)
            .sgst(UPDATED_SGST)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .isParcel(UPDATED_IS_PARCEL)
            .parcelCharges(UPDATED_PARCEL_CHARGES)
            .adjustAmount(UPDATED_ADJUST_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .paidBy(UPDATED_PAID_BY)
            .createDateTime(UPDATED_CREATE_DATE_TIME);

        restBillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBill.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBill))
            )
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getTableNo()).isEqualTo(UPDATED_TABLE_NO);
        assertThat(testBill.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testBill.getCustomerContact()).isEqualTo(UPDATED_CUSTOMER_CONTACT);
        assertThat(testBill.getCgst()).isEqualTo(UPDATED_CGST);
        assertThat(testBill.getSgst()).isEqualTo(UPDATED_SGST);
        assertThat(testBill.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testBill.getIsParcel()).isEqualTo(UPDATED_IS_PARCEL);
        assertThat(testBill.getParcelCharges()).isEqualTo(UPDATED_PARCEL_CHARGES);
        assertThat(testBill.getAdjustAmount()).isEqualTo(UPDATED_ADJUST_AMOUNT);
        assertThat(testBill.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testBill.getPaidBy()).isEqualTo(UPDATED_PAID_BY);
        assertThat(testBill.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();
        bill.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bill.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bill))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();
        bill.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bill))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();
        bill.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBillWithPatch() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill using partial update
        Bill partialUpdatedBill = new Bill();
        partialUpdatedBill.setId(bill.getId());

        partialUpdatedBill
            .tableNo(UPDATED_TABLE_NO)
            .customerContact(UPDATED_CUSTOMER_CONTACT)
            .isParcel(UPDATED_IS_PARCEL)
            .parcelCharges(UPDATED_PARCEL_CHARGES)
            .adjustAmount(UPDATED_ADJUST_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .createDateTime(UPDATED_CREATE_DATE_TIME);

        restBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBill))
            )
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getTableNo()).isEqualTo(UPDATED_TABLE_NO);
        assertThat(testBill.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testBill.getCustomerContact()).isEqualTo(UPDATED_CUSTOMER_CONTACT);
        assertThat(testBill.getCgst()).isEqualTo(DEFAULT_CGST);
        assertThat(testBill.getSgst()).isEqualTo(DEFAULT_SGST);
        assertThat(testBill.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testBill.getIsParcel()).isEqualTo(UPDATED_IS_PARCEL);
        assertThat(testBill.getParcelCharges()).isEqualTo(UPDATED_PARCEL_CHARGES);
        assertThat(testBill.getAdjustAmount()).isEqualTo(UPDATED_ADJUST_AMOUNT);
        assertThat(testBill.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testBill.getPaidBy()).isEqualTo(DEFAULT_PAID_BY);
        assertThat(testBill.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBillWithPatch() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill using partial update
        Bill partialUpdatedBill = new Bill();
        partialUpdatedBill.setId(bill.getId());

        partialUpdatedBill
            .tableNo(UPDATED_TABLE_NO)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerContact(UPDATED_CUSTOMER_CONTACT)
            .cgst(UPDATED_CGST)
            .sgst(UPDATED_SGST)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .isParcel(UPDATED_IS_PARCEL)
            .parcelCharges(UPDATED_PARCEL_CHARGES)
            .adjustAmount(UPDATED_ADJUST_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .paidBy(UPDATED_PAID_BY)
            .createDateTime(UPDATED_CREATE_DATE_TIME);

        restBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBill))
            )
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getTableNo()).isEqualTo(UPDATED_TABLE_NO);
        assertThat(testBill.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testBill.getCustomerContact()).isEqualTo(UPDATED_CUSTOMER_CONTACT);
        assertThat(testBill.getCgst()).isEqualTo(UPDATED_CGST);
        assertThat(testBill.getSgst()).isEqualTo(UPDATED_SGST);
        assertThat(testBill.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testBill.getIsParcel()).isEqualTo(UPDATED_IS_PARCEL);
        assertThat(testBill.getParcelCharges()).isEqualTo(UPDATED_PARCEL_CHARGES);
        assertThat(testBill.getAdjustAmount()).isEqualTo(UPDATED_ADJUST_AMOUNT);
        assertThat(testBill.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testBill.getPaidBy()).isEqualTo(UPDATED_PAID_BY);
        assertThat(testBill.getCreateDateTime()).isEqualTo(UPDATED_CREATE_DATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();
        bill.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bill))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();
        bill.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bill))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();
        bill.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bill)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Delete the bill
        restBillMockMvc
            .perform(delete(ENTITY_API_URL_ID, bill.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
