package com.smarthome.app.repository;

import com.smarthome.app.domain.RequirementItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RequirementItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequirementItemRepository extends JpaRepository<RequirementItem, Long> {}
