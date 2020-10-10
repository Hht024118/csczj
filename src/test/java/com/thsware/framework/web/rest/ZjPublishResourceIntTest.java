package com.thsware.framework.web.rest;

import com.thsware.framework.CsczjApp;

import com.thsware.framework.domain.ZjPublish;
import com.thsware.framework.repository.ZjPublishRepository;
import com.thsware.framework.service.ZjPublishService;
import com.thsware.framework.service.dto.ZjPublishDTO;
import com.thsware.framework.service.mapper.ZjPublishMapper;
import com.thsware.framework.web.rest.errors.ExceptionTranslator;
import com.thsware.framework.service.dto.ZjPublishCriteria;
import com.thsware.framework.service.ZjPublishQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.thsware.framework.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ZjPublishResource REST controller.
 *
 * @see ZjPublishResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsczjApp.class)
public class ZjPublishResourceIntTest {

    private static final String DEFAULT_FLOW_STATE = "AAAAAAAAAA";
    private static final String UPDATED_FLOW_STATE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PUBLISH_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLISH_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ZjPublishRepository zjPublishRepository;


    @Autowired
    private ZjPublishMapper zjPublishMapper;
    

    @Autowired
    private ZjPublishService zjPublishService;

    @Autowired
    private ZjPublishQueryService zjPublishQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZjPublishMockMvc;

    private ZjPublish zjPublish;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZjPublishResource zjPublishResource = new ZjPublishResource(zjPublishService, zjPublishQueryService);
        this.restZjPublishMockMvc = MockMvcBuilders.standaloneSetup(zjPublishResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZjPublish createEntity(EntityManager em) {
        ZjPublish zjPublish = new ZjPublish()
            .flowState(DEFAULT_FLOW_STATE)
            .publishTime(DEFAULT_PUBLISH_TIME);
        return zjPublish;
    }

    @Before
    public void initTest() {
        zjPublish = createEntity(em);
    }

    @Test
    @Transactional
    public void createZjPublish() throws Exception {
        int databaseSizeBeforeCreate = zjPublishRepository.findAll().size();

        // Create the ZjPublish
        ZjPublishDTO zjPublishDTO = zjPublishMapper.toDto(zjPublish);
        restZjPublishMockMvc.perform(post("/api/zj-publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjPublishDTO)))
            .andExpect(status().isCreated());

        // Validate the ZjPublish in the database
        List<ZjPublish> zjPublishList = zjPublishRepository.findAll();
        assertThat(zjPublishList).hasSize(databaseSizeBeforeCreate + 1);
        ZjPublish testZjPublish = zjPublishList.get(zjPublishList.size() - 1);
        assertThat(testZjPublish.getFlowState()).isEqualTo(DEFAULT_FLOW_STATE);
        assertThat(testZjPublish.getPublishTime()).isEqualTo(DEFAULT_PUBLISH_TIME);
    }

    @Test
    @Transactional
    public void createZjPublishWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zjPublishRepository.findAll().size();

        // Create the ZjPublish with an existing ID
        zjPublish.setId("");
        ZjPublishDTO zjPublishDTO = zjPublishMapper.toDto(zjPublish);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZjPublishMockMvc.perform(post("/api/zj-publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjPublishDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZjPublish in the database
        List<ZjPublish> zjPublishList = zjPublishRepository.findAll();
        assertThat(zjPublishList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllZjPublishes() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get all the zjPublishList
        restZjPublishMockMvc.perform(get("/api/zj-publishes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zjPublish.getId())))
            .andExpect(jsonPath("$.[*].flowState").value(hasItem(DEFAULT_FLOW_STATE.toString())))
            .andExpect(jsonPath("$.[*].publishTime").value(hasItem(DEFAULT_PUBLISH_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getZjPublish() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get the zjPublish
        restZjPublishMockMvc.perform(get("/api/zj-publishes/{id}", zjPublish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zjPublish.getId()))
            .andExpect(jsonPath("$.flowState").value(DEFAULT_FLOW_STATE.toString()))
            .andExpect(jsonPath("$.publishTime").value(DEFAULT_PUBLISH_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllZjPublishesByFlowStateIsEqualToSomething() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get all the zjPublishList where flowState equals to DEFAULT_FLOW_STATE
        defaultZjPublishShouldBeFound("flowState.equals=" + DEFAULT_FLOW_STATE);

        // Get all the zjPublishList where flowState equals to UPDATED_FLOW_STATE
        defaultZjPublishShouldNotBeFound("flowState.equals=" + UPDATED_FLOW_STATE);
    }

    @Test
    @Transactional
    public void getAllZjPublishesByFlowStateIsInShouldWork() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get all the zjPublishList where flowState in DEFAULT_FLOW_STATE or UPDATED_FLOW_STATE
        defaultZjPublishShouldBeFound("flowState.in=" + DEFAULT_FLOW_STATE + "," + UPDATED_FLOW_STATE);

        // Get all the zjPublishList where flowState equals to UPDATED_FLOW_STATE
        defaultZjPublishShouldNotBeFound("flowState.in=" + UPDATED_FLOW_STATE);
    }

    @Test
    @Transactional
    public void getAllZjPublishesByFlowStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get all the zjPublishList where flowState is not null
        defaultZjPublishShouldBeFound("flowState.specified=true");

        // Get all the zjPublishList where flowState is null
        defaultZjPublishShouldNotBeFound("flowState.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjPublishesByPublishTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get all the zjPublishList where publishTime equals to DEFAULT_PUBLISH_TIME
        defaultZjPublishShouldBeFound("publishTime.equals=" + DEFAULT_PUBLISH_TIME);

        // Get all the zjPublishList where publishTime equals to UPDATED_PUBLISH_TIME
        defaultZjPublishShouldNotBeFound("publishTime.equals=" + UPDATED_PUBLISH_TIME);
    }

    @Test
    @Transactional
    public void getAllZjPublishesByPublishTimeIsInShouldWork() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get all the zjPublishList where publishTime in DEFAULT_PUBLISH_TIME or UPDATED_PUBLISH_TIME
        defaultZjPublishShouldBeFound("publishTime.in=" + DEFAULT_PUBLISH_TIME + "," + UPDATED_PUBLISH_TIME);

        // Get all the zjPublishList where publishTime equals to UPDATED_PUBLISH_TIME
        defaultZjPublishShouldNotBeFound("publishTime.in=" + UPDATED_PUBLISH_TIME);
    }

    @Test
    @Transactional
    public void getAllZjPublishesByPublishTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        // Get all the zjPublishList where publishTime is not null
        defaultZjPublishShouldBeFound("publishTime.specified=true");

        // Get all the zjPublishList where publishTime is null
        defaultZjPublishShouldNotBeFound("publishTime.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultZjPublishShouldBeFound(String filter) throws Exception {
        restZjPublishMockMvc.perform(get("/api/zj-publishes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zjPublish.getId())))
            .andExpect(jsonPath("$.[*].flowState").value(hasItem(DEFAULT_FLOW_STATE.toString())))
            .andExpect(jsonPath("$.[*].publishTime").value(hasItem(DEFAULT_PUBLISH_TIME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultZjPublishShouldNotBeFound(String filter) throws Exception {
        restZjPublishMockMvc.perform(get("/api/zj-publishes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingZjPublish() throws Exception {
        // Get the zjPublish
        restZjPublishMockMvc.perform(get("/api/zj-publishes/{id}", "-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZjPublish() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        int databaseSizeBeforeUpdate = zjPublishRepository.findAll().size();

        // Update the zjPublish
        ZjPublish updatedZjPublish = zjPublishRepository.findById(zjPublish.getId()).get();
        // Disconnect from session so that the updates on updatedZjPublish are not directly saved in db
        em.detach(updatedZjPublish);
        updatedZjPublish
            .flowState(UPDATED_FLOW_STATE)
            .publishTime(UPDATED_PUBLISH_TIME);
        ZjPublishDTO zjPublishDTO = zjPublishMapper.toDto(updatedZjPublish);

        restZjPublishMockMvc.perform(put("/api/zj-publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjPublishDTO)))
            .andExpect(status().isOk());

        // Validate the ZjPublish in the database
        List<ZjPublish> zjPublishList = zjPublishRepository.findAll();
        assertThat(zjPublishList).hasSize(databaseSizeBeforeUpdate);
        ZjPublish testZjPublish = zjPublishList.get(zjPublishList.size() - 1);
        assertThat(testZjPublish.getFlowState()).isEqualTo(UPDATED_FLOW_STATE);
        assertThat(testZjPublish.getPublishTime()).isEqualTo(UPDATED_PUBLISH_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingZjPublish() throws Exception {
        int databaseSizeBeforeUpdate = zjPublishRepository.findAll().size();

        // Create the ZjPublish
        ZjPublishDTO zjPublishDTO = zjPublishMapper.toDto(zjPublish);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZjPublishMockMvc.perform(put("/api/zj-publishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjPublishDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZjPublish in the database
        List<ZjPublish> zjPublishList = zjPublishRepository.findAll();
        assertThat(zjPublishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZjPublish() throws Exception {
        // Initialize the database
        zjPublishRepository.saveAndFlush(zjPublish);

        int databaseSizeBeforeDelete = zjPublishRepository.findAll().size();

        // Get the zjPublish
        restZjPublishMockMvc.perform(delete("/api/zj-publishes/{id}", zjPublish.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZjPublish> zjPublishList = zjPublishRepository.findAll();
        assertThat(zjPublishList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZjPublish.class);
        ZjPublish zjPublish1 = new ZjPublish();
        zjPublish1.setId("");
        ZjPublish zjPublish2 = new ZjPublish();
        zjPublish2.setId(zjPublish1.getId());
        assertThat(zjPublish1).isEqualTo(zjPublish2);
        zjPublish2.setId("");
        assertThat(zjPublish1).isNotEqualTo(zjPublish2);
        zjPublish1.setId(null);
        assertThat(zjPublish1).isNotEqualTo(zjPublish2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZjPublishDTO.class);
        ZjPublishDTO zjPublishDTO1 = new ZjPublishDTO();
        zjPublishDTO1.setId("");
        ZjPublishDTO zjPublishDTO2 = new ZjPublishDTO();
        assertThat(zjPublishDTO1).isNotEqualTo(zjPublishDTO2);
        zjPublishDTO2.setId(zjPublishDTO1.getId());
        assertThat(zjPublishDTO1).isEqualTo(zjPublishDTO2);
        zjPublishDTO2.setId("");
        assertThat(zjPublishDTO1).isNotEqualTo(zjPublishDTO2);
        zjPublishDTO1.setId(null);
        assertThat(zjPublishDTO1).isNotEqualTo(zjPublishDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zjPublishMapper.fromId("").getId()).isEqualTo(42);
        assertThat(zjPublishMapper.fromId(null)).isNull();
    }
}
