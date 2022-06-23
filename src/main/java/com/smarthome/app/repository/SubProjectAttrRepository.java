package com.smarthome.app.repository;

import com.smarthome.app.domain.SubProjectAttr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubProjectAttr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubProjectAttrRepository extends JpaRepository<SubProjectAttr, Long> {}
