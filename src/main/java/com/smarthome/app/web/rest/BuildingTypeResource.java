package com.smarthome.app.web.rest;

import com.smarthome.app.repository.BuildingTypeRepository;
import com.smarthome.app.service.BuildingTypeService;
import com.smarthome.app.service.dto.BuildingTypeDTO;
import com.smarthome.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.smarthome.app.domain.BuildingType}.
 */
@RestController
@RequestMapping("/api")
public class BuildingTypeResource {

    private final Logger log = LoggerFactory.getLogger(BuildingTypeResource.class);

    private static final String ENTITY_NAME = "buildingType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuildingTypeService buildingTypeService;

    private final BuildingTypeRepository buildingTypeRepository;

    public BuildingTypeResource(BuildingTypeService buildingTypeService, BuildingTypeRepository buildingTypeRepository) {
        this.buildingTypeService = buildingTypeService;
        this.buildingTypeRepository = buildingTypeRepository;
    }

    /**
     * {@code POST  /building-types} : Create a new buildingType.
     *
     * @param buildingTypeDTO the buildingTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buildingTypeDTO, or with status {@code 400 (Bad Request)} if the buildingType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/building-types")
    public ResponseEntity<BuildingTypeDTO> createBuildingType(@RequestBody BuildingTypeDTO buildingTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BuildingType : {}", buildingTypeDTO);
        if (buildingTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildingTypeDTO result = buildingTypeService.save(buildingTypeDTO);
        return ResponseEntity
            .created(new URI("/api/building-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /building-types/:id} : Updates an existing buildingType.
     *
     * @param id the id of the buildingTypeDTO to save.
     * @param buildingTypeDTO the buildingTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildingTypeDTO,
     * or with status {@code 400 (Bad Request)} if the buildingTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buildingTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/building-types/{id}")
    public ResponseEntity<BuildingTypeDTO> updateBuildingType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuildingTypeDTO buildingTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BuildingType : {}, {}", id, buildingTypeDTO);
        if (buildingTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buildingTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuildingTypeDTO result = buildingTypeService.update(buildingTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildingTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /building-types/:id} : Partial updates given fields of an existing buildingType, field will ignore if it is null
     *
     * @param id the id of the buildingTypeDTO to save.
     * @param buildingTypeDTO the buildingTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buildingTypeDTO,
     * or with status {@code 400 (Bad Request)} if the buildingTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buildingTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buildingTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/building-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuildingTypeDTO> partialUpdateBuildingType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuildingTypeDTO buildingTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BuildingType partially : {}, {}", id, buildingTypeDTO);
        if (buildingTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buildingTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buildingTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuildingTypeDTO> result = buildingTypeService.partialUpdate(buildingTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buildingTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /building-types} : get all the buildingTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buildingTypes in body.
     */
    @GetMapping("/building-types")
    public ResponseEntity<List<BuildingTypeDTO>> getAllBuildingTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BuildingTypes");
        Page<BuildingTypeDTO> page = buildingTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /building-types/:id} : get the "id" buildingType.
     *
     * @param id the id of the buildingTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buildingTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/building-types/{id}")
    public ResponseEntity<BuildingTypeDTO> getBuildingType(@PathVariable Long id) {
        log.debug("REST request to get BuildingType : {}", id);
        Optional<BuildingTypeDTO> buildingTypeDTO = buildingTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buildingTypeDTO);
    }

    /**
     * {@code DELETE  /building-types/:id} : delete the "id" buildingType.
     *
     * @param id the id of the buildingTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/building-types/{id}")
    public ResponseEntity<Void> deleteBuildingType(@PathVariable Long id) {
        log.debug("REST request to delete BuildingType : {}", id);
        buildingTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
