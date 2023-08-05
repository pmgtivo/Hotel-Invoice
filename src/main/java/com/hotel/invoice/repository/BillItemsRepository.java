package com.hotel.invoice.repository;

import com.hotel.invoice.domain.BillItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BillItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillItemsRepository extends JpaRepository<BillItems, Long> {}
