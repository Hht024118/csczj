package com.thsware.framework.web.rest;

import com.thsware.framework.CsczjApp;

import com.thsware.framework.domain.ZjProjectChangelog;
import com.thsware.framework.repository.ZjProjectChangelogRepository;
import com.thsware.framework.service.ZjProjectChangelogService;
import com.thsware.framework.service.dto.ZjProjectChangelogDTO;
import com.thsware.framework.service.mapper.ZjProjectChangelogMapper;
import com.thsware.framework.web.rest.errors.ExceptionTranslator;
import com.thsware.framework.service.dto.ZjProjectChangelogCriteria;
import com.thsware.framework.service.ZjProjectChangelogQueryService;

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
 * Test class for the ZjProjectChangelogResource REST controller.
 *
 * @see ZjProjectChangelogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsczjApp.class)
public class ZjProjectChangelogResourceIntTest {

    private static final String DEFAULT_PROJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALTY_ID = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALTY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CHANGE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHANGE_VERSION = 1;
    private static final Integer UPDATED_CHANGE_VERSION = 2;

    private static final String DEFAULT_CHANGE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_REASON = "BBBBBBBBBB";

    private static final Instant DEFAULT_CHANGE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHANGE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OLD_FIELD_ID = "AAAAAAAAAA";
    private static final String UPDATED_OLD_FIELD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OLD_FIELD_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_OLD_FIELD_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_FIELD_ID = "AAAAAAAAAA";
    private static final String UPDATED_NEW_FIELD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_FIELD_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_NEW_FIELD_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FLOW_STATE = "AAAAAAAAAA";
    private static final String UPDATED_FLOW_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MULTI_TENANCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_MULTI_TENANCY_ID = "BBBBBBBBBB";

    @Autowired
    private ZjProjectChangelogRepository zjProjectChangelogRepository;


    @Autowired
    private ZjProjectChangelogMapper zjProjectChangelogMapper;
    

    @Autowired
    private ZjProjectChangelogService zjProjectChangelogService;

    @Autowired
    private ZjProjectChangelogQueryService zjProjectChangelogQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZjProjectChangelogMockMvc;

    private ZjProjectChangelog zjProjectChangelog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZjProjectChangelogResource zjProjectChangelogResource = new ZjProjectChangelogResource(zjProjectChangelogService, zjProjectChangelogQueryService);
        this.restZjProjectChangelogMockMvc = MockMvcBuilders.standaloneSetup(zjProjectChangelogResource)
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
    public static ZjProjectChangelog createEntity(EntityManager em) {
        ZjProjectChangelog zjProjectChangelog = new ZjProjectChangelog()
            .projectId(DEFAULT_PROJECT_ID)
            .specialtyId(DEFAULT_SPECIALTY_ID)
            .changeType(DEFAULT_CHANGE_TYPE)
            .changeVersion(DEFAULT_CHANGE_VERSION)
            .changeReason(DEFAULT_CHANGE_REASON)
            .changeTime(DEFAULT_CHANGE_TIME)
            .oldFieldId(DEFAULT_OLD_FIELD_ID)
            .oldFieldText(DEFAULT_OLD_FIELD_TEXT)
            .newFieldId(DEFAULT_NEW_FIELD_ID)
            .newFieldText(DEFAULT_NEW_FIELD_TEXT)
            .flowState(DEFAULT_FLOW_STATE)
            .remark(DEFAULT_REMARK)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .multiTenancyId(DEFAULT_MULTI_TENANCY_ID);
        return zjProjectChangelog;
    }

    @Before
    public void initTest() {
        zjProjectChangelog = createEntity(em);
    }

    @Test
    @Transactional
    public void createZjProjectChangelog() throws Exception {
        int databaseSizeBeforeCreate = zjProjectChangelogRepository.findAll().size();

        // Create the ZjProjectChangelog
        ZjProjectChangelogDTO zjProjectChangelogDTO = zjProjectChangelogMapper.toDto(zjProjectChangelog);
        restZjProjectChangelogMockMvc.perform(post("/api/zj-project-changelogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjProjectChangelogDTO)))
            .andExpect(status().isCreated());

        // Validate the ZjProjectChangelog in the database
        List<ZjProjectChangelog> zjProjectChangelogList = zjProjectChangelogRepository.findAll();
        assertThat(zjProjectChangelogList).hasSize(databaseSizeBeforeCreate + 1);
        ZjProjectChangelog testZjProjectChangelog = zjProjectChangelogList.get(zjProjectChangelogList.size() - 1);
        assertThat(testZjProjectChangelog.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testZjProjectChangelog.getSpecialtyId()).isEqualTo(DEFAULT_SPECIALTY_ID);
        assertThat(testZjProjectChangelog.getChangeType()).isEqualTo(DEFAULT_CHANGE_TYPE);
        assertThat(testZjProjectChangelog.getChangeVersion()).isEqualTo(DEFAULT_CHANGE_VERSION);
        assertThat(testZjProjectChangelog.getChangeReason()).isEqualTo(DEFAULT_CHANGE_REASON);
        assertThat(testZjProjectChangelog.getChangeTime()).isEqualTo(DEFAULT_CHANGE_TIME);
        assertThat(testZjProjectChangelog.getOldFieldId()).isEqualTo(DEFAULT_OLD_FIELD_ID);
        assertThat(testZjProjectChangelog.getOldFieldText()).isEqualTo(DEFAULT_OLD_FIELD_TEXT);
        assertThat(testZjProjectChangelog.getNewFieldId()).isEqualTo(DEFAULT_NEW_FIELD_ID);
        assertThat(testZjProjectChangelog.getNewFieldText()).isEqualTo(DEFAULT_NEW_FIELD_TEXT);
        assertThat(testZjProjectChangelog.getFlowState()).isEqualTo(DEFAULT_FLOW_STATE);
        assertThat(testZjProjectChangelog.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testZjProjectChangelog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testZjProjectChangelog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testZjProjectChangelog.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testZjProjectChangelog.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testZjProjectChangelog.getMultiTenancyId()).isEqualTo(DEFAULT_MULTI_TENANCY_ID);
    }

    @Test
    @Transactional
    public void createZjProjectChangelogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zjProjectChangelogRepository.findAll().size();

        // Create the ZjProjectChangelog with an existing ID
        zjProjectChangelog.setId("1L");
        ZjProjectChangelogDTO zjProjectChangelogDTO = zjProjectChangelogMapper.toDto(zjProjectChangelog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZjProjectChangelogMockMvc.perform(post("/api/zj-project-changelogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjProjectChangelogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZjProjectChangelog in the database
        List<ZjProjectChangelog> zjProjectChangelogList = zjProjectChangelogRepository.findAll();
        assertThat(zjProjectChangelogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogs() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList
        restZjProjectChangelogMockMvc.perform(get("/api/zj-project-changelogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zjProjectChangelog.getId())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].specialtyId").value(hasItem(DEFAULT_SPECIALTY_ID.toString())))
            .andExpect(jsonPath("$.[*].changeType").value(hasItem(DEFAULT_CHANGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].changeVersion").value(hasItem(DEFAULT_CHANGE_VERSION)))
            .andExpect(jsonPath("$.[*].changeReason").value(hasItem(DEFAULT_CHANGE_REASON.toString())))
            .andExpect(jsonPath("$.[*].changeTime").value(hasItem(DEFAULT_CHANGE_TIME.toString())))
            .andExpect(jsonPath("$.[*].oldFieldId").value(hasItem(DEFAULT_OLD_FIELD_ID.toString())))
            .andExpect(jsonPath("$.[*].oldFieldText").value(hasItem(DEFAULT_OLD_FIELD_TEXT.toString())))
            .andExpect(jsonPath("$.[*].newFieldId").value(hasItem(DEFAULT_NEW_FIELD_ID.toString())))
            .andExpect(jsonPath("$.[*].newFieldText").value(hasItem(DEFAULT_NEW_FIELD_TEXT.toString())))
            .andExpect(jsonPath("$.[*].flowState").value(hasItem(DEFAULT_FLOW_STATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].multiTenancyId").value(hasItem(DEFAULT_MULTI_TENANCY_ID.toString())));
    }
    

    @Test
    @Transactional
    public void getZjProjectChangelog() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get the zjProjectChangelog
        restZjProjectChangelogMockMvc.perform(get("/api/zj-project-changelogs/{id}", zjProjectChangelog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zjProjectChangelog.getId()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.toString()))
            .andExpect(jsonPath("$.specialtyId").value(DEFAULT_SPECIALTY_ID.toString()))
            .andExpect(jsonPath("$.changeType").value(DEFAULT_CHANGE_TYPE.toString()))
            .andExpect(jsonPath("$.changeVersion").value(DEFAULT_CHANGE_VERSION))
            .andExpect(jsonPath("$.changeReason").value(DEFAULT_CHANGE_REASON.toString()))
            .andExpect(jsonPath("$.changeTime").value(DEFAULT_CHANGE_TIME.toString()))
            .andExpect(jsonPath("$.oldFieldId").value(DEFAULT_OLD_FIELD_ID.toString()))
            .andExpect(jsonPath("$.oldFieldText").value(DEFAULT_OLD_FIELD_TEXT.toString()))
            .andExpect(jsonPath("$.newFieldId").value(DEFAULT_NEW_FIELD_ID.toString()))
            .andExpect(jsonPath("$.newFieldText").value(DEFAULT_NEW_FIELD_TEXT.toString()))
            .andExpect(jsonPath("$.flowState").value(DEFAULT_FLOW_STATE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.multiTenancyId").value(DEFAULT_MULTI_TENANCY_ID.toString()));
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByProjectIdIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where projectId equals to DEFAULT_PROJECT_ID
        defaultZjProjectChangelogShouldBeFound("projectId.equals=" + DEFAULT_PROJECT_ID);

        // Get all the zjProjectChangelogList where projectId equals to UPDATED_PROJECT_ID
        defaultZjProjectChangelogShouldNotBeFound("projectId.equals=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByProjectIdIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where projectId in DEFAULT_PROJECT_ID or UPDATED_PROJECT_ID
        defaultZjProjectChangelogShouldBeFound("projectId.in=" + DEFAULT_PROJECT_ID + "," + UPDATED_PROJECT_ID);

        // Get all the zjProjectChangelogList where projectId equals to UPDATED_PROJECT_ID
        defaultZjProjectChangelogShouldNotBeFound("projectId.in=" + UPDATED_PROJECT_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByProjectIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where projectId is not null
        defaultZjProjectChangelogShouldBeFound("projectId.specified=true");

        // Get all the zjProjectChangelogList where projectId is null
        defaultZjProjectChangelogShouldNotBeFound("projectId.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsBySpecialtyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where specialtyId equals to DEFAULT_SPECIALTY_ID
        defaultZjProjectChangelogShouldBeFound("specialtyId.equals=" + DEFAULT_SPECIALTY_ID);

        // Get all the zjProjectChangelogList where specialtyId equals to UPDATED_SPECIALTY_ID
        defaultZjProjectChangelogShouldNotBeFound("specialtyId.equals=" + UPDATED_SPECIALTY_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsBySpecialtyIdIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where specialtyId in DEFAULT_SPECIALTY_ID or UPDATED_SPECIALTY_ID
        defaultZjProjectChangelogShouldBeFound("specialtyId.in=" + DEFAULT_SPECIALTY_ID + "," + UPDATED_SPECIALTY_ID);

        // Get all the zjProjectChangelogList where specialtyId equals to UPDATED_SPECIALTY_ID
        defaultZjProjectChangelogShouldNotBeFound("specialtyId.in=" + UPDATED_SPECIALTY_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsBySpecialtyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where specialtyId is not null
        defaultZjProjectChangelogShouldBeFound("specialtyId.specified=true");

        // Get all the zjProjectChangelogList where specialtyId is null
        defaultZjProjectChangelogShouldNotBeFound("specialtyId.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeType equals to DEFAULT_CHANGE_TYPE
        defaultZjProjectChangelogShouldBeFound("changeType.equals=" + DEFAULT_CHANGE_TYPE);

        // Get all the zjProjectChangelogList where changeType equals to UPDATED_CHANGE_TYPE
        defaultZjProjectChangelogShouldNotBeFound("changeType.equals=" + UPDATED_CHANGE_TYPE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeType in DEFAULT_CHANGE_TYPE or UPDATED_CHANGE_TYPE
        defaultZjProjectChangelogShouldBeFound("changeType.in=" + DEFAULT_CHANGE_TYPE + "," + UPDATED_CHANGE_TYPE);

        // Get all the zjProjectChangelogList where changeType equals to UPDATED_CHANGE_TYPE
        defaultZjProjectChangelogShouldNotBeFound("changeType.in=" + UPDATED_CHANGE_TYPE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeType is not null
        defaultZjProjectChangelogShouldBeFound("changeType.specified=true");

        // Get all the zjProjectChangelogList where changeType is null
        defaultZjProjectChangelogShouldNotBeFound("changeType.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeVersion equals to DEFAULT_CHANGE_VERSION
        defaultZjProjectChangelogShouldBeFound("changeVersion.equals=" + DEFAULT_CHANGE_VERSION);

        // Get all the zjProjectChangelogList where changeVersion equals to UPDATED_CHANGE_VERSION
        defaultZjProjectChangelogShouldNotBeFound("changeVersion.equals=" + UPDATED_CHANGE_VERSION);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeVersionIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeVersion in DEFAULT_CHANGE_VERSION or UPDATED_CHANGE_VERSION
        defaultZjProjectChangelogShouldBeFound("changeVersion.in=" + DEFAULT_CHANGE_VERSION + "," + UPDATED_CHANGE_VERSION);

        // Get all the zjProjectChangelogList where changeVersion equals to UPDATED_CHANGE_VERSION
        defaultZjProjectChangelogShouldNotBeFound("changeVersion.in=" + UPDATED_CHANGE_VERSION);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeVersion is not null
        defaultZjProjectChangelogShouldBeFound("changeVersion.specified=true");

        // Get all the zjProjectChangelogList where changeVersion is null
        defaultZjProjectChangelogShouldNotBeFound("changeVersion.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeVersion greater than or equals to DEFAULT_CHANGE_VERSION
        defaultZjProjectChangelogShouldBeFound("changeVersion.greaterOrEqualThan=" + DEFAULT_CHANGE_VERSION);

        // Get all the zjProjectChangelogList where changeVersion greater than or equals to UPDATED_CHANGE_VERSION
        defaultZjProjectChangelogShouldNotBeFound("changeVersion.greaterOrEqualThan=" + UPDATED_CHANGE_VERSION);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeVersion less than or equals to DEFAULT_CHANGE_VERSION
        defaultZjProjectChangelogShouldNotBeFound("changeVersion.lessThan=" + DEFAULT_CHANGE_VERSION);

        // Get all the zjProjectChangelogList where changeVersion less than or equals to UPDATED_CHANGE_VERSION
        defaultZjProjectChangelogShouldBeFound("changeVersion.lessThan=" + UPDATED_CHANGE_VERSION);
    }


    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeReason equals to DEFAULT_CHANGE_REASON
        defaultZjProjectChangelogShouldBeFound("changeReason.equals=" + DEFAULT_CHANGE_REASON);

        // Get all the zjProjectChangelogList where changeReason equals to UPDATED_CHANGE_REASON
        defaultZjProjectChangelogShouldNotBeFound("changeReason.equals=" + UPDATED_CHANGE_REASON);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeReasonIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeReason in DEFAULT_CHANGE_REASON or UPDATED_CHANGE_REASON
        defaultZjProjectChangelogShouldBeFound("changeReason.in=" + DEFAULT_CHANGE_REASON + "," + UPDATED_CHANGE_REASON);

        // Get all the zjProjectChangelogList where changeReason equals to UPDATED_CHANGE_REASON
        defaultZjProjectChangelogShouldNotBeFound("changeReason.in=" + UPDATED_CHANGE_REASON);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeReason is not null
        defaultZjProjectChangelogShouldBeFound("changeReason.specified=true");

        // Get all the zjProjectChangelogList where changeReason is null
        defaultZjProjectChangelogShouldNotBeFound("changeReason.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeTime equals to DEFAULT_CHANGE_TIME
        defaultZjProjectChangelogShouldBeFound("changeTime.equals=" + DEFAULT_CHANGE_TIME);

        // Get all the zjProjectChangelogList where changeTime equals to UPDATED_CHANGE_TIME
        defaultZjProjectChangelogShouldNotBeFound("changeTime.equals=" + UPDATED_CHANGE_TIME);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeTimeIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeTime in DEFAULT_CHANGE_TIME or UPDATED_CHANGE_TIME
        defaultZjProjectChangelogShouldBeFound("changeTime.in=" + DEFAULT_CHANGE_TIME + "," + UPDATED_CHANGE_TIME);

        // Get all the zjProjectChangelogList where changeTime equals to UPDATED_CHANGE_TIME
        defaultZjProjectChangelogShouldNotBeFound("changeTime.in=" + UPDATED_CHANGE_TIME);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByChangeTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where changeTime is not null
        defaultZjProjectChangelogShouldBeFound("changeTime.specified=true");

        // Get all the zjProjectChangelogList where changeTime is null
        defaultZjProjectChangelogShouldNotBeFound("changeTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByOldFieldIdIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where oldFieldId equals to DEFAULT_OLD_FIELD_ID
        defaultZjProjectChangelogShouldBeFound("oldFieldId.equals=" + DEFAULT_OLD_FIELD_ID);

        // Get all the zjProjectChangelogList where oldFieldId equals to UPDATED_OLD_FIELD_ID
        defaultZjProjectChangelogShouldNotBeFound("oldFieldId.equals=" + UPDATED_OLD_FIELD_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByOldFieldIdIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where oldFieldId in DEFAULT_OLD_FIELD_ID or UPDATED_OLD_FIELD_ID
        defaultZjProjectChangelogShouldBeFound("oldFieldId.in=" + DEFAULT_OLD_FIELD_ID + "," + UPDATED_OLD_FIELD_ID);

        // Get all the zjProjectChangelogList where oldFieldId equals to UPDATED_OLD_FIELD_ID
        defaultZjProjectChangelogShouldNotBeFound("oldFieldId.in=" + UPDATED_OLD_FIELD_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByOldFieldIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where oldFieldId is not null
        defaultZjProjectChangelogShouldBeFound("oldFieldId.specified=true");

        // Get all the zjProjectChangelogList where oldFieldId is null
        defaultZjProjectChangelogShouldNotBeFound("oldFieldId.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByOldFieldTextIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where oldFieldText equals to DEFAULT_OLD_FIELD_TEXT
        defaultZjProjectChangelogShouldBeFound("oldFieldText.equals=" + DEFAULT_OLD_FIELD_TEXT);

        // Get all the zjProjectChangelogList where oldFieldText equals to UPDATED_OLD_FIELD_TEXT
        defaultZjProjectChangelogShouldNotBeFound("oldFieldText.equals=" + UPDATED_OLD_FIELD_TEXT);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByOldFieldTextIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where oldFieldText in DEFAULT_OLD_FIELD_TEXT or UPDATED_OLD_FIELD_TEXT
        defaultZjProjectChangelogShouldBeFound("oldFieldText.in=" + DEFAULT_OLD_FIELD_TEXT + "," + UPDATED_OLD_FIELD_TEXT);

        // Get all the zjProjectChangelogList where oldFieldText equals to UPDATED_OLD_FIELD_TEXT
        defaultZjProjectChangelogShouldNotBeFound("oldFieldText.in=" + UPDATED_OLD_FIELD_TEXT);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByOldFieldTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where oldFieldText is not null
        defaultZjProjectChangelogShouldBeFound("oldFieldText.specified=true");

        // Get all the zjProjectChangelogList where oldFieldText is null
        defaultZjProjectChangelogShouldNotBeFound("oldFieldText.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByNewFieldIdIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where newFieldId equals to DEFAULT_NEW_FIELD_ID
        defaultZjProjectChangelogShouldBeFound("newFieldId.equals=" + DEFAULT_NEW_FIELD_ID);

        // Get all the zjProjectChangelogList where newFieldId equals to UPDATED_NEW_FIELD_ID
        defaultZjProjectChangelogShouldNotBeFound("newFieldId.equals=" + UPDATED_NEW_FIELD_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByNewFieldIdIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where newFieldId in DEFAULT_NEW_FIELD_ID or UPDATED_NEW_FIELD_ID
        defaultZjProjectChangelogShouldBeFound("newFieldId.in=" + DEFAULT_NEW_FIELD_ID + "," + UPDATED_NEW_FIELD_ID);

        // Get all the zjProjectChangelogList where newFieldId equals to UPDATED_NEW_FIELD_ID
        defaultZjProjectChangelogShouldNotBeFound("newFieldId.in=" + UPDATED_NEW_FIELD_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByNewFieldIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where newFieldId is not null
        defaultZjProjectChangelogShouldBeFound("newFieldId.specified=true");

        // Get all the zjProjectChangelogList where newFieldId is null
        defaultZjProjectChangelogShouldNotBeFound("newFieldId.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByNewFieldTextIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where newFieldText equals to DEFAULT_NEW_FIELD_TEXT
        defaultZjProjectChangelogShouldBeFound("newFieldText.equals=" + DEFAULT_NEW_FIELD_TEXT);

        // Get all the zjProjectChangelogList where newFieldText equals to UPDATED_NEW_FIELD_TEXT
        defaultZjProjectChangelogShouldNotBeFound("newFieldText.equals=" + UPDATED_NEW_FIELD_TEXT);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByNewFieldTextIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where newFieldText in DEFAULT_NEW_FIELD_TEXT or UPDATED_NEW_FIELD_TEXT
        defaultZjProjectChangelogShouldBeFound("newFieldText.in=" + DEFAULT_NEW_FIELD_TEXT + "," + UPDATED_NEW_FIELD_TEXT);

        // Get all the zjProjectChangelogList where newFieldText equals to UPDATED_NEW_FIELD_TEXT
        defaultZjProjectChangelogShouldNotBeFound("newFieldText.in=" + UPDATED_NEW_FIELD_TEXT);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByNewFieldTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where newFieldText is not null
        defaultZjProjectChangelogShouldBeFound("newFieldText.specified=true");

        // Get all the zjProjectChangelogList where newFieldText is null
        defaultZjProjectChangelogShouldNotBeFound("newFieldText.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByFlowStateIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where flowState equals to DEFAULT_FLOW_STATE
        defaultZjProjectChangelogShouldBeFound("flowState.equals=" + DEFAULT_FLOW_STATE);

        // Get all the zjProjectChangelogList where flowState equals to UPDATED_FLOW_STATE
        defaultZjProjectChangelogShouldNotBeFound("flowState.equals=" + UPDATED_FLOW_STATE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByFlowStateIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where flowState in DEFAULT_FLOW_STATE or UPDATED_FLOW_STATE
        defaultZjProjectChangelogShouldBeFound("flowState.in=" + DEFAULT_FLOW_STATE + "," + UPDATED_FLOW_STATE);

        // Get all the zjProjectChangelogList where flowState equals to UPDATED_FLOW_STATE
        defaultZjProjectChangelogShouldNotBeFound("flowState.in=" + UPDATED_FLOW_STATE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByFlowStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where flowState is not null
        defaultZjProjectChangelogShouldBeFound("flowState.specified=true");

        // Get all the zjProjectChangelogList where flowState is null
        defaultZjProjectChangelogShouldNotBeFound("flowState.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where remark equals to DEFAULT_REMARK
        defaultZjProjectChangelogShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the zjProjectChangelogList where remark equals to UPDATED_REMARK
        defaultZjProjectChangelogShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultZjProjectChangelogShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the zjProjectChangelogList where remark equals to UPDATED_REMARK
        defaultZjProjectChangelogShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where remark is not null
        defaultZjProjectChangelogShouldBeFound("remark.specified=true");

        // Get all the zjProjectChangelogList where remark is null
        defaultZjProjectChangelogShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where createdBy equals to DEFAULT_CREATED_BY
        defaultZjProjectChangelogShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the zjProjectChangelogList where createdBy equals to UPDATED_CREATED_BY
        defaultZjProjectChangelogShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultZjProjectChangelogShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the zjProjectChangelogList where createdBy equals to UPDATED_CREATED_BY
        defaultZjProjectChangelogShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where createdBy is not null
        defaultZjProjectChangelogShouldBeFound("createdBy.specified=true");

        // Get all the zjProjectChangelogList where createdBy is null
        defaultZjProjectChangelogShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where createdDate equals to DEFAULT_CREATED_DATE
        defaultZjProjectChangelogShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the zjProjectChangelogList where createdDate equals to UPDATED_CREATED_DATE
        defaultZjProjectChangelogShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultZjProjectChangelogShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the zjProjectChangelogList where createdDate equals to UPDATED_CREATED_DATE
        defaultZjProjectChangelogShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where createdDate is not null
        defaultZjProjectChangelogShouldBeFound("createdDate.specified=true");

        // Get all the zjProjectChangelogList where createdDate is null
        defaultZjProjectChangelogShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultZjProjectChangelogShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the zjProjectChangelogList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultZjProjectChangelogShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultZjProjectChangelogShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the zjProjectChangelogList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultZjProjectChangelogShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where lastModifiedBy is not null
        defaultZjProjectChangelogShouldBeFound("lastModifiedBy.specified=true");

        // Get all the zjProjectChangelogList where lastModifiedBy is null
        defaultZjProjectChangelogShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultZjProjectChangelogShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the zjProjectChangelogList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultZjProjectChangelogShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultZjProjectChangelogShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the zjProjectChangelogList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultZjProjectChangelogShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where lastModifiedDate is not null
        defaultZjProjectChangelogShouldBeFound("lastModifiedDate.specified=true");

        // Get all the zjProjectChangelogList where lastModifiedDate is null
        defaultZjProjectChangelogShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByMultiTenancyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where multiTenancyId equals to DEFAULT_MULTI_TENANCY_ID
        defaultZjProjectChangelogShouldBeFound("multiTenancyId.equals=" + DEFAULT_MULTI_TENANCY_ID);

        // Get all the zjProjectChangelogList where multiTenancyId equals to UPDATED_MULTI_TENANCY_ID
        defaultZjProjectChangelogShouldNotBeFound("multiTenancyId.equals=" + UPDATED_MULTI_TENANCY_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByMultiTenancyIdIsInShouldWork() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where multiTenancyId in DEFAULT_MULTI_TENANCY_ID or UPDATED_MULTI_TENANCY_ID
        defaultZjProjectChangelogShouldBeFound("multiTenancyId.in=" + DEFAULT_MULTI_TENANCY_ID + "," + UPDATED_MULTI_TENANCY_ID);

        // Get all the zjProjectChangelogList where multiTenancyId equals to UPDATED_MULTI_TENANCY_ID
        defaultZjProjectChangelogShouldNotBeFound("multiTenancyId.in=" + UPDATED_MULTI_TENANCY_ID);
    }

    @Test
    @Transactional
    public void getAllZjProjectChangelogsByMultiTenancyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        // Get all the zjProjectChangelogList where multiTenancyId is not null
        defaultZjProjectChangelogShouldBeFound("multiTenancyId.specified=true");

        // Get all the zjProjectChangelogList where multiTenancyId is null
        defaultZjProjectChangelogShouldNotBeFound("multiTenancyId.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultZjProjectChangelogShouldBeFound(String filter) throws Exception {
        restZjProjectChangelogMockMvc.perform(get("/api/zj-project-changelogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zjProjectChangelog.getId())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].specialtyId").value(hasItem(DEFAULT_SPECIALTY_ID.toString())))
            .andExpect(jsonPath("$.[*].changeType").value(hasItem(DEFAULT_CHANGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].changeVersion").value(hasItem(DEFAULT_CHANGE_VERSION)))
            .andExpect(jsonPath("$.[*].changeReason").value(hasItem(DEFAULT_CHANGE_REASON.toString())))
            .andExpect(jsonPath("$.[*].changeTime").value(hasItem(DEFAULT_CHANGE_TIME.toString())))
            .andExpect(jsonPath("$.[*].oldFieldId").value(hasItem(DEFAULT_OLD_FIELD_ID.toString())))
            .andExpect(jsonPath("$.[*].oldFieldText").value(hasItem(DEFAULT_OLD_FIELD_TEXT.toString())))
            .andExpect(jsonPath("$.[*].newFieldId").value(hasItem(DEFAULT_NEW_FIELD_ID.toString())))
            .andExpect(jsonPath("$.[*].newFieldText").value(hasItem(DEFAULT_NEW_FIELD_TEXT.toString())))
            .andExpect(jsonPath("$.[*].flowState").value(hasItem(DEFAULT_FLOW_STATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].multiTenancyId").value(hasItem(DEFAULT_MULTI_TENANCY_ID.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultZjProjectChangelogShouldNotBeFound(String filter) throws Exception {
        restZjProjectChangelogMockMvc.perform(get("/api/zj-project-changelogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingZjProjectChangelog() throws Exception {
        // Get the zjProjectChangelog
        restZjProjectChangelogMockMvc.perform(get("/api/zj-project-changelogs/{id}", "-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZjProjectChangelog() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        int databaseSizeBeforeUpdate = zjProjectChangelogRepository.findAll().size();

        // Update the zjProjectChangelog
        ZjProjectChangelog updatedZjProjectChangelog = zjProjectChangelogRepository.findById(zjProjectChangelog.getId()).get();
        // Disconnect from session so that the updates on updatedZjProjectChangelog are not directly saved in db
        em.detach(updatedZjProjectChangelog);
        updatedZjProjectChangelog
            .projectId(UPDATED_PROJECT_ID)
            .specialtyId(UPDATED_SPECIALTY_ID)
            .changeType(UPDATED_CHANGE_TYPE)
            .changeVersion(UPDATED_CHANGE_VERSION)
            .changeReason(UPDATED_CHANGE_REASON)
            .changeTime(UPDATED_CHANGE_TIME)
            .oldFieldId(UPDATED_OLD_FIELD_ID)
            .oldFieldText(UPDATED_OLD_FIELD_TEXT)
            .newFieldId(UPDATED_NEW_FIELD_ID)
            .newFieldText(UPDATED_NEW_FIELD_TEXT)
            .flowState(UPDATED_FLOW_STATE)
            .remark(UPDATED_REMARK)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .multiTenancyId(UPDATED_MULTI_TENANCY_ID);
        ZjProjectChangelogDTO zjProjectChangelogDTO = zjProjectChangelogMapper.toDto(updatedZjProjectChangelog);

        restZjProjectChangelogMockMvc.perform(put("/api/zj-project-changelogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjProjectChangelogDTO)))
            .andExpect(status().isOk());

        // Validate the ZjProjectChangelog in the database
        List<ZjProjectChangelog> zjProjectChangelogList = zjProjectChangelogRepository.findAll();
        assertThat(zjProjectChangelogList).hasSize(databaseSizeBeforeUpdate);
        ZjProjectChangelog testZjProjectChangelog = zjProjectChangelogList.get(zjProjectChangelogList.size() - 1);
        assertThat(testZjProjectChangelog.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testZjProjectChangelog.getSpecialtyId()).isEqualTo(UPDATED_SPECIALTY_ID);
        assertThat(testZjProjectChangelog.getChangeType()).isEqualTo(UPDATED_CHANGE_TYPE);
        assertThat(testZjProjectChangelog.getChangeVersion()).isEqualTo(UPDATED_CHANGE_VERSION);
        assertThat(testZjProjectChangelog.getChangeReason()).isEqualTo(UPDATED_CHANGE_REASON);
        assertThat(testZjProjectChangelog.getChangeTime()).isEqualTo(UPDATED_CHANGE_TIME);
        assertThat(testZjProjectChangelog.getOldFieldId()).isEqualTo(UPDATED_OLD_FIELD_ID);
        assertThat(testZjProjectChangelog.getOldFieldText()).isEqualTo(UPDATED_OLD_FIELD_TEXT);
        assertThat(testZjProjectChangelog.getNewFieldId()).isEqualTo(UPDATED_NEW_FIELD_ID);
        assertThat(testZjProjectChangelog.getNewFieldText()).isEqualTo(UPDATED_NEW_FIELD_TEXT);
        assertThat(testZjProjectChangelog.getFlowState()).isEqualTo(UPDATED_FLOW_STATE);
        assertThat(testZjProjectChangelog.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testZjProjectChangelog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testZjProjectChangelog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testZjProjectChangelog.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testZjProjectChangelog.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testZjProjectChangelog.getMultiTenancyId()).isEqualTo(UPDATED_MULTI_TENANCY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingZjProjectChangelog() throws Exception {
        int databaseSizeBeforeUpdate = zjProjectChangelogRepository.findAll().size();

        // Create the ZjProjectChangelog
        ZjProjectChangelogDTO zjProjectChangelogDTO = zjProjectChangelogMapper.toDto(zjProjectChangelog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZjProjectChangelogMockMvc.perform(put("/api/zj-project-changelogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zjProjectChangelogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZjProjectChangelog in the database
        List<ZjProjectChangelog> zjProjectChangelogList = zjProjectChangelogRepository.findAll();
        assertThat(zjProjectChangelogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZjProjectChangelog() throws Exception {
        // Initialize the database
        zjProjectChangelogRepository.saveAndFlush(zjProjectChangelog);

        int databaseSizeBeforeDelete = zjProjectChangelogRepository.findAll().size();

        // Get the zjProjectChangelog
        restZjProjectChangelogMockMvc.perform(delete("/api/zj-project-changelogs/{id}", zjProjectChangelog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZjProjectChangelog> zjProjectChangelogList = zjProjectChangelogRepository.findAll();
        assertThat(zjProjectChangelogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZjProjectChangelog.class);
        ZjProjectChangelog zjProjectChangelog1 = new ZjProjectChangelog();
        zjProjectChangelog1.setId("1L");
        ZjProjectChangelog zjProjectChangelog2 = new ZjProjectChangelog();
        zjProjectChangelog2.setId(zjProjectChangelog1.getId());
        assertThat(zjProjectChangelog1).isEqualTo(zjProjectChangelog2);
        zjProjectChangelog2.setId("");
        assertThat(zjProjectChangelog1).isNotEqualTo(zjProjectChangelog2);
        zjProjectChangelog1.setId(null);
        assertThat(zjProjectChangelog1).isNotEqualTo(zjProjectChangelog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZjProjectChangelogDTO.class);
        ZjProjectChangelogDTO zjProjectChangelogDTO1 = new ZjProjectChangelogDTO();
        zjProjectChangelogDTO1.setId("1L");
        ZjProjectChangelogDTO zjProjectChangelogDTO2 = new ZjProjectChangelogDTO();
        assertThat(zjProjectChangelogDTO1).isNotEqualTo(zjProjectChangelogDTO2);
        zjProjectChangelogDTO2.setId(zjProjectChangelogDTO1.getId());
        assertThat(zjProjectChangelogDTO1).isEqualTo(zjProjectChangelogDTO2);
        zjProjectChangelogDTO2.setId("");
        assertThat(zjProjectChangelogDTO1).isNotEqualTo(zjProjectChangelogDTO2);
        zjProjectChangelogDTO1.setId(null);
        assertThat(zjProjectChangelogDTO1).isNotEqualTo(zjProjectChangelogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zjProjectChangelogMapper.fromId("").getId()).isEqualTo(42);
        assertThat(zjProjectChangelogMapper.fromId(null)).isNull();
    }
}
