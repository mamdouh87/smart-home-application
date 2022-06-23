package com.smarthome.app.service;

import com.smarthome.app.domain.ProjectItemsRequirement;
import com.smarthome.app.repository.ProjectItemsRequirementRepository;
import com.smarthome.app.service.dto.ProjectItemsRequirementDTO;
import com.smarthome.app.service.mapper.ProjectItemsRequirementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectItemsRequirement}.
 */
@Service
@Transactional
public class ProjectItemsRequirementService {

    private final Logger log = LoggerFactory.getLogger(ProjectItemsRequirementService.class);

    private final ProjectItemsRequirementRepository projectItemsRequirementRepository;

    private final ProjectItemsRequirementMapper projectItemsRequirementMapper;

    public ProjectItemsRequirementService(
        ProjectItemsRequirementRepository projectItemsRequirementRepository,
        ProjectItemsRequirementMapper projectItemsRequirementMapper
    ) {
        this.projectItemsRequirementRepository = projectItemsRequirementRepository;
        this.projectItemsRequirementMapper = projectItemsRequirementMapper;
    }

    /**
     * Save a projectItemsRequirement.
     *
     * @param projectItemsRequirementDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectItemsRequirementDTO save(ProjectItemsRequirementDTO projectItemsRequirementDTO) {
        log.debug("Request to save ProjectItemsRequirement : {}", projectItemsRequirementDTO);
        ProjectItemsRequirement projectItemsRequirement = projectItemsRequirementMapper.toEntity(projectItemsRequirementDTO);
        projectItemsRequirement = projectItemsRequirementRepository.save(projectItemsRequirement);
        return projectItemsRequirementMapper.toDto(projectItemsRequirement);
    }

    /**
     * Update a projectItemsRequirement.
     *
     * @param projectItemsRequirementDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectItemsRequirementDTO update(ProjectItemsRequirementDTO projectItemsRequirementDTO) {
        log.debug("Request to save ProjectItemsRequirement : {}", projectItemsRequirementDTO);
        ProjectItemsRequirement projectItemsRequirement = projectItemsRequirementMapper.toEntity(projectItemsRequirementDTO);
        projectItemsRequirement = projectItemsRequirementRepository.save(projectItemsRequirement);
        return projectItemsRequirementMapper.toDto(projectItemsRequirement);
    }

    /**
     * Partially update a projectItemsRequirement.
     *
     * @param projectItemsRequirementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectItemsRequirementDTO> partialUpdate(ProjectItemsRequirementDTO projectItemsRequirementDTO) {
        log.debug("Request to partially update ProjectItemsRequirement : {}", projectItemsRequirementDTO);

        return projectItemsRequirementRepository
            .findById(projectItemsRequirementDTO.getId())
            .map(existingProjectItemsRequirement -> {
                projectItemsRequirementMapper.partialUpdate(existingProjectItemsRequirement, projectItemsRequirementDTO);

                return existingProjectItemsRequirement;
            })
            .map(projectItemsRequirementRepository::save)
            .map(projectItemsRequirementMapper::toDto);
    }

    /**
     * Get all the projectItemsRequirements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectItemsRequirementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectItemsRequirements");
        return projectItemsRequirementRepository.findAll(pageable).map(projectItemsRequirementMapper::toDto);
    }

    /**
     * Get one projectItemsRequirement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectItemsRequirementDTO> findOne(Long id) {
        log.debug("Request to get ProjectItemsRequirement : {}", id);
        return projectItemsRequirementRepository.findById(id).map(projectItemsRequirementMapper::toDto);
    }

    /**
     * Delete the projectItemsRequirement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectItemsRequirement : {}", id);
        projectItemsRequirementRepository.deleteById(id);
    }
}
