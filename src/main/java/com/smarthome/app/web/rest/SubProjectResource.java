package com.smarthome.app.web.rest;

import com.smarthome.app.repository.SubProjectRepository;
import com.smarthome.app.service.SubProjectService;
import com.smarthome.app.service.dto.SubProjectDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.SubProject}.
 */
@RestController
@RequestMapping("/api")
public class SubProjectResource {

    private final Logger log = LoggerFactory.getLogger(SubProjectResource.class);

    private static final String ENTITY_NAME = "subProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubProjectService subProjectService;

    private final SubProjectRepository subProjectRepository;

    public SubProjectResource(SubProjectService subProjectService, SubProjectRepository subProjectRepository) {
        this.subProjectService = subProjectService;
        this.subProjectRepository = subProjectRepository;
    }

    /**
     * {@code POST  /sub-projects} : Create a new subProject.
     *
     * @param subProjectDTO the subProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subProjectDTO, or with status {@code 400 (Bad Request)} if the subProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-projects")
    public ResponseEntity<SubProjectDTO> createSubProject(@RequestBody SubProjectDTO subProjectDTO) throws URISyntaxException {
        log.debug("REST request to save SubProject : {}", subProjectDTO);
        if (subProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new subProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubProjectDTO result = subProjectService.save(subProjectDTO);
        return ResponseEntity
            .created(new URI("/api/sub-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-projects/:id} : Updates an existing subProject.
     *
     * @param id the id of the subProjectDTO to save.
     * @param subProjectDTO the subProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-projects/{id}")
    public ResponseEntity<SubProjectDTO> updateSubProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectDTO subProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubProject : {}, {}", id, subProjectDTO);
        if (subProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubProjectDTO result = subProjectService.update(subProjectDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-projects/:id} : Partial updates given fields of an existing subProject, field will ignore if it is null
     *
     * @param id the id of the subProjectDTO to save.
     * @param subProjectDTO the subProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subProjectDTO,
     * or with status {@code 400 (Bad Request)} if the subProjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subProjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubProjectDTO> partialUpdateSubProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubProjectDTO subProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubProject partially : {}, {}", id, subProjectDTO);
        if (subProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubProjectDTO> result = subProjectService.partialUpdate(subProjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subProjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-projects} : get all the subProjects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subProjects in body.
     */
    @GetMapping("/sub-projects")
    public ResponseEntity<List<SubProjectDTO>> getAllSubProjects(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SubProjects");
        Page<SubProjectDTO> page = subProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-projects/:id} : get the "id" subProject.
     *
     * @param id the id of the subProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-projects/{id}")
    public ResponseEntity<SubProjectDTO> getSubProject(@PathVariable Long id) {
        log.debug("REST request to get SubProject : {}", id);
        Optional<SubProjectDTO> subProjectDTO = subProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subProjectDTO);
    }

    /**
     * {@code DELETE  /sub-projects/:id} : delete the "id" subProject.
     *
     * @param id the id of the subProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-projects/{id}")
    public ResponseEntity<Void> deleteSubProject(@PathVariable Long id) {
        log.debug("REST request to delete SubProject : {}", id);
        subProjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
