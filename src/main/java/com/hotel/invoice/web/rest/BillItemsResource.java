package com.hotel.invoice.web.rest;

import com.hotel.invoice.domain.BillItems;
import com.hotel.invoice.repository.BillItemsRepository;
import com.hotel.invoice.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.hotel.invoice.domain.BillItems}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BillItemsResource {

    private final Logger log = LoggerFactory.getLogger(BillItemsResource.class);

    private static final String ENTITY_NAME = "billItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillItemsRepository billItemsRepository;

    public BillItemsResource(BillItemsRepository billItemsRepository) {
        this.billItemsRepository = billItemsRepository;
    }

    /**
     * {@code POST  /bill-items} : Create a new billItems.
     *
     * @param billItems the billItems to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billItems, or with status {@code 400 (Bad Request)} if the billItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bill-items")
    public ResponseEntity<BillItems> createBillItems(@RequestBody BillItems billItems) throws URISyntaxException {
        log.debug("REST request to save BillItems : {}", billItems);
        if (billItems.getId() != null) {
            throw new BadRequestAlertException("A new billItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillItems result = billItemsRepository.save(billItems);
        return ResponseEntity
            .created(new URI("/api/bill-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bill-items/:id} : Updates an existing billItems.
     *
     * @param id the id of the billItems to save.
     * @param billItems the billItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billItems,
     * or with status {@code 400 (Bad Request)} if the billItems is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bill-items/{id}")
    public ResponseEntity<BillItems> updateBillItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BillItems billItems
    ) throws URISyntaxException {
        log.debug("REST request to update BillItems : {}, {}", id, billItems);
        if (billItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BillItems result = billItemsRepository.save(billItems);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, billItems.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bill-items/:id} : Partial updates given fields of an existing billItems, field will ignore if it is null
     *
     * @param id the id of the billItems to save.
     * @param billItems the billItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billItems,
     * or with status {@code 400 (Bad Request)} if the billItems is not valid,
     * or with status {@code 404 (Not Found)} if the billItems is not found,
     * or with status {@code 500 (Internal Server Error)} if the billItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bill-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BillItems> partialUpdateBillItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BillItems billItems
    ) throws URISyntaxException {
        log.debug("REST request to partial update BillItems partially : {}, {}", id, billItems);
        if (billItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, billItems.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!billItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BillItems> result = billItemsRepository
            .findById(billItems.getId())
            .map(existingBillItems -> {
                if (billItems.getItemCount() != null) {
                    existingBillItems.setItemCount(billItems.getItemCount());
                }
                if (billItems.getAmount() != null) {
                    existingBillItems.setAmount(billItems.getAmount());
                }

                return existingBillItems;
            })
            .map(billItemsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, billItems.getId().toString())
        );
    }

    /**
     * {@code GET  /bill-items} : get all the billItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billItems in body.
     */
    @GetMapping("/bill-items")
    public List<BillItems> getAllBillItems() {
        log.debug("REST request to get all BillItems");
        return billItemsRepository.findAll();
    }

    /**
     * {@code GET  /bill-items/:id} : get the "id" billItems.
     *
     * @param id the id of the billItems to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billItems, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bill-items/{id}")
    public ResponseEntity<BillItems> getBillItems(@PathVariable Long id) {
        log.debug("REST request to get BillItems : {}", id);
        Optional<BillItems> billItems = billItemsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(billItems);
    }

    /**
     * {@code DELETE  /bill-items/:id} : delete the "id" billItems.
     *
     * @param id the id of the billItems to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bill-items/{id}")
    public ResponseEntity<Void> deleteBillItems(@PathVariable Long id) {
        log.debug("REST request to delete BillItems : {}", id);
        billItemsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
