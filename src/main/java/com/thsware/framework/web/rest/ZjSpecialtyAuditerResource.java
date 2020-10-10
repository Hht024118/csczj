package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.domain.ZjSpecialtyAuditer;
import com.thsware.framework.service.ZjSpecialtyAuditerService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjSpecialtyAuditerDTO;
import com.thsware.framework.service.dto.ZjSpecialtyAuditerCriteria;
import com.thsware.framework.service.ZjSpecialtyAuditerQueryService;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.Optional;

/**
 * REST controller for managing ZjSpecialtyAuditer.
 */
@RestController
@RequestMapping("/api")
public class ZjSpecialtyAuditerResource {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyAuditerResource.class);

    private static final String ENTITY_NAME = "zjSpecialtyAuditer";

    private final ZjSpecialtyAuditerService zjSpecialtyAuditerService;

    private final ZjSpecialtyAuditerQueryService zjSpecialtyAuditerQueryService;

    public ZjSpecialtyAuditerResource(ZjSpecialtyAuditerService zjSpecialtyAuditerService, ZjSpecialtyAuditerQueryService zjSpecialtyAuditerQueryService) {
        this.zjSpecialtyAuditerService = zjSpecialtyAuditerService;
        this.zjSpecialtyAuditerQueryService = zjSpecialtyAuditerQueryService;
    }

    /**
     * POST  /zj-specialty-auditers : Create a new zjSpecialtyAuditer.
     *
     * @param zjSpecialtyAuditerDTO the zjSpecialtyAuditerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjSpecialtyAuditerDTO, or with status 400 (Bad Request) if the zjSpecialtyAuditer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-specialty-auditers")
    @Timed
    public ResponseEntity<ZjSpecialtyAuditerDTO> createZjSpecialtyAuditer(@Valid @RequestBody ZjSpecialtyAuditerDTO zjSpecialtyAuditerDTO) throws URISyntaxException {
        log.debug("REST request to save ZjSpecialtyAuditer : {}", zjSpecialtyAuditerDTO);
        if (zjSpecialtyAuditerDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjSpecialtyAuditer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjSpecialtyAuditerDTO result = zjSpecialtyAuditerService.save(zjSpecialtyAuditerDTO);
        return ResponseEntity.created(new URI("/api/zj-specialty-auditers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-specialty-auditers : Updates an existing zjSpecialtyAuditer.
     *
     * @param zjSpecialtyAuditerDTO the zjSpecialtyAuditerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjSpecialtyAuditerDTO,
     * or with status 400 (Bad Request) if the zjSpecialtyAuditerDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjSpecialtyAuditerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-specialty-auditers")
    @Timed
    public ResponseEntity<ZjSpecialtyAuditerDTO> updateZjSpecialtyAuditer(@Valid @RequestBody ZjSpecialtyAuditerDTO zjSpecialtyAuditerDTO) throws URISyntaxException {
        log.debug("REST request to update ZjSpecialtyAuditer : {}", zjSpecialtyAuditerDTO);
        if (zjSpecialtyAuditerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjSpecialtyAuditerDTO result = zjSpecialtyAuditerService.save(zjSpecialtyAuditerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjSpecialtyAuditerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-specialty-auditers : get all the zjSpecialtyAuditers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjSpecialtyAuditers in body
     */
    @GetMapping("/zj-specialty-auditers")
    @Timed
    public ResponseEntity<List<ZjSpecialtyAuditerDTO>> getAllZjSpecialtyAuditers(ZjSpecialtyAuditerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjSpecialtyAuditers by criteria: {}", criteria);
        Page<ZjSpecialtyAuditerDTO> page = zjSpecialtyAuditerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-specialty-auditers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-specialty-auditers/:id : get the "id" zjSpecialtyAuditer.
     *
     * @param id the id of the zjSpecialtyAuditerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjSpecialtyAuditerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-specialty-auditers/{id}")
    @Timed
    public ResponseEntity<ZjSpecialtyAuditerDTO> getZjSpecialtyAuditer(@PathVariable String id) {
        log.debug("REST request to get ZjSpecialtyAuditer : {}", id);
        Optional<ZjSpecialtyAuditerDTO> zjSpecialtyAuditerDTO = zjSpecialtyAuditerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjSpecialtyAuditerDTO);
    }

    /**
     * DELETE  /zj-specialty-auditers/:id : delete the "id" zjSpecialtyAuditer.
     *
     * @param id the id of the zjSpecialtyAuditerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-specialty-auditers/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjSpecialtyAuditer(@PathVariable String id) {
        log.debug("REST request to delete ZjSpecialtyAuditer : {}", id);
        zjSpecialtyAuditerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }



    @GetMapping("/zj-specialty-auditers/find-all-by-zj-specialty-id/{zjSpecialtyId}")
    @Timed
    public List<ZjSpecialtyAuditer> findAuditerBySpecialtyId(@PathVariable String zjSpecialtyId) {
        log.debug("REST request to get ZjSpecialtyAuditer : {}", zjSpecialtyId);
        return zjSpecialtyAuditerService.findAllByZjSpecialtyId(zjSpecialtyId);
    }
}
