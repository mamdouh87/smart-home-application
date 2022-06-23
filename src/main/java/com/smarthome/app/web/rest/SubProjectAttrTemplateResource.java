package com.smarthome.app.web.rest;

import com.smarthome.app.repository.SubProjectAttrTemplateRepository;
import com.smarthome.app.service.SubProjectAttrTemplateService;
import com.smarthome.app.service.dto.SubProjectAttrTemplateDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.SubProjectAttrTemplate}.
 */
@RestController
@RequestMapping("/api")
public class SubProjectAttrTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SubProjectAttrTemplateResource.class);

    private static final String ENTITY_NAME = "subProjectAttrTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubProjectAttrTemplateService subProjectAttrTemplateService;

    private final SubProjectAttrTemplateRepository subProjectAttrTemplateRepository;

    public SubProjectAttrTemplateResource(
        SubProjectAttrTemplateService subProjectAttrTemplateService,
        SubProjectAttrTemplateRepository subProjectAttrTemplateRepository
    ) {
        this.subProjectAttrTemplateService = subProjectAttrTemplateService;
        this.subProjectAttrTemplateRepository = subProjectAttrTemplateRepository;
    }

    /**
     * {@code POST  /sub-project-attr-templates} : Create a new subProjectAttrTemplate.
     *
     * @param subProjectAttrTemplateDTO the subProjectAttrTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subProjectAttrTemplateDTO, or with status {@code 400 (Bad Request)} if the subProjectAttrTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-project-attr-templates")
    public ResponseEntity<SubProjectAttrTemplateDTO> createSubProjectAttrTemplate(
        @RequestBody SubProjectAttrTemplateDTO subProjectAttrTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to save SubProjectAttrTemplate : {}", subProjectAttrTemplateDTO);
        if (subProjectAttrTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new subProjectAttrTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubProjectAttrTemplateDTO result = subProjectAttrTemplateService.save(subProjectAttrTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/sub-project-attr-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-project-attr-templates/:id} : Updates an existing subProjectAttrTemplate.
     *
     * @param id the id of the subProjectAttrTemplateDTO to save.
     * @param subProjectAttrTemplateDTO the subProjectAttrTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectAttrTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectAttrTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subProjectAttrTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-project-attr-templates/{id}")
    public ResponseEntity<SubProjectAttrTemplateDTO> updateSubProjectAttrTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectAttrTemplateDTO subProjectAttrTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubProjectAttrTemplate : {}, {}", id, subProjectAttrTemplateDTO);
        if (subProjectAttrTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectAttrTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectAttrTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubProjectAttrTemplateDTO result = subProjectAttrTemplateService.update(subProjectAttrTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectAttrTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-project-attr-templates/:id} : Partial updates given fields of an existing subProjectAttrTemplate, field will ignore if it is null
     *
     * @param id the id of the subProjectAttrTemplateDTO to save.
     * @param subProjectAttrTemplateDTO the subProjectAttrTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectAttrTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectAttrTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subProjectAttrTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subProjectAttrTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-project-attr-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubProjectAttrTemplateDTO> partialUpdateSubProjectAttrTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectAttrTemplateDTO subProjectAttrTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubProjectAttrTemplate partially : {}, {}", id, subProjectAttrTemplateDTO);
        if (subProjectAttrTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectAttrTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectAttrTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubProjectAttrTemplateDTO> result = subProjectAttrTemplateService.partialUpdate(subProjectAttrTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectAttrTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-project-attr-templates} : get all the subProjectAttrTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subProjectAttrTemplates in body.
     */
    @GetMapping("/sub-project-attr-templates")
    public ResponseEntity<List<SubProjectAttrTemplateDTO>> getAllSubProjectAttrTemplates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SubProjectAttrTemplates");
        Page<SubProjectAttrTemplateDTO> page = subProjectAttrTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-project-attr-templates/:id} : get the "id" subProjectAttrTemplate.
     *
     * @param id the id of the subProjectAttrTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subProjectAttrTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-project-attr-templates/{id}")
    public ResponseEntity<SubProjectAttrTemplateDTO> getSubProjectAttrTemplate(@PathVariable Long id) {
        log.debug("REST request to get SubProjectAttrTemplate : {}", id);
        Optional<SubProjectAttrTemplateDTO> subProjectAttrTemplateDTO = subProjectAttrTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subProjectAttrTemplateDTO);
    }

    /**
     * {@code DELETE  /sub-project-attr-templates/:id} : delete the "id" subProjectAttrTemplate.
     *
     * @param id the id of the subProjectAttrTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-project-attr-templates/{id}")
    public ResponseEntity<Void> deleteSubProjectAttrTemplate(@PathVariable Long id) {
        log.debug("REST request to delete SubProjectAttrTemplate : {}", id);
        subProjectAttrTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
