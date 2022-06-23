package com.smarthome.app.web.rest;

import com.smarthome.app.repository.HardwareItemsRepository;
import com.smarthome.app.service.HardwareItemsService;
import com.smarthome.app.service.dto.HardwareItemsDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.HardwareItems}.
 */
@RestController
@RequestMapping("/api")
public class HardwareItemsResource {

    private final Logger log = LoggerFactory.getLogger(HardwareItemsResource.class);

    private static final String ENTITY_NAME = "hardwareItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HardwareItemsService hardwareItemsService;

    private final HardwareItemsRepository hardwareItemsRepository;

    public HardwareItemsResource(HardwareItemsService hardwareItemsService, HardwareItemsRepository hardwareItemsRepository) {
        this.hardwareItemsService = hardwareItemsService;
        this.hardwareItemsRepository = hardwareItemsRepository;
    }

    /**
     * {@code POST  /hardware-items} : Create a new hardwareItems.
     *
     * @param hardwareItemsDTO the hardwareItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hardwareItemsDTO, or with status {@code 400 (Bad Request)} if the hardwareItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hardware-items")
    public ResponseEntity<HardwareItemsDTO> createHardwareItems(@RequestBody HardwareItemsDTO hardwareItemsDTO) throws URISyntaxException {
        log.debug("REST request to save HardwareItems : {}", hardwareItemsDTO);
        if (hardwareItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new hardwareItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HardwareItemsDTO result = hardwareItemsService.save(hardwareItemsDTO);
        return ResponseEntity
            .created(new URI("/api/hardware-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hardware-items/:id} : Updates an existing hardwareItems.
     *
     * @param id the id of the hardwareItemsDTO to save.
     * @param hardwareItemsDTO the hardwareItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hardwareItemsDTO,
     * or with status {@code 400 (Bad Request)} if the hardwareItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hardwareItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hardware-items/{id}")
    public ResponseEntity<HardwareItemsDTO> updateHardwareItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HardwareItemsDTO hardwareItemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HardwareItems : {}, {}", id, hardwareItemsDTO);
        if (hardwareItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hardwareItemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hardwareItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HardwareItemsDTO result = hardwareItemsService.update(hardwareItemsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hardwareItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hardware-items/:id} : Partial updates given fields of an existing hardwareItems, field will ignore if it is null
     *
     * @param id the id of the hardwareItemsDTO to save.
     * @param hardwareItemsDTO the hardwareItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hardwareItemsDTO,
     * or with status {@code 400 (Bad Request)} if the hardwareItemsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hardwareItemsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hardwareItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hardware-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HardwareItemsDTO> partialUpdateHardwareItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HardwareItemsDTO hardwareItemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HardwareItems partially : {}, {}", id, hardwareItemsDTO);
        if (hardwareItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hardwareItemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hardwareItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HardwareItemsDTO> result = hardwareItemsService.partialUpdate(hardwareItemsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hardwareItemsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hardware-items} : get all the hardwareItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hardwareItems in body.
     */
    @GetMapping("/hardware-items")
    public ResponseEntity<List<HardwareItemsDTO>> getAllHardwareItems(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of HardwareItems");
        Page<HardwareItemsDTO> page = hardwareItemsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hardware-items/:id} : get the "id" hardwareItems.
     *
     * @param id the id of the hardwareItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hardwareItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hardware-items/{id}")
    public ResponseEntity<HardwareItemsDTO> getHardwareItems(@PathVariable Long id) {
        log.debug("REST request to get HardwareItems : {}", id);
        Optional<HardwareItemsDTO> hardwareItemsDTO = hardwareItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hardwareItemsDTO);
    }

    /**
     * {@code DELETE  /hardware-items/:id} : delete the "id" hardwareItems.
     *
     * @param id the id of the hardwareItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hardware-items/{id}")
    public ResponseEntity<Void> deleteHardwareItems(@PathVariable Long id) {
        log.debug("REST request to delete HardwareItems : {}", id);
        hardwareItemsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
