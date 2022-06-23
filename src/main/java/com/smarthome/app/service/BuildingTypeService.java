package com.smarthome.app.service;

import com.smarthome.app.domain.BuildingType;
import com.smarthome.app.repository.BuildingTypeRepository;
import com.smarthome.app.service.dto.BuildingTypeDTO;
import com.smarthome.app.service.mapper.BuildingTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BuildingType}.
 */
@Service
@Transactional
public class BuildingTypeService {

    private final Logger log = LoggerFactory.getLogger(BuildingTypeService.class);

    private final BuildingTypeRepository buildingTypeRepository;

    private final BuildingTypeMapper buildingTypeMapper;

    public BuildingTypeService(BuildingTypeRepository buildingTypeRepository, BuildingTypeMapper buildingTypeMapper) {
        this.buildingTypeRepository = buildingTypeRepository;
        this.buildingTypeMapper = buildingTypeMapper;
    }

    /**
     * Save a buildingType.
     *
     * @param buildingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BuildingTypeDTO save(BuildingTypeDTO buildingTypeDTO) {
        log.debug("Request to save BuildingType : {}", buildingTypeDTO);
        BuildingType buildingType = buildingTypeMapper.toEntity(buildingTypeDTO);
        buildingType = buildingTypeRepository.save(buildingType);
        return buildingTypeMapper.toDto(buildingType);
    }

    /**
     * Update a buildingType.
     *
     * @param buildingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BuildingTypeDTO update(BuildingTypeDTO buildingTypeDTO) {
        log.debug("Request to save BuildingType : {}", buildingTypeDTO);
        BuildingType buildingType = buildingTypeMapper.toEntity(buildingTypeDTO);
        buildingType = buildingTypeRepository.save(buildingType);
        return buildingTypeMapper.toDto(buildingType);
    }

    /**
     * Partially update a buildingType.
     *
     * @param buildingTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BuildingTypeDTO> partialUpdate(BuildingTypeDTO buildingTypeDTO) {
        log.debug("Request to partially update BuildingType : {}", buildingTypeDTO);

        return buildingTypeRepository
            .findById(buildingTypeDTO.getId())
            .map(existingBuildingType -> {
                buildingTypeMapper.partialUpdate(existingBuildingType, buildingTypeDTO);

                return existingBuildingType;
            })
            .map(buildingTypeRepository::save)
            .map(buildingTypeMapper::toDto);
    }

    /**
     * Get all the buildingTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BuildingTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BuildingTypes");
        return buildingTypeRepository.findAll(pageable).map(buildingTypeMapper::toDto);
    }

    /**
     * Get one buildingType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BuildingTypeDTO> findOne(Long id) {
        log.debug("Request to get BuildingType : {}", id);
        return buildingTypeRepository.findById(id).map(buildingTypeMapper::toDto);
    }

    /**
     * Delete the buildingType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BuildingType : {}", id);
        buildingTypeRepository.deleteById(id);
    }
}
