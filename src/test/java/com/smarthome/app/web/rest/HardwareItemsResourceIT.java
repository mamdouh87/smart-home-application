package com.smarthome.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smarthome.app.IntegrationTest;
import com.smarthome.app.domain.HardwareItems;
import com.smarthome.app.repository.HardwareItemsRepository;
import com.smarthome.app.service.dto.HardwareItemsDTO;
import com.smarthome.app.service.mapper.HardwareItemsMapper;
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
 * Integration tests for the {@link HardwareItemsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HardwareItemsResourceIT {

    private static final String DEFAULT_HARDWARE_DESC_AR = "AAAAAAAAAA";
    private static final String UPDATED_HARDWARE_DESC_AR = "BBBBBBBBBB";

    private static final String DEFAULT_HARDWARE_DESC_EN = "AAAAAAAAAA";
    private static final String UPDATED_HARDWARE_DESC_EN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUPPORTED_QTY = 1;
    private static final Integer UPDATED_SUPPORTED_QTY = 2;

    private static final String ENTITY_API_URL = "/api/hardware-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HardwareItemsRepository hardwareItemsRepository;

    @Autowired
    private HardwareItemsMapper hardwareItemsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHardwareItemsMockMvc;

    private HardwareItems hardwareItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HardwareItems createEntity(EntityManager em) {
        HardwareItems hardwareItems = new HardwareItems()
            .hardwareDescAr(DEFAULT_HARDWARE_DESC_AR)
            .hardwareDescEn(DEFAULT_HARDWARE_DESC_EN)
            .supportedQty(DEFAULT_SUPPORTED_QTY);
        return hardwareItems;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HardwareItems createUpdatedEntity(EntityManager em) {
        HardwareItems hardwareItems = new HardwareItems()
            .hardwareDescAr(UPDATED_HARDWARE_DESC_AR)
            .hardwareDescEn(UPDATED_HARDWARE_DESC_EN)
            .supportedQty(UPDATED_SUPPORTED_QTY);
        return hardwareItems;
    }

    @BeforeEach
    public void initTest() {
        hardwareItems = createEntity(em);
    }

    @Test
    @Transactional
    void createHardwareItems() throws Exception {
        int databaseSizeBeforeCreate = hardwareItemsRepository.findAll().size();
        // Create the HardwareItems
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);
        restHardwareItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeCreate + 1);
        HardwareItems testHardwareItems = hardwareItemsList.get(hardwareItemsList.size() - 1);
        assertThat(testHardwareItems.getHardwareDescAr()).isEqualTo(DEFAULT_HARDWARE_DESC_AR);
        assertThat(testHardwareItems.getHardwareDescEn()).isEqualTo(DEFAULT_HARDWARE_DESC_EN);
        assertThat(testHardwareItems.getSupportedQty()).isEqualTo(DEFAULT_SUPPORTED_QTY);
    }

    @Test
    @Transactional
    void createHardwareItemsWithExistingId() throws Exception {
        // Create the HardwareItems with an existing ID
        hardwareItems.setId(1L);
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);

        int databaseSizeBeforeCreate = hardwareItemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHardwareItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHardwareItems() throws Exception {
        // Initialize the database
        hardwareItemsRepository.saveAndFlush(hardwareItems);

        // Get all the hardwareItemsList
        restHardwareItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hardwareItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].hardwareDescAr").value(hasItem(DEFAULT_HARDWARE_DESC_AR)))
            .andExpect(jsonPath("$.[*].hardwareDescEn").value(hasItem(DEFAULT_HARDWARE_DESC_EN)))
            .andExpect(jsonPath("$.[*].supportedQty").value(hasItem(DEFAULT_SUPPORTED_QTY)));
    }

    @Test
    @Transactional
    void getHardwareItems() throws Exception {
        // Initialize the database
        hardwareItemsRepository.saveAndFlush(hardwareItems);

        // Get the hardwareItems
        restHardwareItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, hardwareItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hardwareItems.getId().intValue()))
            .andExpect(jsonPath("$.hardwareDescAr").value(DEFAULT_HARDWARE_DESC_AR))
            .andExpect(jsonPath("$.hardwareDescEn").value(DEFAULT_HARDWARE_DESC_EN))
            .andExpect(jsonPath("$.supportedQty").value(DEFAULT_SUPPORTED_QTY));
    }

    @Test
    @Transactional
    void getNonExistingHardwareItems() throws Exception {
        // Get the hardwareItems
        restHardwareItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHardwareItems() throws Exception {
        // Initialize the database
        hardwareItemsRepository.saveAndFlush(hardwareItems);

        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();

        // Update the hardwareItems
        HardwareItems updatedHardwareItems = hardwareItemsRepository.findById(hardwareItems.getId()).get();
        // Disconnect from session so that the updates on updatedHardwareItems are not directly saved in db
        em.detach(updatedHardwareItems);
        updatedHardwareItems
            .hardwareDescAr(UPDATED_HARDWARE_DESC_AR)
            .hardwareDescEn(UPDATED_HARDWARE_DESC_EN)
            .supportedQty(UPDATED_SUPPORTED_QTY);
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(updatedHardwareItems);

        restHardwareItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hardwareItemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isOk());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
        HardwareItems testHardwareItems = hardwareItemsList.get(hardwareItemsList.size() - 1);
        assertThat(testHardwareItems.getHardwareDescAr()).isEqualTo(UPDATED_HARDWARE_DESC_AR);
        assertThat(testHardwareItems.getHardwareDescEn()).isEqualTo(UPDATED_HARDWARE_DESC_EN);
        assertThat(testHardwareItems.getSupportedQty()).isEqualTo(UPDATED_SUPPORTED_QTY);
    }

    @Test
    @Transactional
    void putNonExistingHardwareItems() throws Exception {
        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();
        hardwareItems.setId(count.incrementAndGet());

        // Create the HardwareItems
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHardwareItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hardwareItemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHardwareItems() throws Exception {
        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();
        hardwareItems.setId(count.incrementAndGet());

        // Create the HardwareItems
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardwareItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHardwareItems() throws Exception {
        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();
        hardwareItems.setId(count.incrementAndGet());

        // Create the HardwareItems
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardwareItemsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHardwareItemsWithPatch() throws Exception {
        // Initialize the database
        hardwareItemsRepository.saveAndFlush(hardwareItems);

        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();

        // Update the hardwareItems using partial update
        HardwareItems partialUpdatedHardwareItems = new HardwareItems();
        partialUpdatedHardwareItems.setId(hardwareItems.getId());

        partialUpdatedHardwareItems.hardwareDescEn(UPDATED_HARDWARE_DESC_EN);

        restHardwareItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHardwareItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHardwareItems))
            )
            .andExpect(status().isOk());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
        HardwareItems testHardwareItems = hardwareItemsList.get(hardwareItemsList.size() - 1);
        assertThat(testHardwareItems.getHardwareDescAr()).isEqualTo(DEFAULT_HARDWARE_DESC_AR);
        assertThat(testHardwareItems.getHardwareDescEn()).isEqualTo(UPDATED_HARDWARE_DESC_EN);
        assertThat(testHardwareItems.getSupportedQty()).isEqualTo(DEFAULT_SUPPORTED_QTY);
    }

    @Test
    @Transactional
    void fullUpdateHardwareItemsWithPatch() throws Exception {
        // Initialize the database
        hardwareItemsRepository.saveAndFlush(hardwareItems);

        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();

        // Update the hardwareItems using partial update
        HardwareItems partialUpdatedHardwareItems = new HardwareItems();
        partialUpdatedHardwareItems.setId(hardwareItems.getId());

        partialUpdatedHardwareItems
            .hardwareDescAr(UPDATED_HARDWARE_DESC_AR)
            .hardwareDescEn(UPDATED_HARDWARE_DESC_EN)
            .supportedQty(UPDATED_SUPPORTED_QTY);

        restHardwareItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHardwareItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHardwareItems))
            )
            .andExpect(status().isOk());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
        HardwareItems testHardwareItems = hardwareItemsList.get(hardwareItemsList.size() - 1);
        assertThat(testHardwareItems.getHardwareDescAr()).isEqualTo(UPDATED_HARDWARE_DESC_AR);
        assertThat(testHardwareItems.getHardwareDescEn()).isEqualTo(UPDATED_HARDWARE_DESC_EN);
        assertThat(testHardwareItems.getSupportedQty()).isEqualTo(UPDATED_SUPPORTED_QTY);
    }

    @Test
    @Transactional
    void patchNonExistingHardwareItems() throws Exception {
        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();
        hardwareItems.setId(count.incrementAndGet());

        // Create the HardwareItems
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHardwareItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hardwareItemsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHardwareItems() throws Exception {
        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();
        hardwareItems.setId(count.incrementAndGet());

        // Create the HardwareItems
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardwareItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHardwareItems() throws Exception {
        int databaseSizeBeforeUpdate = hardwareItemsRepository.findAll().size();
        hardwareItems.setId(count.incrementAndGet());

        // Create the HardwareItems
        HardwareItemsDTO hardwareItemsDTO = hardwareItemsMapper.toDto(hardwareItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardwareItemsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hardwareItemsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HardwareItems in the database
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHardwareItems() throws Exception {
        // Initialize the database
        hardwareItemsRepository.saveAndFlush(hardwareItems);

        int databaseSizeBeforeDelete = hardwareItemsRepository.findAll().size();

        // Delete the hardwareItems
        restHardwareItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, hardwareItems.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HardwareItems> hardwareItemsList = hardwareItemsRepository.findAll();
        assertThat(hardwareItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
