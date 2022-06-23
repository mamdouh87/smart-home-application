package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.RequirementItem;
import com.smarthome.app.repository.RequirementItemRepository;
import com.smarthome.app.service.dto.RequirementItemDTO;
import com.smarthome.app.service.mapper.RequirementItemMapper;
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
 * Integration tests for the {@link RequirementItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequirementItemResourceIT {

    private static final String DEFAULT_SYS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SYS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_AR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_AR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/requirement-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequirementItemRepository requirementItemRepository;

    @Autowired
    private RequirementItemMapper requirementItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequirementItemMockMvc;

    private RequirementItem requirementItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequirementItem createEntity(EntityManager em) {
        RequirementItem requirementItem = new RequirementItem()
            .sysCode(DEFAULT_SYS_CODE)
            .code(DEFAULT_CODE)
            .descriptionAr(DEFAULT_DESCRIPTION_AR)
            .descriptionEn(DEFAULT_DESCRIPTION_EN);
        return requirementItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequirementItem createUpdatedEntity(EntityManager em) {
        RequirementItem requirementItem = new RequirementItem()
            .sysCode(UPDATED_SYS_CODE)
            .code(UPDATED_CODE)
            .descriptionAr(UPDATED_DESCRIPTION_AR)
            .descriptionEn(UPDATED_DESCRIPTION_EN);
        return requirementItem;
    }

    @BeforeEach
    public void initTest() {
        requirementItem = createEntity(em);
    }

    @Test
    @Transactional
    void createRequirementItem() throws Exception {
        int databaseSizeBeforeCreate = requirementItemRepository.findAll().size();
        // Create the RequirementItem
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);
        restRequirementItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeCreate + 1);
        RequirementItem testRequirementItem = requirementItemList.get(requirementItemList.size() - 1);
        assertThat(testRequirementItem.getSysCode()).isEqualTo(DEFAULT_SYS_CODE);
        assertThat(testRequirementItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRequirementItem.getDescriptionAr()).isEqualTo(DEFAULT_DESCRIPTION_AR);
        assertThat(testRequirementItem.getDescriptionEn()).isEqualTo(DEFAULT_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void createRequirementItemWithExistingId() throws Exception {
        // Create the RequirementItem with an existing ID
        requirementItem.setId(1L);
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);

        int databaseSizeBeforeCreate = requirementItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequirementItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequirementItems() throws Exception {
        // Initialize the database
        requirementItemRepository.saveAndFlush(requirementItem);

        // Get all the requirementItemList
        restRequirementItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requirementItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sysCode").value(hasItem(DEFAULT_SYS_CODE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].descriptionAr").value(hasItem(DEFAULT_DESCRIPTION_AR)))
            .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN)));
    }

    @Test
    @Transactional
    void getRequirementItem() throws Exception {
        // Initialize the database
        requirementItemRepository.saveAndFlush(requirementItem);

        // Get the requirementItem
        restRequirementItemMockMvc
            .perform(get(ENTITY_API_URL_ID, requirementItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requirementItem.getId().intValue()))
            .andExpect(jsonPath("$.sysCode").value(DEFAULT_SYS_CODE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.descriptionAr").value(DEFAULT_DESCRIPTION_AR))
            .andExpect(jsonPath("$.descriptionEn").value(DEFAULT_DESCRIPTION_EN));
    }

    @Test
    @Transactional
    void getNonExistingRequirementItem() throws Exception {
        // Get the requirementItem
        restRequirementItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequirementItem() throws Exception {
        // Initialize the database
        requirementItemRepository.saveAndFlush(requirementItem);

        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();

        // Update the requirementItem
        RequirementItem updatedRequirementItem = requirementItemRepository.findById(requirementItem.getId()).get();
        // Disconnect from session so that the updates on updatedRequirementItem are not directly saved in db
        em.detach(updatedRequirementItem);
        updatedRequirementItem
            .sysCode(UPDATED_SYS_CODE)
            .code(UPDATED_CODE)
            .descriptionAr(UPDATED_DESCRIPTION_AR)
            .descriptionEn(UPDATED_DESCRIPTION_EN);
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(updatedRequirementItem);

        restRequirementItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requirementItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
        RequirementItem testRequirementItem = requirementItemList.get(requirementItemList.size() - 1);
        assertThat(testRequirementItem.getSysCode()).isEqualTo(UPDATED_SYS_CODE);
        assertThat(testRequirementItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRequirementItem.getDescriptionAr()).isEqualTo(UPDATED_DESCRIPTION_AR);
        assertThat(testRequirementItem.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void putNonExistingRequirementItem() throws Exception {
        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();
        requirementItem.setId(count.incrementAndGet());

        // Create the RequirementItem
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequirementItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requirementItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequirementItem() throws Exception {
        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();
        requirementItem.setId(count.incrementAndGet());

        // Create the RequirementItem
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequirementItem() throws Exception {
        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();
        requirementItem.setId(count.incrementAndGet());

        // Create the RequirementItem
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequirementItemWithPatch() throws Exception {
        // Initialize the database
        requirementItemRepository.saveAndFlush(requirementItem);

        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();

        // Update the requirementItem using partial update
        RequirementItem partialUpdatedRequirementItem = new RequirementItem();
        partialUpdatedRequirementItem.setId(requirementItem.getId());

        partialUpdatedRequirementItem.code(UPDATED_CODE).descriptionEn(UPDATED_DESCRIPTION_EN);

        restRequirementItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequirementItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequirementItem))
            )
            .andExpect(status().isOk());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
        RequirementItem testRequirementItem = requirementItemList.get(requirementItemList.size() - 1);
        assertThat(testRequirementItem.getSysCode()).isEqualTo(DEFAULT_SYS_CODE);
        assertThat(testRequirementItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRequirementItem.getDescriptionAr()).isEqualTo(DEFAULT_DESCRIPTION_AR);
        assertThat(testRequirementItem.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void fullUpdateRequirementItemWithPatch() throws Exception {
        // Initialize the database
        requirementItemRepository.saveAndFlush(requirementItem);

        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();

        // Update the requirementItem using partial update
        RequirementItem partialUpdatedRequirementItem = new RequirementItem();
        partialUpdatedRequirementItem.setId(requirementItem.getId());

        partialUpdatedRequirementItem
            .sysCode(UPDATED_SYS_CODE)
            .code(UPDATED_CODE)
            .descriptionAr(UPDATED_DESCRIPTION_AR)
            .descriptionEn(UPDATED_DESCRIPTION_EN);

        restRequirementItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequirementItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequirementItem))
            )
            .andExpect(status().isOk());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
        RequirementItem testRequirementItem = requirementItemList.get(requirementItemList.size() - 1);
        assertThat(testRequirementItem.getSysCode()).isEqualTo(UPDATED_SYS_CODE);
        assertThat(testRequirementItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRequirementItem.getDescriptionAr()).isEqualTo(UPDATED_DESCRIPTION_AR);
        assertThat(testRequirementItem.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
    }

    @Test
    @Transactional
    void patchNonExistingRequirementItem() throws Exception {
        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();
        requirementItem.setId(count.incrementAndGet());

        // Create the RequirementItem
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequirementItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requirementItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequirementItem() throws Exception {
        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();
        requirementItem.setId(count.incrementAndGet());

        // Create the RequirementItem
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequirementItem() throws Exception {
        int databaseSizeBeforeUpdate = requirementItemRepository.findAll().size();
        requirementItem.setId(count.incrementAndGet());

        // Create the RequirementItem
        RequirementItemDTO requirementItemDTO = requirementItemMapper.toDto(requirementItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequirementItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requirementItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequirementItem in the database
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequirementItem() throws Exception {
        // Initialize the database
        requirementItemRepository.saveAndFlush(requirementItem);

        int databaseSizeBeforeDelete = requirementItemRepository.findAll().size();

        // Delete the requirementItem
        restRequirementItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, requirementItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequirementItem> requirementItemList = requirementItemRepository.findAll();
        assertThat(requirementItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
