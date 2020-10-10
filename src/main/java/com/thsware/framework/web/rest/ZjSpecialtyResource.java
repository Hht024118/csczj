package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjSpecialtyService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjSpecialtyDTO;
import com.thsware.framework.service.dto.ZjSpecialtyCriteria;
import com.thsware.framework.service.ZjSpecialtyQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import liquibase.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing ZjSpecialty.
 */
@RestController
@RequestMapping("/api")
public class ZjSpecialtyResource {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyResource.class);

    private static final String ENTITY_NAME = "zjSpecialty";

    private final ZjSpecialtyService zjSpecialtyService;

    private final ZjSpecialtyQueryService zjSpecialtyQueryService;

    public ZjSpecialtyResource(ZjSpecialtyService zjSpecialtyService, ZjSpecialtyQueryService zjSpecialtyQueryService) {
        this.zjSpecialtyService = zjSpecialtyService;
        this.zjSpecialtyQueryService = zjSpecialtyQueryService;
    }

    /**
     * POST  /zj-specialties : Create a new zjSpecialty.
     *
     * @param zjSpecialtyDTO the zjSpecialtyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjSpecialtyDTO, or with status 400 (Bad Request) if the zjSpecialty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-specialties")
    @Timed
    public ResponseEntity<ZjSpecialtyDTO> createZjSpecialty(@Valid @RequestBody ZjSpecialtyDTO zjSpecialtyDTO) throws URISyntaxException {
        log.debug("REST request to save ZjSpecialty : {}", zjSpecialtyDTO);
        if (zjSpecialtyDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjSpecialty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjSpecialtyDTO result = zjSpecialtyService.save(zjSpecialtyDTO);
        return ResponseEntity.created(new URI("/api/zj-specialties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-specialties : Updates an existing zjSpecialty.
     *
     * @param zjSpecialtyDTO the zjSpecialtyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjSpecialtyDTO,
     * or with status 400 (Bad Request) if the zjSpecialtyDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjSpecialtyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-specialties")
    @Timed
    public ResponseEntity<ZjSpecialtyDTO> updateZjSpecialty(@Valid @RequestBody ZjSpecialtyDTO zjSpecialtyDTO) throws URISyntaxException {
        log.debug("REST request to update ZjSpecialty : {}", zjSpecialtyDTO);
        if (zjSpecialtyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjSpecialtyDTO result = zjSpecialtyService.save(zjSpecialtyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjSpecialtyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-specialties : get all the zjSpecialties.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjSpecialties in body
     */
    @GetMapping("/zj-specialties")
    @Timed
    public ResponseEntity<List<ZjSpecialtyDTO>> getAllZjSpecialties(ZjSpecialtyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjSpecialties by criteria: {}", criteria);
        Page<ZjSpecialtyDTO> page = zjSpecialtyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-specialties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-specialties/:id : get the "id" zjSpecialty.
     *
     * @param id the id of the zjSpecialtyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjSpecialtyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-specialties/{id}")
    @Timed
    public ResponseEntity<ZjSpecialtyDTO> getZjSpecialty(@PathVariable String id) {
        log.debug("REST request to get ZjSpecialty : {}", id);
        Optional<ZjSpecialtyDTO> zjSpecialtyDTO = zjSpecialtyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjSpecialtyDTO);
    }

    /**
     * GET  /zj-specialties/PublishIdeas:id : get the "id" zjSpecialty.
     *
     * @param id the id of the zjSpecialtyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjSpecialtyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-specialties/get-publish-id/{id}")
    @Timed
    public Map<String, Object> getPublishIdeas(@PathVariable String id) {
        log.debug("REST request to get ZjSpecialty : {}", id);
        return zjSpecialtyService.getPublishIdeas(id);
    }

    /**
     * DELETE  /zj-specialties/:id : delete the "id" zjSpecialty.
     *
     * @param ids the id of the zjSpecialtyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-specialties/{ids}")
    @Timed
    public ResponseEntity<Void> deleteZjSpecialty(@PathVariable String ids) {
        log.debug("REST request to delete ZjSpecialty : {}", ids);
        if (StringUtils.isNotEmpty(ids)) {
            String[] idList = ids.split(",");
            for (String id : idList) {
                zjSpecialtyService.delete(id);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, ids.toString())).build();
    }

    /**
     * 清空工程里的编制人信息
     * @param zjProjectId
     * @return
     */
    @PostMapping("/zj-specialties/clean-bzr/{zjProjectId}")
    @Timed
    public boolean cleanBzr(@PathVariable String zjProjectId) {
        zjSpecialtyService.cleanBzr(zjProjectId);
        return true;
    }

    /**
     * 清空工程审批人员里二级审核人的信息
     * @param zjProjectId
     * @return
     */
    @PostMapping("/zj-specialties/clean-shr/{zjProjectId}/{type}")
    @Timed
    public boolean cleanEjshr(@PathVariable String zjProjectId,@PathVariable String type) {
        zjSpecialtyService.cleanShr(zjProjectId, type);
        return true;
    }

}
