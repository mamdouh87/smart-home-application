package com.smarthome.app.web.rest;

import com.smarthome.app.repository.ProjectItemsRequirementRepository;
import com.smarthome.app.service.ProjectItemsRequirementService;
import com.smarthome.app.service.dto.ProjectItemsRequirementDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.ProjectItemsRequirement}.
 */
@RestController
@RequestMapping("/api")
public class ProjectItemsRequirementResource {

    private final Logger log = LoggerFactory.getLogger(ProjectItemsRequirementResource.class);

    private static final String ENTITY_NAME = "projectItemsRequirement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectItemsRequirementService projectItemsRequirementService;

    private final ProjectItemsRequirementRepository projectItemsRequirementRepository;

    public ProjectItemsRequirementResource(
        ProjectItemsRequirementService projectItemsRequirementService,
        ProjectItemsRequirementRepository projectItemsRequirementRepository
    ) {
        this.projectItemsRequirementService = projectItemsRequirementService;
        this.projectItemsRequirementRepository = projectItemsRequirementRepository;
    }

    /**
     * {@code POST  /project-items-requirements} : Create a new projectItemsRequirement.
     *
     * @param projectItemsRequirementDTO the projectItemsRequirementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectItemsRequirementDTO, or with status {@code 400 (Bad Request)} if the projectItemsRequirement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-items-requirements")
    public ResponseEntity<ProjectItemsRequirementDTO> createProjectItemsRequirement(
        @RequestBody ProjectItemsRequirementDTO projectItemsRequirementDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProjectItemsRequirement : {}", projectItemsRequirementDTO);
        if (projectItemsRequirementDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectItemsRequirement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectItemsRequirementDTO result = projectItemsRequirementService.save(projectItemsRequirementDTO);
        return ResponseEntity
            .created(new URI("/api/project-items-requirements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-items-requirements/:id} : Updates an existing projectItemsRequirement.
     *
     * @param id the id of the projectItemsRequirementDTO to save.
     * @param projectItemsRequirementDTO the projectItemsRequirementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectItemsRequirementDTO,
     * or with status {@code 400 (Bad Request)} if the projectItemsRequirementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectItemsRequirementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-items-requirements/{id}")
    public ResponseEntity<ProjectItemsRequirementDTO> updateProjectItemsRequirement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectItemsRequirementDTO projectItemsRequirementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectItemsRequirement : {}, {}", id, projectItemsRequirementDTO);
        if (projectItemsRequirementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectItemsRequirementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectItemsRequirementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectItemsRequirementDTO result = projectItemsRequirementService.update(projectItemsRequirementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectItemsRequirementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-items-requirements/:id} : Partial updates given fields of an existing projectItemsRequirement, field will ignore if it is null
     *
     * @param id the id of the projectItemsRequirementDTO to save.
     * @param projectItemsRequirementDTO the projectItemsRequirementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectItemsRequirementDTO,
     * or with status {@code 400 (Bad Request)} if the projectItemsRequirementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectItemsRequirementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectItemsRequirementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-items-requirements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectItemsRequirementDTO> partialUpdateProjectItemsRequirement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectItemsRequirementDTO projectItemsRequirementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectItemsRequirement partially : {}, {}", id, projectItemsRequirementDTO);
        if (projectItemsRequirementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectItemsRequirementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectItemsRequirementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectItemsRequirementDTO> result = projectItemsRequirementService.partialUpdate(projectItemsRequirementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectItemsRequirementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-items-requirements} : get all the projectItemsRequirements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectItemsRequirements in body.
     */
    @GetMapping("/project-items-requirements")
    public ResponseEntity<List<ProjectItemsRequirementDTO>> getAllProjectItemsRequirements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProjectItemsRequirements");
        Page<ProjectItemsRequirementDTO> page = projectItemsRequirementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-items-requirements/:id} : get the "id" projectItemsRequirement.
     *
     * @param id the id of the projectItemsRequirementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectItemsRequirementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-items-requirements/{id}")
    public ResponseEntity<ProjectItemsRequirementDTO> getProjectItemsRequirement(@PathVariable Long id) {
        log.debug("REST request to get ProjectItemsRequirement : {}", id);
        Optional<ProjectItemsRequirementDTO> projectItemsRequirementDTO = projectItemsRequirementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectItemsRequirementDTO);
    }

    /**
     * {@code DELETE  /project-items-requirements/:id} : delete the "id" projectItemsRequirement.
     *
     * @param id the id of the projectItemsRequirementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-items-requirements/{id}")
    public ResponseEntity<Void> deleteProjectItemsRequirement(@PathVariable Long id) {
        log.debug("REST request to delete ProjectItemsRequirement : {}", id);
        projectItemsRequirementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
