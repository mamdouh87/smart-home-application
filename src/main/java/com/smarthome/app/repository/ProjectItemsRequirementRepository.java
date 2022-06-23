package com.smarthome.app.repository;

import com.smarthome.app.domain.ProjectItemsRequirement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectItemsRequirement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectItemsRequirementRepository extends JpaRepository<ProjectItemsRequirement, Long> {}
