package com.smarthome.app.service;

import com.smarthome.app.domain.SubProjectAttr;
import com.smarthome.app.repository.SubProjectAttrRepository;
import com.smarthome.app.service.dto.SubProjectAttrDTO;
import com.smarthome.app.service.mapper.SubProjectAttrMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubProjectAttr}.
 */
@Service
@Transactional
public class SubProjectAttrService {

    private final Logger log = LoggerFactory.getLogger(SubProjectAttrService.class);

    private final SubProjectAttrRepository subProjectAttrRepository;

    private final SubProjectAttrMapper subProjectAttrMapper;

    public SubProjectAttrService(SubProjectAttrRepository subProjectAttrRepository, SubProjectAttrMapper subProjectAttrMapper) {
        this.subProjectAttrRepository = subProjectAttrRepository;
        this.subProjectAttrMapper = subProjectAttrMapper;
    }

    /**
     * Save a subProjectAttr.
     *
     * @param subProjectAttrDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectAttrDTO save(SubProjectAttrDTO subProjectAttrDTO) {
        log.debug("Request to save SubProjectAttr : {}", subProjectAttrDTO);
        SubProjectAttr subProjectAttr = subProjectAttrMapper.toEntity(subProjectAttrDTO);
        subProjectAttr = subProjectAttrRepository.save(subProjectAttr);
        return subProjectAttrMapper.toDto(subProjectAttr);
    }

    /**
     * Update a subProjectAttr.
     *
     * @param subProjectAttrDTO the entity to save.
     * @return the persisted entity.
     */
    public SubProjectAttrDTO update(SubProjectAttrDTO subProjectAttrDTO) {
        log.debug("Request to save SubProjectAttr : {}", subProjectAttrDTO);
        SubProjectAttr subProjectAttr = subProjectAttrMapper.toEntity(subProjectAttrDTO);
        subProjectAttr = subProjectAttrRepository.save(subProjectAttr);
        return subProjectAttrMapper.toDto(subProjectAttr);
    }

    /**
     * Partially update a subProjectAttr.
     *
     * @param subProjectAttrDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubProjectAttrDTO> partialUpdate(SubProjectAttrDTO subProjectAttrDTO) {
        log.debug("Request to partially update SubProjectAttr : {}", subProjectAttrDTO);

        return subProjectAttrRepository
            .findById(subProjectAttrDTO.getId())
            .map(existingSubProjectAttr -> {
                subProjectAttrMapper.partialUpdate(existingSubProjectAttr, subProjectAttrDTO);

                return existingSubProjectAttr;
            })
            .map(subProjectAttrRepository::save)
            .map(subProjectAttrMapper::toDto);
    }

    /**
     * Get all the subProjectAttrs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubProjectAttrDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubProjectAttrs");
        return subProjectAttrRepository.findAll(pageable).map(subProjectAttrMapper::toDto);
    }

    /**
     * Get one subProjectAttr by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubProjectAttrDTO> findOne(Long id) {
        log.debug("Request to get SubProjectAttr : {}", id);
        return subProjectAttrRepository.findById(id).map(subProjectAttrMapper::toDto);
    }

    /**
     * Delete the subProjectAttr by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubProjectAttr : {}", id);
        subProjectAttrRepository.deleteById(id);
    }
}
