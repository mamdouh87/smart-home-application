package com.smarthome.app.repository;

import com.smarthome.app.domain.ProjectTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTemplateRepository extends JpaRepository<ProjectTemplate, Long> {}
