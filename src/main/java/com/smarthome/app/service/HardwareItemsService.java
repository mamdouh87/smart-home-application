package com.smarthome.app.service;

import com.smarthome.app.domain.HardwareItems;
import com.smarthome.app.repository.HardwareItemsRepository;
import com.smarthome.app.service.dto.HardwareItemsDTO;
import com.smarthome.app.service.mapper.HardwareItemsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HardwareItems}.
 */
@Service
@Transactional
public class HardwareItemsService {

    private final Logger log = LoggerFactory.getLogger(HardwareItemsService.class);

    private final HardwareItemsRepository hardwareItemsRepository;

    private final HardwareItemsMapper hardwareItemsMapper;

    public HardwareItemsService(HardwareItemsRepository hardwareItemsRepository, HardwareItemsMapper hardwareItemsMapper) {
        this.hardwareItemsRepository = hardwareItemsRepository;
        this.hardwareItemsMapper = hardwareItemsMapper;
    }

    /**
     * Save a hardwareItems.
     *
     * @param hardwareItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public HardwareItemsDTO save(HardwareItemsDTO hardwareItemsDTO) {
        log.debug("Request to save HardwareItems : {}", hardwareItemsDTO);
        HardwareItems hardwareItems = hardwareItemsMapper.toEntity(hardwareItemsDTO);
        hardwareItems = hardwareItemsRepository.save(hardwareItems);
        return hardwareItemsMapper.toDto(hardwareItems);
    }

    /**
     * Update a hardwareItems.
     *
     * @param hardwareItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public HardwareItemsDTO update(HardwareItemsDTO hardwareItemsDTO) {
        log.debug("Request to save HardwareItems : {}", hardwareItemsDTO);
        HardwareItems hardwareItems = hardwareItemsMapper.toEntity(hardwareItemsDTO);
        hardwareItems = hardwareItemsRepository.save(hardwareItems);
        return hardwareItemsMapper.toDto(hardwareItems);
    }

    /**
     * Partially update a hardwareItems.
     *
     * @param hardwareItemsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HardwareItemsDTO> partialUpdate(HardwareItemsDTO hardwareItemsDTO) {
        log.debug("Request to partially update HardwareItems : {}", hardwareItemsDTO);

        return hardwareItemsRepository
            .findById(hardwareItemsDTO.getId())
            .map(existingHardwareItems -> {
                hardwareItemsMapper.partialUpdate(existingHardwareItems, hardwareItemsDTO);

                return existingHardwareItems;
            })
            .map(hardwareItemsRepository::save)
            .map(hardwareItemsMapper::toDto);
    }

    /**
     * Get all the hardwareItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HardwareItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HardwareItems");
        return hardwareItemsRepository.findAll(pageable).map(hardwareItemsMapper::toDto);
    }

    /**
     * Get one hardwareItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HardwareItemsDTO> findOne(Long id) {
        log.debug("Request to get HardwareItems : {}", id);
        return hardwareItemsRepository.findById(id).map(hardwareItemsMapper::toDto);
    }

    /**
     * Delete the hardwareItems by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HardwareItems : {}", id);
        hardwareItemsRepository.deleteById(id);
    }
}
