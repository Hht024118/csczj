package com.thsware.framework.web.rest;

import com.thsware.framework.CsczjApp;

import com.thsware.framework.domain.ZjActors;
import com.thsware.framework.repository.ZjActorsRepository;
import com.thsware.framework.service.ZjActorsService;
import com.thsware.framework.service.dto.ZjActorsDTO;
import com.thsware.framework.service.mapper.ZjActorsMapper;
import com.thsware.framework.web.rest.errors.ExceptionTranslator;
import com.thsware.framework.service.dto.ZjActorsCriteria;
import com.thsware.framework.service.ZjActorsQueryService;

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
import java.util.List;


import static com.thsware.framework.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ZjActorsResource REST controller.
 *
 * @see ZjActorsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsczjApp.class)
public class ZjActorsResourceIntTest {

    private static final String DEFAULT_PROJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STEP = "AAAAAAAAAA";
    private static final String UPDATED_STEP = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_NAME = "BBBBBBBBBB";

    @Autowired
    private ZjActorsRepository zjActorsRepository;


    @Autowired
    private ZjActorsMapper zjActorsMapper;
    

    @Autowired
    private ZjActorsService zjActorsService;

    @Autowired
    private ZjActorsQueryService zjActorsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZjActorsMockMvc;

    private ZjActors zjActors;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZjActorsResource zjActorsResource = new ZjActorsResource(zjActorsService, zjActorsQueryService);
        this.restZjActorsMockMvc = MockMvcBuilders.standaloneSetup(zjActorsResource)
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
    public static ZjActors createEntity(EntityManager em) {
        ZjActors zjActors = new ZjActors()
            .projectId(DEFAULT_PROJECT_ID)
            .step(DEFAULT_STEP)
            .personId(DEFAULT_PERSON_ID)
            .personName(DEFAULT_PERSON_NAME);
        return zjActors;
    }

    @Before
    public void initTest() {
        zjActors = createEntity(em);
    }

    @Test
    @Transactional
    public void createZjActors() throws Exception {
        int databaseSizeBeforeCreate = zjActorsRepository.findAll().size();

        // Create the ZjActors
        ZjActorsDTO zjActorsDTO = zjActorsMapper.toDto(zjActors);
        restZjActorsMockMvc.perform(post("/api/zj-actors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjActorsDTO)))
            .andExpect(status().isCreated());

        // Validate the ZjActors in the database
        List<ZjActors> zjActorsList = zjActorsRepository.findAll();
        assertThat(zjActorsList).hasSize(databaseSizeBeforeCreate + 1);
        ZjActors testZjActors = zjActorsList.get(zjActorsList.size() - 1);
        assertThat(testZjActors.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testZjActors.getStep()).isEqualTo(DEFAULT_STEP);
        assertThat(testZjActors.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testZjActors.getPersonName()).isEqualTo(DEFAULT_PERSON_NAME);
    }

    @Test
    @Transactional
    public void createZjActorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zjActorsRepository.findAll().size();

        // Create the ZjActors with an existing ID
        zjActors.setId("1L");
        ZjActorsDTO zjActorsDTO = zjActorsMapper.toDto(zjActors);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZjActorsMockMvc.perform(post("/api/zj-actors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjActorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZjActors in the database
        List<ZjActors> zjActorsList = zjActorsRepository.findAll();
        assertThat(zjActorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllZjActors() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList
        restZjActorsMockMvc.perform(get("/api/zj-actors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zjActors.getId())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP.toString())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.toString())))
            .andExpect(jsonPath("$.[*].personName").value(hasItem(DEFAULT_PERSON_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getZjActors() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get the zjActors
        restZjActorsMockMvc.perform(get("/api/zj-actors/{id}", zjActors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zjActors.getId()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.toString()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP.toString()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.toString()))
            .andExpect(jsonPath("$.personName").value(DEFAULT_PERSON_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllZjActorsByProjectIdIsEqualToSomething() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where projectId equals to DEFAULT_PROJECT_ID
        defaultZjActorsShouldBeFound("projectId.equals=" + DEFAULT_PROJECT_ID);

        // Get all the zjActorsList where projectId equals to UPDATED_PROJECT_ID
        defaultZjActorsShouldNotBeFound("projectId.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllZjActorsByProjectIdIsInShouldWork() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where projectId in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultZjActorsShouldBeFound("projectId.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the zjActorsList where projectId equals to UPDATED_PROJECT_ID
        defaultZjActorsShouldNotBeFound("projectId.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllZjActorsByProjectIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where projectId is not null
        defaultZjActorsShouldBeFound("projectId.specified=true");

        // Get all the zjActorsList where projectId is null
        defaultZjActorsShouldNotBeFound("projectId.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjActorsByStepIsEqualToSomething() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where step equals to DEFAULT_STEP
        defaultZjActorsShouldBeFound("step.equals=" + DEFAULT_STEP);

        // Get all the zjActorsList where step equals to UPDATED_STEP
        defaultZjActorsShouldNotBeFound("step.equals=" + UPDATED_STEP);
    }

    @Test
    @Transactional
    public void getAllZjActorsByStepIsInShouldWork() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where step in DEFAULT_STEP or UPDATED_STEP
        defaultZjActorsShouldBeFound("step.in=" + DEFAULT_STEP + "," + UPDATED_STEP);

        // Get all the zjActorsList where step equals to UPDATED_STEP
        defaultZjActorsShouldNotBeFound("step.in=" + UPDATED_STEP);
    }

    @Test
    @Transactional
    public void getAllZjActorsByStepIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where step is not null
        defaultZjActorsShouldBeFound("step.specified=true");

        // Get all the zjActorsList where step is null
        defaultZjActorsShouldNotBeFound("step.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjActorsByPersonIdIsEqualToSomething() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where personId equals to DEFAULT_PERSON_ID
        defaultZjActorsShouldBeFound("personId.equals=" + DEFAULT_PERSON_ID);

        // Get all the zjActorsList where personId equals to UPDATED_PERSON_ID
        defaultZjActorsShouldNotBeFound("personId.equals=" + UPDATED_PERSON_ID);
    }

    @Test
    @Transactional
    public void getAllZjActorsByPersonIdIsInShouldWork() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where personId in DEFAULT_PERSON_ID or UPDATED_PERSON_ID
        defaultZjActorsShouldBeFound("personId.in=" + DEFAULT_PERSON_ID + "," + UPDATED_PERSON_ID);

        // Get all the zjActorsList where personId equals to UPDATED_PERSON_ID
        defaultZjActorsShouldNotBeFound("personId.in=" + UPDATED_PERSON_ID);
    }

    @Test
    @Transactional
    public void getAllZjActorsByPersonIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where personId is not null
        defaultZjActorsShouldBeFound("personId.specified=true");

        // Get all the zjActorsList where personId is null
        defaultZjActorsShouldNotBeFound("personId.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjActorsByPersonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where personName equals to DEFAULT_PERSON_NAME
        defaultZjActorsShouldBeFound("personName.equals=" + DEFAULT_PERSON_NAME);

        // Get all the zjActorsList where personName equals to UPDATED_PERSON_NAME
        defaultZjActorsShouldNotBeFound("personName.equals=" + UPDATED_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllZjActorsByPersonNameIsInShouldWork() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where personName in DEFAULT_PERSON_NAME or UPDATED_PERSON_NAME
        defaultZjActorsShouldBeFound("personName.in=" + DEFAULT_PERSON_NAME + "," + UPDATED_PERSON_NAME);

        // Get all the zjActorsList where personName equals to UPDATED_PERSON_NAME
        defaultZjActorsShouldNotBeFound("personName.in=" + UPDATED_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllZjActorsByPersonNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        // Get all the zjActorsList where personName is not null
        defaultZjActorsShouldBeFound("personName.specified=true");

        // Get all the zjActorsList where personName is null
        defaultZjActorsShouldNotBeFound("personName.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultZjActorsShouldBeFound(String filter) throws Exception {
        restZjActorsMockMvc.perform(get("/api/zj-actors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zjActors.getId())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP.toString())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.toString())))
            .andExpect(jsonPath("$.[*].personName").value(hasItem(DEFAULT_PERSON_NAME.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultZjActorsShouldNotBeFound(String filter) throws Exception {
        restZjActorsMockMvc.perform(get("/api/zj-actors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingZjActors() throws Exception {
        // Get the zjActors
        restZjActorsMockMvc.perform(get("/api/zj-actors/{id}", "-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZjActors() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        int databaseSizeBeforeUpdate = zjActorsRepository.findAll().size();

        // Update the zjActors
        ZjActors updatedZjActors = zjActorsRepository.findById(zjActors.getId()).get();
        // Disconnect from session so that the updates on updatedZjActors are not directly saved in db
        em.detach(updatedZjActors);
        updatedZjActors
            .projectId(UPDATED_PROJECT_ID)
            .step(UPDATED_STEP)
            .personId(UPDATED_PERSON_ID)
            .personName(UPDATED_PERSON_NAME);
        ZjActorsDTO zjActorsDTO = zjActorsMapper.toDto(updatedZjActors);

        restZjActorsMockMvc.perform(put("/api/zj-actors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjActorsDTO)))
            .andExpect(status().isOk());

        // Validate the ZjActors in the database
        List<ZjActors> zjActorsList = zjActorsRepository.findAll();
        assertThat(zjActorsList).hasSize(databaseSizeBeforeUpdate);
        ZjActors testZjActors = zjActorsList.get(zjActorsList.size() - 1);
        assertThat(testZjActors.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testZjActors.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testZjActors.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testZjActors.getPersonName()).isEqualTo(UPDATED_PERSON_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingZjActors() throws Exception {
        int databaseSizeBeforeUpdate = zjActorsRepository.findAll().size();

        // Create the ZjActors
        ZjActorsDTO zjActorsDTO = zjActorsMapper.toDto(zjActors);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZjActorsMockMvc.perform(put("/api/zj-actors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjActorsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZjActors in the database
        List<ZjActors> zjActorsList = zjActorsRepository.findAll();
        assertThat(zjActorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZjActors() throws Exception {
        // Initialize the database
        zjActorsRepository.saveAndFlush(zjActors);

        int databaseSizeBeforeDelete = zjActorsRepository.findAll().size();

        // Get the zjActors
        restZjActorsMockMvc.perform(delete("/api/zj-actors/{id}", zjActors.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZjActors> zjActorsList = zjActorsRepository.findAll();
        assertThat(zjActorsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZjActors.class);
        ZjActors zjActors1 = new ZjActors();
        zjActors1.setId("1L");
        ZjActors zjActors2 = new ZjActors();
        zjActors2.setId(zjActors1.getId());
        assertThat(zjActors1).isEqualTo(zjActors2);
        zjActors2.setId("");
        assertThat(zjActors1).isNotEqualTo(zjActors2);
        zjActors1.setId(null);
        assertThat(zjActors1).isNotEqualTo(zjActors2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZjActorsDTO.class);
        ZjActorsDTO zjActorsDTO1 = new ZjActorsDTO();
        zjActorsDTO1.setId("1L");
        ZjActorsDTO zjActorsDTO2 = new ZjActorsDTO();
        assertThat(zjActorsDTO1).isNotEqualTo(zjActorsDTO2);
        zjActorsDTO2.setId(zjActorsDTO1.getId());
        assertThat(zjActorsDTO1).isEqualTo(zjActorsDTO2);
        zjActorsDTO2.setId("");
        assertThat(zjActorsDTO1).isNotEqualTo(zjActorsDTO2);
        zjActorsDTO1.setId(null);
        assertThat(zjActorsDTO1).isNotEqualTo(zjActorsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zjActorsMapper.fromId("").getId()).isEqualTo(42);
        assertThat(zjActorsMapper.fromId(null)).isNull();
    }
}
