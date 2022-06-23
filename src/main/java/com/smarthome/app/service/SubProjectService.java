package com.smarthome.app.service;

import com.smarthome.app.domain.SubProject;
import com.smarthome.app.repository.SubProjectRepository;
import com.smarthome.app.service.dto.SubProjectDTO;
import com.smarthome.app.service.mapper.SubProjectMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubProject}.
 */
@Service
@Transactional
public class SubProjectService {

    private final Logger log = LoggerFactory.getLogger(SubProjectService.class);

    private final SubProjectRepository subProjectRepository;

    private final SubProjectMapper subProjectMapper;

    public SubProjectService(SubProjectRepository subProjectRepository, SubProjectMapper subProjectMapper) {
        this.subProjectRepository = subProjectRepository;
        this.subProjectMapper = subProjectMapper;
    }

    /**
     * Save a subProject.
     *
     * @param subProjectDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectDTO save(SubProjectDTO subProjectDTO) {
        log.debug("Request to save SubProject : {}", subProjectDTO);
        SubProject subProject = subProjectMapper.toEntity(subProjectDTO);
        subProject = subProjectRepository.save(subProject);
        return subProjectMapper.toDto(subProject);
    }

    /**
     * Update a subProject.
     *
     * @param subProjectDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectDTO update(SubProjectDTO subProjectDTO) {
        log.debug("Request to save SubProject : {}", subProjectDTO);
        SubProject subProject = subProjectMapper.toEntity(subProjectDTO);
        subProject = subProjectRepository.save(subProject);
        return subProjectMapper.toDto(subProject);
    }

    /**
     * Partially update a subProject.
     *
     * @param subProjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubProjectDTO> partialUpdate(SubProjectDTO subProjectDTO) {
        log.debug("Request to partially update SubProject : {}", subProjectDTO);

        return subProjectRepository
            .findById(subProjectDTO.getId())
            .map(existingSubProject -> {
                subProjectMapper.partialUpdate(existingSubProject, subProjectDTO);

                return existingSubProject;
            })
            .map(subProjectRepository::save)
            .map(subProjectMapper::toDto);
    }

    /**
     * Get all the subProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubProjects");
        return subProjectRepository.findAll(pageable).map(subProjectMapper::toDto);
    }

    /**
     * Get one subProject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubProjectDTO> findOne(Long id) {
        log.debug("Request to get SubProject : {}", id);
        return subProjectRepository.findById(id).map(subProjectMapper::toDto);
    }

    /**
     * Delete the subProject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubProject : {}", id);
        subProjectRepository.deleteById(id);
    }
}
