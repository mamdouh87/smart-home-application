package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.SubProjectAttr;
import com.smarthome.app.repository.SubProjectAttrRepository;
import com.smarthome.app.service.dto.SubProjectAttrDTO;
import com.smarthome.app.service.mapper.SubProjectAttrMapper;
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
 * Integration tests for the {@link SubProjectAttrResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubProjectAttrResourceIT {

    private static final String DEFAULT_ATTR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_CODE_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CODE_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_CODE_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CODE_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ATTR_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-project-attrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubProjectAttrRepository subProjectAttrRepository;

    @Autowired
    private SubProjectAttrMapper subProjectAttrMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubProjectAttrMockMvc;

    private SubProjectAttr subProjectAttr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProjectAttr createEntity(EntityManager em) {
        SubProjectAttr subProjectAttr = new SubProjectAttr()
            .attrCode(DEFAULT_ATTR_CODE)
            .attrCodeNameAr(DEFAULT_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(DEFAULT_ATTR_CODE_NAME_EN)
            .attrType(DEFAULT_ATTR_TYPE)
            .attrValue(DEFAULT_ATTR_VALUE);
        return subProjectAttr;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubProjectAttr createUpdatedEntity(EntityManager em) {
        SubProjectAttr subProjectAttr = new SubProjectAttr()
            .attrCode(UPDATED_ATTR_CODE)
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE)
            .attrValue(UPDATED_ATTR_VALUE);
        return subProjectAttr;
    }

    @BeforeEach
    public void initTest() {
        subProjectAttr = createEntity(em);
    }

    @Test
    @Transactional
    void createSubProjectAttr() throws Exception {
        int databaseSizeBeforeCreate = subProjectAttrRepository.findAll().size();
        // Create the SubProjectAttr
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);
        restSubProjectAttrMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeCreate + 1);
        SubProjectAttr testSubProjectAttr = subProjectAttrList.get(subProjectAttrList.size() - 1);
        assertThat(testSubProjectAttr.getAttrCode()).isEqualTo(DEFAULT_ATTR_CODE);
        assertThat(testSubProjectAttr.getAttrCodeNameAr()).isEqualTo(DEFAULT_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttr.getAttrCodeNameEn()).isEqualTo(DEFAULT_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttr.getAttrType()).isEqualTo(DEFAULT_ATTR_TYPE);
        assertThat(testSubProjectAttr.getAttrValue()).isEqualTo(DEFAULT_ATTR_VALUE);
    }

    @Test
    @Transactional
    void createSubProjectAttrWithExistingId() throws Exception {
        // Create the SubProjectAttr with an existing ID
        subProjectAttr.setId(1L);
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);

        int databaseSizeBeforeCreate = subProjectAttrRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubProjectAttrMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubProjectAttrs() throws Exception {
        // Initialize the database
        subProjectAttrRepository.saveAndFlush(subProjectAttr);

        // Get all the subProjectAttrList
        restSubProjectAttrMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subProjectAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].attrCode").value(hasItem(DEFAULT_ATTR_CODE)))
            .andExpect(jsonPath("$.[*].attrCodeNameAr").value(hasItem(DEFAULT_ATTR_CODE_NAME_AR)))
            .andExpect(jsonPath("$.[*].attrCodeNameEn").value(hasItem(DEFAULT_ATTR_CODE_NAME_EN)))
            .andExpect(jsonPath("$.[*].attrType").value(hasItem(DEFAULT_ATTR_TYPE)))
            .andExpect(jsonPath("$.[*].attrValue").value(hasItem(DEFAULT_ATTR_VALUE)));
    }

    @Test
    @Transactional
    void getSubProjectAttr() throws Exception {
        // Initialize the database
        subProjectAttrRepository.saveAndFlush(subProjectAttr);

        // Get the subProjectAttr
        restSubProjectAttrMockMvc
            .perform(get(ENTITY_API_URL_ID, subProjectAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subProjectAttr.getId().intValue()))
            .andExpect(jsonPath("$.attrCode").value(DEFAULT_ATTR_CODE))
            .andExpect(jsonPath("$.attrCodeNameAr").value(DEFAULT_ATTR_CODE_NAME_AR))
            .andExpect(jsonPath("$.attrCodeNameEn").value(DEFAULT_ATTR_CODE_NAME_EN))
            .andExpect(jsonPath("$.attrType").value(DEFAULT_ATTR_TYPE))
            .andExpect(jsonPath("$.attrValue").value(DEFAULT_ATTR_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingSubProjectAttr() throws Exception {
        // Get the subProjectAttr
        restSubProjectAttrMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubProjectAttr() throws Exception {
        // Initialize the database
        subProjectAttrRepository.saveAndFlush(subProjectAttr);

        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();

        // Update the subProjectAttr
        SubProjectAttr updatedSubProjectAttr = subProjectAttrRepository.findById(subProjectAttr.getId()).get();
        // Disconnect from session so that the updates on updatedSubProjectAttr are not directly saved in db
        em.detach(updatedSubProjectAttr);
        updatedSubProjectAttr
            .attrCode(UPDATED_ATTR_CODE)
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE)
            .attrValue(UPDATED_ATTR_VALUE);
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(updatedSubProjectAttr);

        restSubProjectAttrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectAttrDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
        SubProjectAttr testSubProjectAttr = subProjectAttrList.get(subProjectAttrList.size() - 1);
        assertThat(testSubProjectAttr.getAttrCode()).isEqualTo(UPDATED_ATTR_CODE);
        assertThat(testSubProjectAttr.getAttrCodeNameAr()).isEqualTo(UPDATED_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttr.getAttrCodeNameEn()).isEqualTo(UPDATED_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttr.getAttrType()).isEqualTo(UPDATED_ATTR_TYPE);
        assertThat(testSubProjectAttr.getAttrValue()).isEqualTo(UPDATED_ATTR_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingSubProjectAttr() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();
        subProjectAttr.setId(count.incrementAndGet());

        // Create the SubProjectAttr
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectAttrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subProjectAttrDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubProjectAttr() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();
        subProjectAttr.setId(count.incrementAndGet());

        // Create the SubProjectAttr
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubProjectAttr() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();
        subProjectAttr.setId(count.incrementAndGet());

        // Create the SubProjectAttr
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubProjectAttrWithPatch() throws Exception {
        // Initialize the database
        subProjectAttrRepository.saveAndFlush(subProjectAttr);

        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();

        // Update the subProjectAttr using partial update
        SubProjectAttr partialUpdatedSubProjectAttr = new SubProjectAttr();
        partialUpdatedSubProjectAttr.setId(subProjectAttr.getId());

        partialUpdatedSubProjectAttr
            .attrCode(UPDATED_ATTR_CODE)
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE)
            .attrValue(UPDATED_ATTR_VALUE);

        restSubProjectAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProjectAttr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProjectAttr))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
        SubProjectAttr testSubProjectAttr = subProjectAttrList.get(subProjectAttrList.size() - 1);
        assertThat(testSubProjectAttr.getAttrCode()).isEqualTo(UPDATED_ATTR_CODE);
        assertThat(testSubProjectAttr.getAttrCodeNameAr()).isEqualTo(UPDATED_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttr.getAttrCodeNameEn()).isEqualTo(UPDATED_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttr.getAttrType()).isEqualTo(UPDATED_ATTR_TYPE);
        assertThat(testSubProjectAttr.getAttrValue()).isEqualTo(UPDATED_ATTR_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateSubProjectAttrWithPatch() throws Exception {
        // Initialize the database
        subProjectAttrRepository.saveAndFlush(subProjectAttr);

        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();

        // Update the subProjectAttr using partial update
        SubProjectAttr partialUpdatedSubProjectAttr = new SubProjectAttr();
        partialUpdatedSubProjectAttr.setId(subProjectAttr.getId());

        partialUpdatedSubProjectAttr
            .attrCode(UPDATED_ATTR_CODE)
            .attrCodeNameAr(UPDATED_ATTR_CODE_NAME_AR)
            .attrCodeNameEn(UPDATED_ATTR_CODE_NAME_EN)
            .attrType(UPDATED_ATTR_TYPE)
            .attrValue(UPDATED_ATTR_VALUE);

        restSubProjectAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubProjectAttr.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubProjectAttr))
            )
            .andExpect(status().isOk());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
        SubProjectAttr testSubProjectAttr = subProjectAttrList.get(subProjectAttrList.size() - 1);
        assertThat(testSubProjectAttr.getAttrCode()).isEqualTo(UPDATED_ATTR_CODE);
        assertThat(testSubProjectAttr.getAttrCodeNameAr()).isEqualTo(UPDATED_ATTR_CODE_NAME_AR);
        assertThat(testSubProjectAttr.getAttrCodeNameEn()).isEqualTo(UPDATED_ATTR_CODE_NAME_EN);
        assertThat(testSubProjectAttr.getAttrType()).isEqualTo(UPDATED_ATTR_TYPE);
        assertThat(testSubProjectAttr.getAttrValue()).isEqualTo(UPDATED_ATTR_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingSubProjectAttr() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();
        subProjectAttr.setId(count.incrementAndGet());

        // Create the SubProjectAttr
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubProjectAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subProjectAttrDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubProjectAttr() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();
        subProjectAttr.setId(count.incrementAndGet());

        // Create the SubProjectAttr
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubProjectAttr() throws Exception {
        int databaseSizeBeforeUpdate = subProjectAttrRepository.findAll().size();
        subProjectAttr.setId(count.incrementAndGet());

        // Create the SubProjectAttr
        SubProjectAttrDTO subProjectAttrDTO = subProjectAttrMapper.toDto(subProjectAttr);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubProjectAttrMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subProjectAttrDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubProjectAttr in the database
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubProjectAttr() throws Exception {
        // Initialize the database
        subProjectAttrRepository.saveAndFlush(subProjectAttr);

        int databaseSizeBeforeDelete = subProjectAttrRepository.findAll().size();

        // Delete the subProjectAttr
        restSubProjectAttrMockMvc
            .perform(delete(ENTITY_API_URL_ID, subProjectAttr.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubProjectAttr> subProjectAttrList = subProjectAttrRepository.findAll();
        assertThat(subProjectAttrList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
