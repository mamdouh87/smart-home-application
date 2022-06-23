package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.SubProjectAttrTemplate;
import com.smarthome.app.repository.SubProjectAttrTemplateRepository;
import com.smarthome.app.service.dto.SubProjectAttrTemplateDTO;
import com.smarthome.app.service.mapper.SubProjectAttrTemplateMapper;
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
 * Integration tests for the {@link SubProjectAttrTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubProjectAttrTemplateResourceIT {

    private static final String DEFAULT_ATTR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_CODE_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CODE_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_CODE_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CODE_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-project-attr-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubProjectAttrTemplateRepository subProjectAttrTemplateRepository;

    @Autowired
    private SubProjectAttrTemplateMapper subProjectAttrTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubProjectAttrTemplateMockMvc;

    private SubProjectAttrTemplate subProjectAttrTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProjectAttrTemplate createEntity(EntityManager em) {
        SubProjectAttrTemplate subProjectAttrTemplate = new SubProjectAttrTemplate()
            .attrCode(DEFAULT_ATTR_CODE)
            .attrCodeNameAr(DEFAULT_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(DEFAULT_ATTR_CODE_NAME_EN)
            .attrType(DEFAULT_ATTR_TYPE);
        return subProjectAttrTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProjectAttrTemplate createUpdatedEntity(EntityManager em) {
        SubProjectAttrTemplate subProjectAttrTemplate = new SubProjectAttrTemplate()
            .attrCode(UPDATED_ATTR_CODE)
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE);
        return subProjectAttrTemplate;
    }

    @BeforeEach
    public void initTest() {
        subProjectAttrTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createSubProjectAttrTemplate() throws Exception {
        int databaseSizeBeforeCreate = subProjectAttrTemplateRepository.findAll().size();
        // Create the SubProjectAttrTemplate
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);
        restSubProjectAttrTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SubProjectAttrTemplate testSubProjectAttrTemplate = subProjectAttrTemplateList.get(subProjectAttrTemplateList.size() - 1);
        assertThat(testSubProjectAttrTemplate.getAttrCode()).isEqualTo(DEFAULT_ATTR_CODE);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameAr()).isEqualTo(DEFAULT_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameEn()).isEqualTo(DEFAULT_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttrTemplate.getAttrType()).isEqualTo(DEFAULT_ATTR_TYPE);
    }

    @Test
    @Transactional
    void createSubProjectAttrTemplateWithExistingId() throws Exception {
        // Create the SubProjectAttrTemplate with an existing ID
        subProjectAttrTemplate.setId(1L);
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);

        int databaseSizeBeforeCreate = subProjectAttrTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubProjectAttrTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubProjectAttrTemplates() throws Exception {
        // Initialize the database
        subProjectAttrTemplateRepository.saveAndFlush(subProjectAttrTemplate);

        // Get all the subProjectAttrTemplateList
        restSubProjectAttrTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subProjectAttrTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].attrCode").value(hasItem(DEFAULT_ATTR_CODE)))
            .andExpect(jsonPath("$.[*].attrCodeNameAr").value(hasItem(DEFAULT_ATTR_CODE_NAME_AR)))
            .andExpect(jsonPath("$.[*].attrCodeNameEn").value(hasItem(DEFAULT_ATTR_CODE_NAME_EN)))
            .andExpect(jsonPath("$.[*].attrType").value(hasItem(DEFAULT_ATTR_TYPE)));
    }

    @Test
    @Transactional
    void getSubProjectAttrTemplate() throws Exception {
        // Initialize the database
        subProjectAttrTemplateRepository.saveAndFlush(subProjectAttrTemplate);

        // Get the subProjectAttrTemplate
        restSubProjectAttrTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, subProjectAttrTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subProjectAttrTemplate.getId().intValue()))
            .andExpect(jsonPath("$.attrCode").value(DEFAULT_ATTR_CODE))
            .andExpect(jsonPath("$.attrCodeNameAr").value(DEFAULT_ATTR_CODE_NAME_AR))
            .andExpect(jsonPath("$.attrCodeNameEn").value(DEFAULT_ATTR_CODE_NAME_EN))
            .andExpect(jsonPath("$.attrType").value(DEFAULT_ATTR_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingSubProjectAttrTemplate() throws Exception {
        // Get the subProjectAttrTemplate
        restSubProjectAttrTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubProjectAttrTemplate() throws Exception {
        // Initialize the database
        subProjectAttrTemplateRepository.saveAndFlush(subProjectAttrTemplate);

        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();

        // Update the subProjectAttrTemplate
        SubProjectAttrTemplate updatedSubProjectAttrTemplate = subProjectAttrTemplateRepository
            .findById(subProjectAttrTemplate.getId())
            .get();
        // Disconnect from session so that the updates on updatedSubProjectAttrTemplate are not directly saved in db
        em.detach(updatedSubProjectAttrTemplate);
        updatedSubProjectAttrTemplate
            .attrCode(UPDATED_ATTR_CODE)
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE);
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(updatedSubProjectAttrTemplate);

        restSubProjectAttrTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectAttrTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
        SubProjectAttrTemplate testSubProjectAttrTemplate = subProjectAttrTemplateList.get(subProjectAttrTemplateList.size() - 1);
        assertThat(testSubProjectAttrTemplate.getAttrCode()).isEqualTo(UPDATED_ATTR_CODE);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameAr()).isEqualTo(UPDATED_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameEn()).isEqualTo(UPDATED_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttrTemplate.getAttrType()).isEqualTo(UPDATED_ATTR_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSubProjectAttrTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();
        subProjectAttrTemplate.setId(count.incrementAndGet());

        // Create the SubProjectAttrTemplate
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectAttrTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectAttrTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubProjectAttrTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();
        subProjectAttrTemplate.setId(count.incrementAndGet());

        // Create the SubProjectAttrTemplate
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubProjectAttrTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();
        subProjectAttrTemplate.setId(count.incrementAndGet());

        // Create the SubProjectAttrTemplate
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrTemplateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubProjectAttrTemplateWithPatch() throws Exception {
        // Initialize the database
        subProjectAttrTemplateRepository.saveAndFlush(subProjectAttrTemplate);

        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();

        // Update the subProjectAttrTemplate using partial update
        SubProjectAttrTemplate partialUpdatedSubProjectAttrTemplate = new SubProjectAttrTemplate();
        partialUpdatedSubProjectAttrTemplate.setId(subProjectAttrTemplate.getId());

        partialUpdatedSubProjectAttrTemplate
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE);

        restSubProjectAttrTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProjectAttrTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProjectAttrTemplate))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
        SubProjectAttrTemplate testSubProjectAttrTemplate = subProjectAttrTemplateList.get(subProjectAttrTemplateList.size() - 1);
        assertThat(testSubProjectAttrTemplate.getAttrCode()).isEqualTo(DEFAULT_ATTR_CODE);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameAr()).isEqualTo(UPDATED_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameEn()).isEqualTo(UPDATED_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttrTemplate.getAttrType()).isEqualTo(UPDATED_ATTR_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSubProjectAttrTemplateWithPatch() throws Exception {
        // Initialize the database
        subProjectAttrTemplateRepository.saveAndFlush(subProjectAttrTemplate);

        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();

        // Update the subProjectAttrTemplate using partial update
        SubProjectAttrTemplate partialUpdatedSubProjectAttrTemplate = new SubProjectAttrTemplate();
        partialUpdatedSubProjectAttrTemplate.setId(subProjectAttrTemplate.getId());

        partialUpdatedSubProjectAttrTemplate
            .attrCode(UPDATED_ATTR_CODE)
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE);

        restSubProjectAttrTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProjectAttrTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProjectAttrTemplate))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
        SubProjectAttrTemplate testSubProjectAttrTemplate = subProjectAttrTemplateList.get(subProjectAttrTemplateList.size() - 1);
        assertThat(testSubProjectAttrTemplate.getAttrCode()).isEqualTo(UPDATED_ATTR_CODE);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameAr()).isEqualTo(UPDATED_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttrTemplate.getAttrCodeNameEn()).isEqualTo(UPDATED_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttrTemplate.getAttrType()).isEqualTo(UPDATED_ATTR_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSubProjectAttrTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();
        subProjectAttrTemplate.setId(count.incrementAndGet());

        // Create the SubProjectAttrTemplate
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectAttrTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subProjectAttrTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubProjectAttrTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();
        subProjectAttrTemplate.setId(count.incrementAndGet());

        // Create the SubProjectAttrTemplate
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubProjectAttrTemplate() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrTemplateRepository.findAll().size();
        subProjectAttrTemplate.setId(count.incrementAndGet());

        // Create the SubProjectAttrTemplate
        SubProjectAttrTemplateDTO subProjectAttrTemplateDTO = subProjectAttrTemplateMapper.toDto(subProjectAttrTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProjectAttrTemplate in the database
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubProjectAttrTemplate() throws Exception {
        // Initialize the database
        subProjectAttrTemplateRepository.saveAndFlush(subProjectAttrTemplate);

        int databaseSizeBeforeDelete = subProjectAttrTemplateRepository.findAll().size();

        // Delete the subProjectAttrTemplate
        restSubProjectAttrTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, subProjectAttrTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubProjectAttrTemplate> subProjectAttrTemplateList = subProjectAttrTemplateRepository.findAll();
        assertThat(subProjectAttrTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
