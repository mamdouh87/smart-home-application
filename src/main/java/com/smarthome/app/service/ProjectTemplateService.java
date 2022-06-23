package com.smarthome.app.service;

import com.smarthome.app.domain.ProjectTemplate;
import com.smarthome.app.repository.ProjectTemplateRepository;
import com.smarthome.app.service.dto.ProjectTemplateDTO;
import com.smarthome.app.service.mapper.ProjectTemplateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectTemplate}.
 */
@Service
@Transactional
public class ProjectTemplateService {

    private final Logger log = LoggerFactory.getLogger(ProjectTemplateService.class);

    private final ProjectTemplateRepository projectTemplateRepository;

    private final ProjectTemplateMapper projectTemplateMapper;

    public ProjectTemplateService(ProjectTemplateRepository projectTemplateRepository, ProjectTemplateMapper projectTemplateMapper) {
        this.projectTemplateRepository = projectTemplateRepository;
        this.projectTemplateMapper = projectTemplateMapper;
    }

    /**
     * Save a projectTemplate.
     *
     * @param projectTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectTemplateDTO save(ProjectTemplateDTO projectTemplateDTO) {
        log.debug("Request to save ProjectTemplate : {}", projectTemplateDTO);
        ProjectTemplate projectTemplate = projectTemplateMapper.toEntity(projectTemplateDTO);
        projectTemplate = projectTemplateRepository.save(projectTemplate);
        return projectTemplateMapper.toDto(projectTemplate);
    }

    /**
     * Update a projectTemplate.
     *
     * @param projectTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectTemplateDTO update(ProjectTemplateDTO projectTemplateDTO) {
        log.debug("Request to save ProjectTemplate : {}", projectTemplateDTO);
        ProjectTemplate projectTemplate = projectTemplateMapper.toEntity(projectTemplateDTO);
        projectTemplate = projectTemplateRepository.save(projectTemplate);
        return projectTemplateMapper.toDto(projectTemplate);
    }

    /**
     * Partially update a projectTemplate.
     *
     * @param projectTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectTemplateDTO> partialUpdate(ProjectTemplateDTO projectTemplateDTO) {
        log.debug("Request to partially update ProjectTemplate : {}", projectTemplateDTO);

        return projectTemplateRepository
            .findById(projectTemplateDTO.getId())
            .map(existingProjectTemplate -> {
                projectTemplateMapper.partialUpdate(existingProjectTemplate, projectTemplateDTO);

                return existingProjectTemplate;
            })
            .map(projectTemplateRepository::save)
            .map(projectTemplateMapper::toDto);
    }

    /**
     * Get all the projectTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTemplates");
        return projectTemplateRepository.findAll(pageable).map(projectTemplateMapper::toDto);
    }

    /**
     * Get one projectTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTemplateDTO> findOne(Long id) {
        log.debug("Request to get ProjectTemplate : {}", id);
        return projectTemplateRepository.findById(id).map(projectTemplateMapper::toDto);
    }

    /**
     * Delete the projectTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTemplate : {}", id);
        projectTemplateRepository.deleteById(id);
    }
}
