package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.SubProjectTemplate;
import com.smarthome.app.repository.SubProjectTemplateRepository;
import com.smarthome.app.service.dto.SubProjectTemplateDTO;
import com.smarthome.app.service.mapper.SubProjectTemplateMapper;
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
 * Integration tests for the {@link SubProjectTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubProjectTemplateResourceIT {

    private static final String DEFAULT_PROJECT_TEMPLATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_TEMPLATE_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_TEMPLATE_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_TEMPLATE_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-project-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubProjectTemplateRepository subProjectTemplateRepository;

    @Autowired
    private SubProjectTemplateMapper subProjectTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubProjectTemplateMockMvc;

    private SubProjectTemplate subProjectTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProjectTemplate createEntity(EntityManager em) {
        SubProjectTemplate subProjectTemplate = new SubProjectTemplate()
            .projectTemplateCode(DEFAULT_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(DEFAULT_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(DEFAULT_PROJECT_TEMPLATE_NAME_EN);
        return subProjectTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProjectTemplate createUpdatedEntity(EntityManager em) {
        SubProjectTemplate subProjectTemplate = new SubProjectTemplate()
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);
        return subProjectTemplate;
    }

    @BeforeEach
    public void initTest() {
        subProjectTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createSubProjectTemplate() throws Exception {
        int databaseSizeBeforeCreate = subProjectTemplateRepository.findAll().size();
        // Create the SubProjectTemplate
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);
        restSubProjectTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SubProjectTemplate testSubProjectTemplate = subProjectTemplateList.get(subProjectTemplateList.size() - 1);
        assertThat(testSubProjectTemplate.getProjectTemplateCode()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProjectTemplate.getProjectTemplateNameAr()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProjectTemplate.getProjectTemplateNameEn()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void createSubProjectTemplateWithExistingId() throws Exception {
        // Create the SubProjectTemplate with an existing ID
        subProjectTemplate.setId(1L);
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);

        int databaseSizeBeforeCreate = subProjectTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubProjectTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubProjectTemplates() throws Exception {
        // Initialize the database
        subProjectTemplateRepository.saveAndFlush(subProjectTemplate);

        // Get all the subProjectTemplateList
        restSubProjectTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subProjectTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectTemplateCode").value(hasItem(DEFAULT_PROJECT_TEMPLATE_CODE)))
            .andExpect(jsonPath("$.[*].projectTemplateNameAr").value(hasItem(DEFAULT_PROJECT_TEMPLATE_NAME_AR)))
            .andExpect(jsonPath("$.[*].projectTemplateNameEn").value(hasItem(DEFAULT_PROJECT_TEMPLATE_NAME_EN)));
    }

    @Test
    @Transactional
    void getSubProjectTemplate() throws Exception {
        // Initialize the database
        subProjectTemplateRepository.saveAndFlush(subProjectTemplate);

        // Get the subProjectTemplate
        restSubProjectTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, subProjectTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subProjectTemplate.getId().intValue()))
            .andExpect(jsonPath("$.projectTemplateCode").value(DEFAULT_PROJECT_TEMPLATE_CODE))
            .andExpect(jsonPath("$.projectTemplateNameAr").value(DEFAULT_PROJECT_TEMPLATE_NAME_AR))
            .andExpect(jsonPath("$.projectTemplateNameEn").value(DEFAULT_PROJECT_TEMPLATE_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingSubProjectTemplate() throws Exception {
        // Get the subProjectTemplate
        restSubProjectTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubProjectTemplate() throws Exception {
        // Initialize the database
        subProjectTemplateRepository.saveAndFlush(subProjectTemplate);

        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();

        // Update the subProjectTemplate
        SubProjectTemplate updatedSubProjectTemplate = subProjectTemplateRepository.findById(subProjectTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedSubProjectTemplate are not directly saved in db
        em.detach(updatedSubProjectTemplate);
        updatedSubProjectTemplate
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(updatedSubProjectTemplate);

        restSubProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
        SubProjectTemplate testSubProjectTemplate = subProjectTemplateList.get(subProjectTemplateList.size() - 1);
        assertThat(testSubProjectTemplate.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProjectTemplate.getProjectTemplateNameAr()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProjectTemplate.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void putNonExistingSubProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();
        subProjectTemplate.setId(count.incrementAndGet());

        // Create the SubProjectTemplate
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();
        subProjectTemplate.setId(count.incrementAndGet());

        // Create the SubProjectTemplate
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();
        subProjectTemplate.setId(count.incrementAndGet());

        // Create the SubProjectTemplate
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectTemplateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubProjectTemplateWithPatch() throws Exception {
        // Initialize the database
        subProjectTemplateRepository.saveAndFlush(subProjectTemplate);

        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();

        // Update the subProjectTemplate using partial update
        SubProjectTemplate partialUpdatedSubProjectTemplate = new SubProjectTemplate();
        partialUpdatedSubProjectTemplate.setId(subProjectTemplate.getId());

        partialUpdatedSubProjectTemplate
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);

        restSubProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProjectTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProjectTemplate))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
        SubProjectTemplate testSubProjectTemplate = subProjectTemplateList.get(subProjectTemplateList.size() - 1);
        assertThat(testSubProjectTemplate.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProjectTemplate.getProjectTemplateNameAr()).isEqualTo(DEFAULT_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProjectTemplate.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void fullUpdateSubProjectTemplateWithPatch() throws Exception {
        // Initialize the database
        subProjectTemplateRepository.saveAndFlush(subProjectTemplate);

        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();

        // Update the subProjectTemplate using partial update
        SubProjectTemplate partialUpdatedSubProjectTemplate = new SubProjectTemplate();
        partialUpdatedSubProjectTemplate.setId(subProjectTemplate.getId());

        partialUpdatedSubProjectTemplate
            .projectTemplateCode(UPDATED_PROJECT_TEMPLATE_CODE)
            .projectTemplateNameAr(UPDATED_PROJECT_TEMPLATE_NAME_AR)
            .projectTemplateNameEn(UPDATED_PROJECT_TEMPLATE_NAME_EN);

        restSubProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProjectTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProjectTemplate))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
        SubProjectTemplate testSubProjectTemplate = subProjectTemplateList.get(subProjectTemplateList.size() - 1);
        assertThat(testSubProjectTemplate.getProjectTemplateCode()).isEqualTo(UPDATED_PROJECT_TEMPLATE_CODE);
        assertThat(testSubProjectTemplate.getProjectTemplateNameAr()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_AR);
        assertThat(testSubProjectTemplate.getProjectTemplateNameEn()).isEqualTo(UPDATED_PROJECT_TEMPLATE_NAME_EN);
    }

    @Test
    @Transactional
    void patchNonExistingSubProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();
        subProjectTemplate.setId(count.incrementAndGet());

        // Create the SubProjectTemplate
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subProjectTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();
        subProjectTemplate.setId(count.incrementAndGet());

        // Create the SubProjectTemplate
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubProjectTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectTemplateRepository.findAll().size();
        subProjectTemplate.setId(count.incrementAndGet());

        // Create the SubProjectTemplate
        SubProjectTemplateDTO subProjectTemplateDTO = subProjectTemplateMapper.toDto(subProjectTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProjectTemplate in the database
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubProjectTemplate() throws Exception {
        // Initialize the database
        subProjectTemplateRepository.saveAndFlush(subProjectTemplate);

        int databaseSizeBeforeDelete = subProjectTemplateRepository.findAll().size();

        // Delete the subProjectTemplate
        restSubProjectTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, subProjectTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubProjectTemplate> subProjectTemplateList = subProjectTemplateRepository.findAll();
        assertThat(subProjectTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
