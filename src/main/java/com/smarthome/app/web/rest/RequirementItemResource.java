package com.smarthome.app.web.rest;

import com.smarthome.app.repository.RequirementItemRepository;
import com.smarthome.app.service.RequirementItemService;
import com.smarthome.app.service.dto.RequirementItemDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.RequirementItem}.
 */
@RestController
@RequestMapping("/api")
public class RequirementItemResource {

    private final Logger log = LoggerFactory.getLogger(RequirementItemResource.class);

    private static final String ENTITY_NAME = "requirementItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequirementItemService requirementItemService;

    private final RequirementItemRepository requirementItemRepository;

    public RequirementItemResource(RequirementItemService requirementItemService, RequirementItemRepository requirementItemRepository) {
        this.requirementItemService = requirementItemService;
        this.requirementItemRepository = requirementItemRepository;
    }

    /**
     * {@code POST  /requirement-items} : Create a new requirementItem.
     *
     * @param requirementItemDTO the requirementItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requirementItemDTO, or with status {@code 400 (Bad Request)} if the requirementItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requirement-items")
    public ResponseEntity<RequirementItemDTO> createRequirementItem(@RequestBody RequirementItemDTO requirementItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequirementItem : {}", requirementItemDTO);
        if (requirementItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new requirementItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequirementItemDTO result = requirementItemService.save(requirementItemDTO);
        return ResponseEntity
            .created(new URI("/api/requirement-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requirement-items/:id} : Updates an existing requirementItem.
     *
     * @param id the id of the requirementItemDTO to save.
     * @param requirementItemDTO the requirementItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requirementItemDTO,
     * or with status {@code 400 (Bad Request)} if the requirementItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requirementItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requirement-items/{id}")
    public ResponseEntity<RequirementItemDTO> updateRequirementItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequirementItemDTO requirementItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequirementItem : {}, {}", id, requirementItemDTO);
        if (requirementItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requirementItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requirementItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequirementItemDTO result = requirementItemService.update(requirementItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requirementItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requirement-items/:id} : Partial updates given fields of an existing requirementItem, field will ignore if it is null
     *
     * @param id the id of the requirementItemDTO to save.
     * @param requirementItemDTO the requirementItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requirementItemDTO,
     * or with status {@code 400 (Bad Request)} if the requirementItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requirementItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requirementItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requirement-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequirementItemDTO> partialUpdateRequirementItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequirementItemDTO requirementItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequirementItem partially : {}, {}", id, requirementItemDTO);
        if (requirementItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requirementItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requirementItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequirementItemDTO> result = requirementItemService.partialUpdate(requirementItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requirementItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /requirement-items} : get all the requirementItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requirementItems in body.
     */
    @GetMapping("/requirement-items")
    public ResponseEntity<List<RequirementItemDTO>> getAllRequirementItems(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RequirementItems");
        Page<RequirementItemDTO> page = requirementItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requirement-items/:id} : get the "id" requirementItem.
     *
     * @param id the id of the requirementItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requirementItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requirement-items/{id}")
    public ResponseEntity<RequirementItemDTO> getRequirementItem(@PathVariable Long id) {
        log.debug("REST request to get RequirementItem : {}", id);
        Optional<RequirementItemDTO> requirementItemDTO = requirementItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requirementItemDTO);
    }

    /**
     * {@code DELETE  /requirement-items/:id} : delete the "id" requirementItem.
     *
     * @param id the id of the requirementItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requirement-items/{id}")
    public ResponseEntity<Void> deleteRequirementItem(@PathVariable Long id) {
        log.debug("REST request to delete RequirementItem : {}", id);
        requirementItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
