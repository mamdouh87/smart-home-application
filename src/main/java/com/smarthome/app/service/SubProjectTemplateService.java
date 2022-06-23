package com.smarthome.app.service;

import com.smarthome.app.domain.SubProjectTemplate;
import com.smarthome.app.repository.SubProjectTemplateRepository;
import com.smarthome.app.service.dto.SubProjectTemplateDTO;
import com.smarthome.app.service.mapper.SubProjectTemplateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubProjectTemplate}.
 */
@Service
@Transactional
public class SubProjectTemplateService {

    private final Logger log = LoggerFactory.getLogger(SubProjectTemplateService.class);

    private final SubProjectTemplateRepository subProjectTemplateRepository;

    private final SubProjectTemplateMapper subProjectTemplateMapper;

    public SubProjectTemplateService(
        SubProjectTemplateRepository subProjectTemplateRepository,
        SubProjectTemplateMapper subProjectTemplateMapper
    ) {
        this.subProjectTemplateRepository = subProjectTemplateRepository;
        this.subProjectTemplateMapper = subProjectTemplateMapper;
    }

    /**
     * Save a subProjectTemplate.
     *
     * @param subProjectTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectTemplateDTO save(SubProjectTemplateDTO subProjectTemplateDTO) {
        log.debug("Request to save SubProjectTemplate : {}", subProjectTemplateDTO);
        SubProjectTemplate subProjectTemplate = subProjectTemplateMapper.toEntity(subProjectTemplateDTO);
        subProjectTemplate = subProjectTemplateRepository.save(subProjectTemplate);
        return subProjectTemplateMapper.toDto(subProjectTemplate);
    }

    /**
     * Update a subProjectTemplate.
     *
     * @param subProjectTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectTemplateDTO update(SubProjectTemplateDTO subProjectTemplateDTO) {
        log.debug("Request to save SubProjectTemplate : {}", subProjectTemplateDTO);
        SubProjectTemplate subProjectTemplate = subProjectTemplateMapper.toEntity(subProjectTemplateDTO);
        subProjectTemplate = subProjectTemplateRepository.save(subProjectTemplate);
        return subProjectTemplateMapper.toDto(subProjectTemplate);
    }

    /**
     * Partially update a subProjectTemplate.
     *
     * @param subProjectTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubProjectTemplateDTO> partialUpdate(SubProjectTemplateDTO subProjectTemplateDTO) {
        log.debug("Request to partially update SubProjectTemplate : {}", subProjectTemplateDTO);

        return subProjectTemplateRepository
            .findById(subProjectTemplateDTO.getId())
            .map(existingSubProjectTemplate -> {
                subProjectTemplateMapper.partialUpdate(existingSubProjectTemplate, subProjectTemplateDTO);

                return existingSubProjectTemplate;
            })
            .map(subProjectTemplateRepository::save)
            .map(subProjectTemplateMapper::toDto);
    }

    /**
     * Get all the subProjectTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubProjectTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubProjectTemplates");
        return subProjectTemplateRepository.findAll(pageable).map(subProjectTemplateMapper::toDto);
    }

    /**
     * Get one subProjectTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubProjectTemplateDTO> findOne(Long id) {
        log.debug("Request to get SubProjectTemplate : {}", id);
        return subProjectTemplateRepository.findById(id).map(subProjectTemplateMapper::toDto);
    }

    /**
     * Delete the subProjectTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubProjectTemplate : {}", id);
        subProjectTemplateRepository.deleteById(id);
    }
}
