package com.smarthome.app.repository;

import com.smarthome.app.domain.HardwareItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HardwareItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HardwareItemsRepository extends JpaRepository<HardwareItems, Long> {}
