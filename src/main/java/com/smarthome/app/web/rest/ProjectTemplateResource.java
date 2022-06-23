package com.smarthome.app.web.rest;

import com.smarthome.app.repository.ProjectTemplateRepository;
import com.smarthome.app.service.ProjectTemplateService;
import com.smarthome.app.service.dto.ProjectTemplateDTO;
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
 * REST controller for managing {@link com.smarthome.app.domain.ProjectTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTemplateResource.class);

    private static final String ENTITY_NAME = "projectTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTemplateService projectTemplateService;

    private final ProjectTemplateRepository projectTemplateRepository;

    public ProjectTemplateResource(ProjectTemplateService projectTemplateService, ProjectTemplateRepository projectTemplateRepository) {
        this.projectTemplateService = projectTemplateService;
        this.projectTemplateRepository = projectTemplateRepository;
    }

    /**
     * {@code POST  /project-templates} : Create a new projectTemplate.
     *
     * @param projectTemplateDTO the projectTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTemplateDTO, or with status {@code 400 (Bad Request)} if the projectTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-templates")
    public ResponseEntity<ProjectTemplateDTO> createProjectTemplate(@RequestBody ProjectTemplateDTO projectTemplateDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectTemplate : {}", projectTemplateDTO);
        if (projectTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTemplateDTO result = projectTemplateService.save(projectTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/project-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-templates/:id} : Updates an existing projectTemplate.
     *
     * @param id the id of the projectTemplateDTO to save.
     * @param projectTemplateDTO the projectTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the projectTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-templates/{id}")
    public ResponseEntity<ProjectTemplateDTO> updateProjectTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectTemplateDTO projectTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectTemplate : {}, {}", id, projectTemplateDTO);
        if (projectTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectTemplateDTO result = projectTemplateService.update(projectTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-templates/:id} : Partial updates given fields of an existing projectTemplate, field will ignore if it is null
     *
     * @param id the id of the projectTemplateDTO to save.
     * @param projectTemplateDTO the projectTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the projectTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectTemplateDTO> partialUpdateProjectTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectTemplateDTO projectTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectTemplate partially : {}, {}", id, projectTemplateDTO);
        if (projectTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectTemplateDTO> result = projectTemplateService.partialUpdate(projectTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-templates} : get all the projectTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTemplates in body.
     */
    @GetMapping("/project-templates")
    public ResponseEntity<List<ProjectTemplateDTO>> getAllProjectTemplates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProjectTemplates");
        Page<ProjectTemplateDTO> page = projectTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-templates/:id} : get the "id" projectTemplate.
     *
     * @param id the id of the projectTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-templates/{id}")
    public ResponseEntity<ProjectTemplateDTO> getProjectTemplate(@PathVariable Long id) {
        log.debug("REST request to get ProjectTemplate : {}", id);
        Optional<ProjectTemplateDTO> projectTemplateDTO = projectTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTemplateDTO);
    }

    /**
     * {@code DELETE  /project-templates/:id} : delete the "id" projectTemplate.
     *
     * @param id the id of the projectTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-templates/{id}")
    public ResponseEntity<Void> deleteProjectTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTemplate : {}", id);
        projectTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
