package com.smarthome.app.repository;

import com.smarthome.app.domain.SubProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubProjectRepository extends JpaRepository<SubProject, Long> {}
