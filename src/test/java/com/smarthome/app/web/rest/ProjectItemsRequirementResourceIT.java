package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.ProjectItemsRequirement;
import com.smarthome.app.repository.ProjectItemsRequirementRepository;
import com.smarthome.app.service.dto.ProjectItemsRequirementDTO;
import com.smarthome.app.service.mapper.ProjectItemsRequirementMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectItemsRequirementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectItemsRequirementResourceIT {

    private static final Integer DEFAULT_QTY_NO = 1;
    private static final Integer UPDATED_QTY_NO = 2;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-items-requirements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectItemsRequirementRepository projectItemsRequirementRepository;

    @Autowired
    private ProjectItemsRequirementMapper projectItemsRequirementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectItemsRequirementMockMvc;

    private ProjectItemsRequirement projectItemsRequirement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectItemsRequirement createEntity(EntityManager em) {
        ProjectItemsRequirement projectItemsRequirement = new ProjectItemsRequirement().qtyNo(DEFAULT_QTY_NO).notes(DEFAULT_NOTES);
        return projectItemsRequirement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectItemsRequirement createUpdatedEntity(EntityManager em) {
        ProjectItemsRequirement projectItemsRequirement = new ProjectItemsRequirement().qtyNo(UPDATED_QTY_NO).notes(UPDATED_NOTES);
        return projectItemsRequirement;
    }

    @BeforeEach
    public void initTest() {
        projectItemsRequirement = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectItemsRequirement() throws Exception {
        int databaseSizeBeforeCreate = projectItemsRequirementRepository.findAll().size();
        // Create the ProjectItemsRequirement
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);
        restProjectItemsRequirementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectItemsRequirement testProjectItemsRequirement = projectItemsRequirementList.get(projectItemsRequirementList.size() - 1);
        assertThat(testProjectItemsRequirement.getQtyNo()).isEqualTo(DEFAULT_QTY_NO);
        assertThat(testProjectItemsRequirement.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createProjectItemsRequirementWithExistingId() throws Exception {
        // Create the ProjectItemsRequirement with an existing ID
        projectItemsRequirement.setId(1L);
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);

        int databaseSizeBeforeCreate = projectItemsRequirementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectItemsRequirementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectItemsRequirements() throws Exception {
        // Initialize the database
        projectItemsRequirementRepository.saveAndFlush(projectItemsRequirement);

        // Get all the projectItemsRequirementList
        restProjectItemsRequirementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectItemsRequirement.getId().intValue())))
            .andExpect(jsonPath("$.[*].qtyNo").value(hasItem(DEFAULT_QTY_NO)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getProjectItemsRequirement() throws Exception {
        // Initialize the database
        projectItemsRequirementRepository.saveAndFlush(projectItemsRequirement);

        // Get the projectItemsRequirement
        restProjectItemsRequirementMockMvc
            .perform(get(ENTITY_API_URL_ID, projectItemsRequirement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectItemsRequirement.getId().intValue()))
            .andExpect(jsonPath("$.qtyNo").value(DEFAULT_QTY_NO))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingProjectItemsRequirement() throws Exception {
        // Get the projectItemsRequirement
        restProjectItemsRequirementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectItemsRequirement() throws Exception {
        // Initialize the database
        projectItemsRequirementRepository.saveAndFlush(projectItemsRequirement);

        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();

        // Update the projectItemsRequirement
        ProjectItemsRequirement updatedProjectItemsRequirement = projectItemsRequirementRepository
            .findById(projectItemsRequirement.getId())
            .get();
        // Disconnect from session so that the updates on updatedProjectItemsRequirement are not directly saved in db
        em.detach(updatedProjectItemsRequirement);
        updatedProjectItemsRequirement.qtyNo(UPDATED_QTY_NO).notes(UPDATED_NOTES);
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(updatedProjectItemsRequirement);

        restProjectItemsRequirementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectItemsRequirementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
        ProjectItemsRequirement testProjectItemsRequirement = projectItemsRequirementList.get(projectItemsRequirementList.size() - 1);
        assertThat(testProjectItemsRequirement.getQtyNo()).isEqualTo(UPDATED_QTY_NO);
        assertThat(testProjectItemsRequirement.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingProjectItemsRequirement() throws Exception {
        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();
        projectItemsRequirement.setId(count.incrementAndGet());

        // Create the ProjectItemsRequirement
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectItemsRequirementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectItemsRequirementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectItemsRequirement() throws Exception {
        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();
        projectItemsRequirement.setId(count.incrementAndGet());

        // Create the ProjectItemsRequirement
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectItemsRequirementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectItemsRequirement() throws Exception {
        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();
        projectItemsRequirement.setId(count.incrementAndGet());

        // Create the ProjectItemsRequirement
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectItemsRequirementMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectItemsRequirementWithPatch() throws Exception {
        // Initialize the database
        projectItemsRequirementRepository.saveAndFlush(projectItemsRequirement);

        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();

        // Update the projectItemsRequirement using partial update
        ProjectItemsRequirement partialUpdatedProjectItemsRequirement = new ProjectItemsRequirement();
        partialUpdatedProjectItemsRequirement.setId(projectItemsRequirement.getId());

        partialUpdatedProjectItemsRequirement.qtyNo(UPDATED_QTY_NO);

        restProjectItemsRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectItemsRequirement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectItemsRequirement))
            )
            .andExpect(status().isOk());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
        ProjectItemsRequirement testProjectItemsRequirement = projectItemsRequirementList.get(projectItemsRequirementList.size() - 1);
        assertThat(testProjectItemsRequirement.getQtyNo()).isEqualTo(UPDATED_QTY_NO);
        assertThat(testProjectItemsRequirement.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateProjectItemsRequirementWithPatch() throws Exception {
        // Initialize the database
        projectItemsRequirementRepository.saveAndFlush(projectItemsRequirement);

        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();

        // Update the projectItemsRequirement using partial update
        ProjectItemsRequirement partialUpdatedProjectItemsRequirement = new ProjectItemsRequirement();
        partialUpdatedProjectItemsRequirement.setId(projectItemsRequirement.getId());

        partialUpdatedProjectItemsRequirement.qtyNo(UPDATED_QTY_NO).notes(UPDATED_NOTES);

        restProjectItemsRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectItemsRequirement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectItemsRequirement))
            )
            .andExpect(status().isOk());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
        ProjectItemsRequirement testProjectItemsRequirement = projectItemsRequirementList.get(projectItemsRequirementList.size() - 1);
        assertThat(testProjectItemsRequirement.getQtyNo()).isEqualTo(UPDATED_QTY_NO);
        assertThat(testProjectItemsRequirement.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingProjectItemsRequirement() throws Exception {
        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();
        projectItemsRequirement.setId(count.incrementAndGet());

        // Create the ProjectItemsRequirement
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectItemsRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectItemsRequirementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectItemsRequirement() throws Exception {
        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();
        projectItemsRequirement.setId(count.incrementAndGet());

        // Create the ProjectItemsRequirement
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectItemsRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectItemsRequirement() throws Exception {
        int databaseSizeBeforeUpdate = projectItemsRequirementRepository.findAll().size();
        projectItemsRequirement.setId(count.incrementAndGet());

        // Create the ProjectItemsRequirement
        ProjectItemsRequirementDTO projectItemsRequirementDTO = projectItemsRequirementMapper.toDto(projectItemsRequirement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectItemsRequirementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectItemsRequirementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectItemsRequirement in the database
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectItemsRequirement() throws Exception {
        // Initialize the database
        projectItemsRequirementRepository.saveAndFlush(projectItemsRequirement);

        int databaseSizeBeforeDelete = projectItemsRequirementRepository.findAll().size();

        // Delete the projectItemsRequirement
        restProjectItemsRequirementMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectItemsRequirement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectItemsRequirement> projectItemsRequirementList = projectItemsRequirementRepository.findAll();
        assertThat(projectItemsRequirementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
