package com.smarthome.app.web.rest;

import com.smarthome.app.repository.SubProjectAttrRepository;
import com.smarthome.app.service.SubProjectAttrService;
import com.smarthome.app.service.dto.SubProjectAttrDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.SubProjectAttr}.
 */
@RestController
@RequestMapping("/api")
public class SubProjectAttrResource {

    private final Logger log = LoggerFactory.getLogger(SubProjectAttrResource.class);

    private static final String ENTITY_NAME = "subProjectAttr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubProjectAttrService subProjectAttrService;

    private final SubProjectAttrRepository subProjectAttrRepository;

    public SubProjectAttrResource(SubProjectAttrService subProjectAttrService, SubProjectAttrRepository subProjectAttrRepository) {
        this.subProjectAttrService = subProjectAttrService;
        this.subProjectAttrRepository = subProjectAttrRepository;
    }

    /**
     * {@code POST  /sub-project-attrs} : Create a new subProjectAttr.
     *
     * @param subProjectAttrDTO the subProjectAttrDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subProjectAttrDTO, or with status {@code 400 (Bad Request)} if the subProjectAttr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-project-attrs")
    public ResponseEntity<SubProjectAttrDTO> createSubProjectAttr(@RequestBody SubProjectAttrDTO subProjectAttrDTO)
        throws URISyntaxException {
        log.debug("REST request to save SubProjectAttr : {}", subProjectAttrDTO);
        if (subProjectAttrDTO.getId() != null) {
            throw new BadRequestAlertException("A new subProjectAttr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubProjectAttrDTO result = subProjectAttrService.save(subProjectAttrDTO);
        return ResponseEntity
            .created(new URI("/api/sub-project-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-project-attrs/:id} : Updates an existing subProjectAttr.
     *
     * @param id the id of the subProjectAttrDTO to save.
     * @param subProjectAttrDTO the subProjectAttrDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectAttrDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectAttrDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subProjectAttrDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-project-attrs/{id}")
    public ResponseEntity<SubProjectAttrDTO> updateSubProjectAttr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectAttrDTO subProjectAttrDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubProjectAttr : {}, {}", id, subProjectAttrDTO);
        if (subProjectAttrDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectAttrDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectAttrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubProjectAttrDTO result = subProjectAttrService.update(subProjectAttrDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectAttrDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-project-attrs/:id} : Partial updates given fields of an existing subProjectAttr, field will ignore if it is null
     *
     * @param id the id of the subProjectAttrDTO to save.
     * @param subProjectAttrDTO the subProjectAttrDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectAttrDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectAttrDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subProjectAttrDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subProjectAttrDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-project-attrs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubProjectAttrDTO> partialUpdateSubProjectAttr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectAttrDTO subProjectAttrDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubProjectAttr partially : {}, {}", id, subProjectAttrDTO);
        if (subProjectAttrDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectAttrDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectAttrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubProjectAttrDTO> result = subProjectAttrService.partialUpdate(subProjectAttrDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectAttrDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-project-attrs} : get all the subProjectAttrs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subProjectAttrs in body.
     */
    @GetMapping("/sub-project-attrs")
    public ResponseEntity<List<SubProjectAttrDTO>> getAllSubProjectAttrs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SubProjectAttrs");
        Page<SubProjectAttrDTO> page = subProjectAttrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-project-attrs/:id} : get the "id" subProjectAttr.
     *
     * @param id the id of the subProjectAttrDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subProjectAttrDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-project-attrs/{id}")
    public ResponseEntity<SubProjectAttrDTO> getSubProjectAttr(@PathVariable Long id) {
        log.debug("REST request to get SubProjectAttr : {}", id);
        Optional<SubProjectAttrDTO> subProjectAttrDTO = subProjectAttrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subProjectAttrDTO);
    }

    /**
     * {@code DELETE  /sub-project-attrs/:id} : delete the "id" subProjectAttr.
     *
     * @param id the id of the subProjectAttrDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-project-attrs/{id}")
    public ResponseEntity<Void> deleteSubProjectAttr(@PathVariable Long id) {
        log.debug("REST request to delete SubProjectAttr : {}", id);
        subProjectAttrService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
