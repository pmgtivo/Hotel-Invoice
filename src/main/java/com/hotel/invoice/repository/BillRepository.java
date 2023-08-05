package com.hotel.invoice.repository;

import com.hotel.invoice.domain.Bill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {}
