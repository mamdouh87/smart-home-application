package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.SubProject;
import com.smarthome.app.repository.SubProjectRepository;
import com.smarthome.app.service.dto.SubProjectDTO;
import com.smarthome.app.service.mapper.SubProjectMapper;
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
 * Integration tests for the {@link SubProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubProjectResourceIT {

    private static final String DEFAULT_PROJECT_TEMPLATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_TEMPLATE_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_TEMPLATE_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private SubProjectMapper subProjectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubProjectMockMvc;

    private SubProject subProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProject createEntity(EntityManager em) {
        SubProject subProject = new SubProject()
            .projectTemplateCode(DEFAULT_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(DEFAULT_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(DEFAULT_PROJECT_TEMPLATE_NAME_EN);
        return subProject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProject createUpdatedEntity(EntityManager em) {
        SubProject subProject = new SubProject()
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);
        return subProject;
    }

    @BeforeEach
    public void initTest() {
        subProject = createEntity(em);
    }

    @Test
    @Transactional
    void createSubProject() throws Exception {
        int databaseSizeBeforeCreate = subProjectRepository.findAll().size();
        // Create the SubProject
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);
        restSubProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subProjectDTO)))
            .andExpect(status().isCreated());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeCreate + 1);
        SubProject testSubProject = subProjectList.get(subProjectList.size() - 1);
        assertThat(testSubProject.getProjectTemplateCode()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProject.getProjectTemplateNameAr()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProject.getProjectTemplateNameEn()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void createSubProjectWithExistingId() throws Exception {
        // Create the SubProject with an existing ID
        subProject.setId(1L);
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);

        int databaseSizeBeforeCreate = subProjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubProjects() throws Exception {
        // Initialize the database
        subProjectRepository.saveAndFlush(subProject);

        // Get all the subProjectList
        restSubProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectTemplateCode").value(hasItem(DEFAULT_PROJECT_TEMPLATE_CODE)))
            .andExpect(jsonPath("$.[*].projectTemplateNameAr").value(hasItem(DEFAULT_PROJECT_TEMPLATE_NAME_AR)))
            .andExpect(jsonPath("$.[*].projectTemplateNameEn").value(hasItem(DEFAULT_PROJECT_TEMPLATE_NAME_EN)));
    }

    @Test
    @Transactional
    void getSubProject() throws Exception {
        // Initialize the database
        subProjectRepository.saveAndFlush(subProject);

        // Get the subProject
        restSubProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, subProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subProject.getId().intValue()))
            .andExpect(jsonPath("$.projectTemplateCode").value(DEFAULT_PROJECT_TEMPLATE_CODE))
            .andExpect(jsonPath("$.projectTemplateNameAr").value(DEFAULT_PROJECT_TEMPLATE_NAME_AR))
            .andExpect(jsonPath("$.projectTemplateNameEn").value(DEFAULT_PROJECT_TEMPLATE_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingSubProject() throws Exception {
        // Get the subProject
        restSubProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubProject() throws Exception {
        // Initialize the database
        subProjectRepository.saveAndFlush(subProject);

        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();

        // Update the subProject
        SubProject updatedSubProject = subProjectRepository.findById(subProject.getId()).get();
        // Disconnect from session so that the updates on updatedSubProject are not directly saved in db
        em.detach(updatedSubProject);
        updatedSubProject
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(updatedSubProject);

        restSubProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
        SubProject testSubProject = subProjectList.get(subProjectList.size() - 1);
        assertThat(testSubProject.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProject.getProjectTemplateNameAr()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProject.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void putNonExistingSubProject() throws Exception {
        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();
        subProject.setId(count.incrementAndGet());

        // Create the SubProject
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubProject() throws Exception {
        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();
        subProject.setId(count.incrementAndGet());

        // Create the SubProject
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubProject() throws Exception {
        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();
        subProject.setId(count.incrementAndGet());

        // Create the SubProject
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subProjectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubProjectWithPatch() throws Exception {
        // Initialize the database
        subProjectRepository.saveAndFlush(subProject);

        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();

        // Update the subProject using partial update
        SubProject partialUpdatedSubProject = new SubProject();
        partialUpdatedSubProject.setId(subProject.getId());

        partialUpdatedSubProject
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);

        restSubProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProject))
            )
            .andExpect(status().isOk());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
        SubProject testSubProject = subProjectList.get(subProjectList.size() - 1);
        assertThat(testSubProject.getProjectTemplateCode()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProject.getProjectTemplateNameAr()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProject.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void fullUpdateSubProjectWithPatch() throws Exception {
        // Initialize the database
        subProjectRepository.saveAndFlush(subProject);

        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();

        // Update the subProject using partial update
        SubProject partialUpdatedSubProject = new SubProject();
        partialUpdatedSubProject.setId(subProject.getId());

        partialUpdatedSubProject
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);

        restSubProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProject))
            )
            .andExpect(status().isOk());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
        SubProject testSubProject = subProjectList.get(subProjectList.size() - 1);
        assertThat(testSubProject.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProject.getProjectTemplateNameAr()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProject.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void patchNonExistingSubProject() throws Exception {
        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();
        subProject.setId(count.incrementAndGet());

        // Create the SubProject
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subProjectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubProject() throws Exception {
        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();
        subProject.setId(count.incrementAndGet());

        // Create the SubProject
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubProject() throws Exception {
        int databaseSizeBeforeUpdate = subProjectRepository.findAll().size();
        subProject.setId(count.incrementAndGet());

        // Create the SubProject
        SubProjectDTO subProjectDTO = subProjectMapper.toDto(subProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subProjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProject in the database
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubProject() throws Exception {
        // Initialize the database
        subProjectRepository.saveAndFlush(subProject);

        int databaseSizeBeforeDelete = subProjectRepository.findAll().size();

        // Delete the subProject
        restSubProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, subProject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubProject> subProjectList = subProjectRepository.findAll();
        assertThat(subProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
