package com.smarthome.app.repository;

import com.smarthome.app.domain.BuildingType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BuildingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingTypeRepository extends JpaRepository<BuildingType, Long> {}
