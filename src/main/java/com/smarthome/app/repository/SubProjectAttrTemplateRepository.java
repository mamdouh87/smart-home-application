package com.smarthome.app.repository;

import com.smarthome.app.domain.SubProjectAttrTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubProjectAttrTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubProjectAttrTemplateRepository extends JpaRepository<SubProjectAttrTemplate, Long> {}
