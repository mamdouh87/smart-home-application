package com.smarthome.app.service;

import com.smarthome.app.domain.RequirementItem;
import com.smarthome.app.repository.RequirementItemRepository;
import com.smarthome.app.service.dto.RequirementItemDTO;
import com.smarthome.app.service.mapper.RequirementItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequirementItem}.
 */
@Service
@Transactional
public class RequirementItemService {

    private final Logger log = LoggerFactory.getLogger(RequirementItemService.class);

    private final RequirementItemRepository requirementItemRepository;

    private final RequirementItemMapper requirementItemMapper;

    public RequirementItemService(RequirementItemRepository requirementItemRepository, RequirementItemMapper requirementItemMapper) {
        this.requirementItemRepository = requirementItemRepository;
        this.requirementItemMapper = requirementItemMapper;
    }

    /**
     * Save a requirementItem.
     *
     * @param requirementItemDTO the entity to save.
     * @return the persisted entity.
     */
    public RequirementItemDTO save(RequirementItemDTO requirementItemDTO) {
        log.debug("Request to save RequirementItem : {}", requirementItemDTO);
        RequirementItem requirementItem = requirementItemMapper.toEntity(requirementItemDTO);
        requirementItem = requirementItemRepository.save(requirementItem);
        return requirementItemMapper.toDto(requirementItem);
    }

    /**
     * Update a requirementItem.
     *
     * @param requirementItemDTO the entity to save.
     * @return the persisted entity.
     */
    public RequirementItemDTO update(RequirementItemDTO requirementItemDTO) {
        log.debug("Request to save RequirementItem : {}", requirementItemDTO);
        RequirementItem requirementItem = requirementItemMapper.toEntity(requirementItemDTO);
        requirementItem = requirementItemRepository.save(requirementItem);
        return requirementItemMapper.toDto(requirementItem);
    }

    /**
     * Partially update a requirementItem.
     *
     * @param requirementItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RequirementItemDTO> partialUpdate(RequirementItemDTO requirementItemDTO) {
        log.debug("Request to partially update RequirementItem : {}", requirementItemDTO);

        return requirementItemRepository
            .findById(requirementItemDTO.getId())
            .map(existingRequirementItem -> {
                requirementItemMapper.partialUpdate(existingRequirementItem, requirementItemDTO);

                return existingRequirementItem;
            })
            .map(requirementItemRepository::save)
            .map(requirementItemMapper::toDto);
    }

    /**
     * Get all the requirementItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequirementItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequirementItems");
        return requirementItemRepository.findAll(pageable).map(requirementItemMapper::toDto);
    }

    /**
     * Get one requirementItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequirementItemDTO> findOne(Long id) {
        log.debug("Request to get RequirementItem : {}", id);
        return requirementItemRepository.findById(id).map(requirementItemMapper::toDto);
    }

    /**
     * Delete the requirementItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RequirementItem : {}", id);
        requirementItemRepository.deleteById(id);
    }
}
