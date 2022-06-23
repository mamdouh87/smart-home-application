package com.smarthome.app.web.rest;

import com.smarthome.app.repository.SubProjectTemplateRepository;
import com.smarthome.app.service.SubProjectTemplateService;
import com.smarthome.app.service.dto.SubProjectTemplateDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.SubProjectTemplate}.
 */
@RestController
@RequestMapping("/api")
public class SubProjectTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SubProjectTemplateResource.class);

    private static final String ENTITY_NAME = "subProjectTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubProjectTemplateService subProjectTemplateService;

    private final SubProjectTemplateRepository subProjectTemplateRepository;

    public SubProjectTemplateResource(
        SubProjectTemplateService subProjectTemplateService,
        SubProjectTemplateRepository subProjectTemplateRepository
    ) {
        this.subProjectTemplateService = subProjectTemplateService;
        this.subProjectTemplateRepository = subProjectTemplateRepository;
    }

    /**
     * {@code POST  /sub-project-templates} : Create a new subProjectTemplate.
     *
     * @param subProjectTemplateDTO the subProjectTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subProjectTemplateDTO, or with status {@code 400 (Bad Request)} if the subProjectTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-project-templates")
    public ResponseEntity<SubProjectTemplateDTO> createSubProjectTemplate(@RequestBody SubProjectTemplateDTO subProjectTemplateDTO)
        throws URISyntaxException {
        log.debug("REST request to save SubProjectTemplate : {}", subProjectTemplateDTO);
        if (subProjectTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new subProjectTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubProjectTemplateDTO result = subProjectTemplateService.save(subProjectTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/sub-project-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-project-templates/:id} : Updates an existing subProjectTemplate.
     *
     * @param id the id of the subProjectTemplateDTO to save.
     * @param subProjectTemplateDTO the subProjectTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subProjectTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-project-templates/{id}")
    public ResponseEntity<SubProjectTemplateDTO> updateSubProjectTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectTemplateDTO subProjectTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubProjectTemplate : {}, {}", id, subProjectTemplateDTO);
        if (subProjectTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubProjectTemplateDTO result = subProjectTemplateService.update(subProjectTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-project-templates/:id} : Partial updates given fields of an existing subProjectTemplate, field will ignore if it is null
     *
     * @param id the id of the subProjectTemplateDTO to save.
     * @param subProjectTemplateDTO the subProjectTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subProjectTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subProjectTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-project-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubProjectTemplateDTO> partialUpdateSubProjectTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectTemplateDTO subProjectTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubProjectTemplate partially : {}, {}", id, subProjectTemplateDTO);
        if (subProjectTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubProjectTemplateDTO> result = subProjectTemplateService.partialUpdate(subProjectTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-project-templates} : get all the subProjectTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subProjectTemplates in body.
     */
    @GetMapping("/sub-project-templates")
    public ResponseEntity<List<SubProjectTemplateDTO>> getAllSubProjectTemplates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SubProjectTemplates");
        Page<SubProjectTemplateDTO> page = subProjectTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-project-templates/:id} : get the "id" subProjectTemplate.
     *
     * @param id the id of the subProjectTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subProjectTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-project-templates/{id}")
    public ResponseEntity<SubProjectTemplateDTO> getSubProjectTemplate(@PathVariable Long id) {
        log.debug("REST request to get SubProjectTemplate : {}", id);
        Optional<SubProjectTemplateDTO> subProjectTemplateDTO = subProjectTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subProjectTemplateDTO);
    }

    /**
     * {@code DELETE  /sub-project-templates/:id} : delete the "id" subProjectTemplate.
     *
     * @param id the id of the subProjectTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-project-templates/{id}")
    public ResponseEntity<Void> deleteSubProjectTemplate(@PathVariable Long id) {
        log.debug("REST request to delete SubProjectTemplate : {}", id);
        subProjectTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
