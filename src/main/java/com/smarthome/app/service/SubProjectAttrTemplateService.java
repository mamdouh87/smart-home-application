package com.smarthome.app.service;

import com.smarthome.app.domain.SubProjectAttrTemplate;
import com.smarthome.app.repository.SubProjectAttrTemplateRepository;
import com.smarthome.app.service.dto.SubProjectAttrTemplateDTO;
import com.smarthome.app.service.mapper.SubProjectAttrTemplateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubProjectAttrTemplate}.
 */
@Service
@Transactional
public class SubProjectAttrTemplateService {

    private final Logger log = LoggerFactory.getLogger(SubProjectAttrTemplateService.class);

    private final SubProjectAttrTemplateRepository subProjectAttrTemplateRepository;

    private final SubProjectAttrTemplateMapper subProjectAttrTemplateMapper;

    public SubProjectAttrTemplateService(
        SubProjectAttrTemplateRepository subProjectAttrTemplateRepository,
        SubProjectAttrTemplateMapper subProjectAttrTemplateMapper
    ) {
        this.subProjectAttrTemplateRepository = subProjectAttrTemplateRepository;
        this.subProjectAttrTemplateMapper = subProjectAttrTemplateMapper;
    }

    /**
     * Save a subProjectAttrTemplate.
     *
     * @param subProjectAttrTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectAttrTemplateDTO save(SubProjectAttrTemplateDTO subProjectAttrTemplateDTO) {
        log.debug("Request to save SubProjectAttrTemplate : {}", subProjectAttrTemplateDTO);
        SubProjectAttrTemplate subProjectAttrTemplate = subProjectAttrTemplateMapper.toEntity(subProjectAttrTemplateDTO);
        subProjectAttrTemplate = subProjectAttrTemplateRepository.save(subProjectAttrTemplate);
        return subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);
    }

    /**
     * Update a subProjectAttrTemplate.
     *
     * @param subProjectAttrTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectAttrTemplateDTO update(SubProjectAttrTemplateDTO subProjectAttrTemplateDTO) {
        log.debug("Request to save SubProjectAttrTemplate : {}", subProjectAttrTemplateDTO);
        SubProjectAttrTemplate subProjectAttrTemplate = subProjectAttrTemplateMapper.toEntity(subProjectAttrTemplateDTO);
        subProjectAttrTemplate = subProjectAttrTemplateRepository.save(subProjectAttrTemplate);
        return subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);
    }

    /**
     * Partially update a subProjectAttrTemplate.
     *
     * @param subProjectAttrTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubProjectAttrTemplateDTO> partialUpdate(SubProjectAttrTemplateDTO subProjectAttrTemplateDTO) {
        log.debug("Request to partially update SubProjectAttrTemplate : {}", subProjectAttrTemplateDTO);

        return subProjectAttrTemplateRepository
            .findById(subProjectAttrTemplateDTO.getId())
            .map(existingSubProjectAttrTemplate -> {
                subProjectAttrTemplateMapper.partialUpdate(existingSubProjectAttrTemplate, subProjectAttrTemplateDTO);

                return existingSubProjectAttrTemplate;
            })
            .map(subProjectAttrTemplateRepository::save)
            .map(subProjectAttrTemplateMapper::toDto);
    }

    /**
     * Get all the subProjectAttrTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubProjectAttrTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubProjectAttrTemplates");
        return subProjectAttrTemplateRepository.findAll(pageable).map(subProjectAttrTemplateMapper::toDto);
    }

    /**
     * Get one subProjectAttrTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubProjectAttrTemplateDTO> findOne(Long id) {
        log.debug("Request to get SubProjectAttrTemplate : {}", id);
        return subProjectAttrTemplateRepository.findById(id).map(subProjectAttrTemplateMapper::toDto);
    }

    /**
     * Delete the subProjectAttrTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubProjectAttrTemplate : {}", id);
        subProjectAttrTemplateRepository.deleteById(id);
    }
}
