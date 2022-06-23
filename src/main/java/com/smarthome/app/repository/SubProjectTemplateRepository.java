package com.smarthome.app.repository;

import com.smarthome.app.domain.SubProjectTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubProjectTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubProjectTemplateRepository extends JpaRepository<SubProjectTemplate, Long> {}
