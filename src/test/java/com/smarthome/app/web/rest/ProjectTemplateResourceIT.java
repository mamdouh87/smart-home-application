package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.ProjectTemplate;
import com.smarthome.app.repository.ProjectTemplateRepository;
import com.smarthome.app.service.dto.ProjectTemplateDTO;
import com.smarthome.app.service.mapper.ProjectTemplateMapper;
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
 * Integration tests for the {@link ProjectTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectTemplateResourceIT {

    private static final String DEFAULT_PROJECT_TEMPLATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_TEMPLATE_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_TEMPLATE_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectTemplateRepository projectTemplateRepository;

    @Autowired
    private ProjectTemplateMapper projectTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectTemplateMockMvc;

    private ProjectTemplate projectTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTemplate createEntity(EntityManager em) {
        ProjectTemplate projectTemplate = new ProjectTemplate()
            .projectTemplateCode(DEFAULT_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(DEFAULT_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(DEFAULT_PROJECT_TEMPLATE_NAME_EN);
        return projectTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTemplate createUpdatedEntity(EntityManager em) {
        ProjectTemplate projectTemplate = new ProjectTemplate()
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);
        return projectTemplate;
    }

    @BeforeEach
    public void initTest() {
        projectTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectTemplate() throws Exception {
        int databaseSizeBeforeCreate = projectTemplateRepository.findAll().size();
        // Create the ProjectTemplate
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);
        restProjectTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTemplate testProjectTemplate = projectTemplateList.get(projectTemplateList.size() - 1);
        assertThat(testProjectTemplate.getProjectTemplateCode()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_CODE);
        assertThat(testProjectTemplate.getProjectTemplateNameAr()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testProjectTemplate.getProjectTemplateNameEn()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void createProjectTemplateWithExistingId() throws Exception {
        // Create the ProjectTemplate with an existing ID
        projectTemplate.setId(1L);
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);

        int databaseSizeBeforeCreate = projectTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectTemplates() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        // Get all the projectTemplateList
        restProjectTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectTemplateCode").value(hasItem(DEFAULT_PROJECT_TEMPLATE_CODE)))
            .andExpect(jsonPath("$.[*].projectTemplateNameAr").value(hasItem(DEFAULT_PROJECT_TEMPLATE_NAME_AR)))
            .andExpect(jsonPath("$.[*].projectTemplateNameEn").value(hasItem(DEFAULT_PROJECT_TEMPLATE_NAME_EN)));
    }

    @Test
    @Transactional
    void getProjectTemplate() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        // Get the projectTemplate
        restProjectTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, projectTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectTemplate.getId().intValue()))
            .andExpect(jsonPath("$.projectTemplateCode").value(DEFAULT_PROJECT_TEMPLATE_CODE))
            .andExpect(jsonPath("$.projectTemplateNameAr").value(DEFAULT_PROJECT_TEMPLATE_NAME_AR))
            .andExpect(jsonPath("$.projectTemplateNameEn").value(DEFAULT_PROJECT_TEMPLATE_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingProjectTemplate() throws Exception {
        // Get the projectTemplate
        restProjectTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectTemplate() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();

        // Update the projectTemplate
        ProjectTemplate updatedProjectTemplate = projectTemplateRepository.findById(projectTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedProjectTemplate are not directly saved in db
        em.detach(updatedProjectTemplate);
        updatedProjectTemplate
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(updatedProjectTemplate);

        restProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProjectTemplate testProjectTemplate = projectTemplateList.get(projectTemplateList.size() - 1);
        assertThat(testProjectTemplate.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testProjectTemplate.getProjectTemplateNameAr()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testProjectTemplate.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void putNonExistingProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();
        projectTemplate.setId(count.incrementAndGet());

        // Create the ProjectTemplate
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();
        projectTemplate.setId(count.incrementAndGet());

        // Create the ProjectTemplate
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();
        projectTemplate.setId(count.incrementAndGet());

        // Create the ProjectTemplate
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectTemplateWithPatch() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();

        // Update the projectTemplate using partial update
        ProjectTemplate partialUpdatedProjectTemplate = new ProjectTemplate();
        partialUpdatedProjectTemplate.setId(projectTemplate.getId());

        partialUpdatedProjectTemplate.projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE);

        restProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProjectTemplate testProjectTemplate = projectTemplateList.get(projectTemplateList.size() - 1);
        assertThat(testProjectTemplate.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testProjectTemplate.getProjectTemplateNameAr()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testProjectTemplate.getProjectTemplateNameEn()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void fullUpdateProjectTemplateWithPatch() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();

        // Update the projectTemplate using partial update
        ProjectTemplate partialUpdatedProjectTemplate = new ProjectTemplate();
        partialUpdatedProjectTemplate.setId(projectTemplate.getId());

        partialUpdatedProjectTemplate
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);

        restProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProjectTemplate testProjectTemplate = projectTemplateList.get(projectTemplateList.size() - 1);
        assertThat(testProjectTemplate.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testProjectTemplate.getProjectTemplateNameAr()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testProjectTemplate.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void patchNonExistingProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();
        projectTemplate.setId(count.incrementAndGet());

        // Create the ProjectTemplate
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();
        projectTemplate.setId(count.incrementAndGet());

        // Create the ProjectTemplate
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = projectTemplateRepository.findAll().size();
        projectTemplate.setId(count.incrementAndGet());

        // Create the ProjectTemplate
        ProjectTemplateDTO projectTemplateDTO = projectTemplateMapper.toDto(projectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectTemplate in the database
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectTemplate() throws Exception {
        // Initialize the database
        projectTemplateRepository.saveAndFlush(projectTemplate);

        int databaseSizeBeforeDelete = projectTemplateRepository.findAll().size();

        // Delete the projectTemplate
        restProjectTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectTemplate> projectTemplateList = projectTemplateRepository.findAll();
        assertThat(projectTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
