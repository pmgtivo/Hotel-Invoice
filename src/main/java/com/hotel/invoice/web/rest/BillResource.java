package com.hotel.invoice.web.rest;

import com.hotel.invoice.domain.Bill;
import com.hotel.invoice.repository.BillRepository;
import com.hotel.invoice.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hotel.invoice.domain.Bill}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BillResource {

    private final Logger log = LoggerFactory.getLogger(BillResource.class);

    private static final String ENTITY_NAME = "bill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillRepository billRepository;

    public BillResource(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    /**
     * {@code POST  /bills} : Create a new bill.
     *
     * @param bill the bill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bill, or with status {@code 400 (Bad Request)} if the bill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bills")
    public ResponseEntity<Bill> createBill(@Valid @RequestBody Bill bill) throws URISyntaxException {
        log.debug("REST request to save Bill : {}", bill);
        if (bill.getId() != null) {
            throw new BadRequestAlertException("A new bill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bill result = billRepository.save(bill);
        return ResponseEntity
            .created(new URI("/api/bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bills/:id} : Updates an existing bill.
     *
     * @param id the id of the bill to save.
     * @param bill the bill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bill,
     * or with status {@code 400 (Bad Request)} if the bill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bills/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Bill bill)
        throws URISyntaxException {
        log.debug("REST request to update Bill : {}, {}", id, bill);
        if (bill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bill result = billRepository.save(bill);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bill.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bills/:id} : Partial updates given fields of an existing bill, field will ignore if it is null
     *
     * @param id the id of the bill to save.
     * @param bill the bill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bill,
     * or with status {@code 400 (Bad Request)} if the bill is not valid,
     * or with status {@code 404 (Not Found)} if the bill is not found,
     * or with status {@code 500 (Internal Server Error)} if the bill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bill> partialUpdateBill(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bill bill
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bill partially : {}, {}", id, bill);
        if (bill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bill> result = billRepository
            .findById(bill.getId())
            .map(existingBill -> {
                if (bill.getTableNo() != null) {
                    existingBill.setTableNo(bill.getTableNo());
                }
                if (bill.getCustomerName() != null) {
                    existingBill.setCustomerName(bill.getCustomerName());
                }
                if (bill.getCustomerContact() != null) {
                    existingBill.setCustomerContact(bill.getCustomerContact());
                }
                if (bill.getCgst() != null) {
                    existingBill.setCgst(bill.getCgst());
                }
                if (bill.getSgst() != null) {
                    existingBill.setSgst(bill.getSgst());
                }
                if (bill.getTotalAmount() != null) {
                    existingBill.setTotalAmount(bill.getTotalAmount());
                }
                if (bill.getIsParcel() != null) {
                    existingBill.setIsParcel(bill.getIsParcel());
                }
                if (bill.getParcelCharges() != null) {
                    existingBill.setParcelCharges(bill.getParcelCharges());
                }
                if (bill.getAdjustAmount() != null) {
                    existingBill.setAdjustAmount(bill.getAdjustAmount());
                }
                if (bill.getDiscountPercentage() != null) {
                    existingBill.setDiscountPercentage(bill.getDiscountPercentage());
                }
                if (bill.getPaidBy() != null) {
                    existingBill.setPaidBy(bill.getPaidBy());
                }
                if (bill.getCreateDateTime() != null) {
                    existingBill.setCreateDateTime(bill.getCreateDateTime());
                }

                return existingBill;
            })
            .map(billRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bill.getId().toString())
        );
    }

    /**
     * {@code GET  /bills} : get all the bills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bills in body.
     */
    @GetMapping("/bills")
    public List<Bill> getAllBills() {
        log.debug("REST request to get all Bills");
        return billRepository.findAll();
    }

    /**
     * {@code GET  /bills/:id} : get the "id" bill.
     *
     * @param id the id of the bill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> getBill(@PathVariable Long id) {
        log.debug("REST request to get Bill : {}", id);
        Optional<Bill> bill = billRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bill);
    }

    /**
     * {@code DELETE  /bills/:id} : delete the "id" bill.
     *
     * @param id the id of the bill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bills/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        log.debug("REST request to delete Bill : {}", id);
        billRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
