package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.BuildingType;
import com.smarthome.app.repository.BuildingTypeRepository;
import com.smarthome.app.service.dto.BuildingTypeDTO;
import com.smarthome.app.service.mapper.BuildingTypeMapper;
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
 * Integration tests for the {@link BuildingTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuildingTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/building-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuildingTypeRepository buildingTypeRepository;

    @Autowired
    private BuildingTypeMapper buildingTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuildingTypeMockMvc;

    private BuildingType buildingType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingType createEntity(EntityManager em) {
        BuildingType buildingType = new BuildingType().code(DEFAULT_CODE).nameAr(DEFAULT_NAME_AR).nameEn(DEFAULT_NAME_EN);
        return buildingType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingType createUpdatedEntity(EntityManager em) {
        BuildingType buildingType = new BuildingType().code(UPDATED_CODE).nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN);
        return buildingType;
    }

    @BeforeEach
    public void initTest() {
        buildingType = createEntity(em);
    }

    @Test
    @Transactional
    void createBuildingType() throws Exception {
        int databaseSizeBeforeCreate = buildingTypeRepository.findAll().size();
        // Create the BuildingType
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);
        restBuildingTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BuildingType testBuildingType = buildingTypeList.get(buildingTypeList.size() - 1);
        assertThat(testBuildingType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBuildingType.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testBuildingType.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
    }

    @Test
    @Transactional
    void createBuildingTypeWithExistingId() throws Exception {
        // Create the BuildingType with an existing ID
        buildingType.setId(1L);
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);

        int databaseSizeBeforeCreate = buildingTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuildingTypes() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        // Get all the buildingTypeList
        restBuildingTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)));
    }

    @Test
    @Transactional
    void getBuildingType() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        // Get the buildingType
        restBuildingTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, buildingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buildingType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingBuildingType() throws Exception {
        // Get the buildingType
        restBuildingTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBuildingType() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();

        // Update the buildingType
        BuildingType updatedBuildingType = buildingTypeRepository.findById(buildingType.getId()).get();
        // Disconnect from session so that the updates on updatedBuildingType are not directly saved in db
        em.detach(updatedBuildingType);
        updatedBuildingType.code(UPDATED_CODE).nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN);
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(updatedBuildingType);

        restBuildingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buildingTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
        BuildingType testBuildingType = buildingTypeList.get(buildingTypeList.size() - 1);
        assertThat(testBuildingType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBuildingType.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testBuildingType.getNameEn()).isEqualTo(UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void putNonExistingBuildingType() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();
        buildingType.setId(count.incrementAndGet());

        // Create the BuildingType
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buildingTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuildingType() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();
        buildingType.setId(count.incrementAndGet());

        // Create the BuildingType
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuildingType() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();
        buildingType.setId(count.incrementAndGet());

        // Create the BuildingType
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuildingTypeWithPatch() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();

        // Update the buildingType using partial update
        BuildingType partialUpdatedBuildingType = new BuildingType();
        partialUpdatedBuildingType.setId(buildingType.getId());

        partialUpdatedBuildingType.code(UPDATED_CODE);

        restBuildingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuildingType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildingType))
            )
            .andExpect(status().isOk());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
        BuildingType testBuildingType = buildingTypeList.get(buildingTypeList.size() - 1);
        assertThat(testBuildingType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBuildingType.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testBuildingType.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
    }

    @Test
    @Transactional
    void fullUpdateBuildingTypeWithPatch() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();

        // Update the buildingType using partial update
        BuildingType partialUpdatedBuildingType = new BuildingType();
        partialUpdatedBuildingType.setId(buildingType.getId());

        partialUpdatedBuildingType.code(UPDATED_CODE).nameAr(UPDATED_NAME_AR).nameEn(UPDATED_NAME_EN);

        restBuildingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuildingType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuildingType))
            )
            .andExpect(status().isOk());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
        BuildingType testBuildingType = buildingTypeList.get(buildingTypeList.size() - 1);
        assertThat(testBuildingType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBuildingType.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testBuildingType.getNameEn()).isEqualTo(UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void patchNonExistingBuildingType() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();
        buildingType.setId(count.incrementAndGet());

        // Create the BuildingType
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buildingTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuildingType() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();
        buildingType.setId(count.incrementAndGet());

        // Create the BuildingType
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuildingType() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();
        buildingType.setId(count.incrementAndGet());

        // Create the BuildingType
        BuildingTypeDTO buildingTypeDTO = buildingTypeMapper.toDto(buildingType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuildingTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buildingTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuildingType() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        int databaseSizeBeforeDelete = buildingTypeRepository.findAll().size();

        // Delete the buildingType
        restBuildingTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, buildingType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
