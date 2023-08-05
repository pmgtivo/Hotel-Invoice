package com.hotel.invoice.web.rest;

import com.hotel.invoice.domain.Hotel;
import com.hotel.invoice.repository.HotelRepository;
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
 * REST controller for managing {@link com.hotel.invoice.domain.Hotel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HotelResource {

    private final Logger log = LoggerFactory.getLogger(HotelResource.class);

    private static final String ENTITY_NAME = "hotel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelRepository hotelRepository;

    public HotelResource(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * {@code POST  /hotels} : Create a new hotel.
     *
     * @param hotel the hotel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotel, or with status {@code 400 (Bad Request)} if the hotel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotels")
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to save Hotel : {}", hotel);
        if (hotel.getId() != null) {
            throw new BadRequestAlertException("A new hotel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hotel result = hotelRepository.save(hotel);
        return ResponseEntity
            .created(new URI("/api/hotels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hotels/:id} : Updates an existing hotel.
     *
     * @param id the id of the hotel to save.
     * @param hotel the hotel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotel,
     * or with status {@code 400 (Bad Request)} if the hotel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hotels/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Hotel hotel)
        throws URISyntaxException {
        log.debug("REST request to update Hotel : {}, {}", id, hotel);
        if (hotel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Hotel result = hotelRepository.save(hotel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hotel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hotels/:id} : Partial updates given fields of an existing hotel, field will ignore if it is null
     *
     * @param id the id of the hotel to save.
     * @param hotel the hotel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotel,
     * or with status {@code 400 (Bad Request)} if the hotel is not valid,
     * or with status {@code 404 (Not Found)} if the hotel is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hotels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Hotel> partialUpdateHotel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Hotel hotel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hotel partially : {}, {}", id, hotel);
        if (hotel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hotel> result = hotelRepository
            .findById(hotel.getId())
            .map(existingHotel -> {
                if (hotel.getName() != null) {
                    existingHotel.setName(hotel.getName());
                }
                if (hotel.getAddress() != null) {
                    existingHotel.setAddress(hotel.getAddress());
                }
                if (hotel.getContact() != null) {
                    existingHotel.setContact(hotel.getContact());
                }
                if (hotel.getOwnerName() != null) {
                    existingHotel.setOwnerName(hotel.getOwnerName());
                }
                if (hotel.getOwnerContact() != null) {
                    existingHotel.setOwnerContact(hotel.getOwnerContact());
                }
                if (hotel.getGstNo() != null) {
                    existingHotel.setGstNo(hotel.getGstNo());
                }
                if (hotel.getIsVeg() != null) {
                    existingHotel.setIsVeg(hotel.getIsVeg());
                }
                if (hotel.getLogo() != null) {
                    existingHotel.setLogo(hotel.getLogo());
                }
                if (hotel.getLogoContentType() != null) {
                    existingHotel.setLogoContentType(hotel.getLogoContentType());
                }

                return existingHotel;
            })
            .map(hotelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hotel.getId().toString())
        );
    }

    /**
     * {@code GET  /hotels} : get all the hotels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotels in body.
     */
    @GetMapping("/hotels")
    public List<Hotel> getAllHotels() {
        log.debug("REST request to get all Hotels");
        return hotelRepository.findAll();
    }

    /**
     * {@code GET  /hotels/:id} : get the "id" hotel.
     *
     * @param id the id of the hotel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hotels/{id}")
    public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
        log.debug("REST request to get Hotel : {}", id);
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hotel);
    }

    /**
     * {@code DELETE  /hotels/:id} : delete the "id" hotel.
     *
     * @param id the id of the hotel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        log.debug("REST request to delete Hotel : {}", id);
        hotelRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
