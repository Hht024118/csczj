package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjCheckLogService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjCheckLogDTO;
import com.thsware.framework.service.dto.ZjCheckLogCriteria;
import com.thsware.framework.service.ZjCheckLogQueryService;
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
 * REST controller for managing ZjCheckLog.
 */
@RestController
@RequestMapping("/api")
public class ZjCheckLogResource {

    private final Logger log = LoggerFactory.getLogger(ZjCheckLogResource.class);

    private static final String ENTITY_NAME = "zjCheckLog";

    private final ZjCheckLogService zjCheckLogService;

    private final ZjCheckLogQueryService zjCheckLogQueryService;

    public ZjCheckLogResource(ZjCheckLogService zjCheckLogService, ZjCheckLogQueryService zjCheckLogQueryService) {
        this.zjCheckLogService = zjCheckLogService;
        this.zjCheckLogQueryService = zjCheckLogQueryService;
    }

    /**
     * POST  /zj-check-logs : Create a new zjCheckLog.
     *
     * @param zjCheckLogDTO the zjCheckLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjCheckLogDTO, or with status 400 (Bad Request) if the zjCheckLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-check-logs")
    @Timed
    public ResponseEntity<ZjCheckLogDTO> createZjCheckLog(@Valid @RequestBody ZjCheckLogDTO zjCheckLogDTO) throws URISyntaxException {
        log.debug("REST request to save ZjCheckLog : {}", zjCheckLogDTO);
        if (zjCheckLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjCheckLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjCheckLogDTO result = zjCheckLogService.save(zjCheckLogDTO);
        return ResponseEntity.created(new URI("/api/zj-check-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-check-logs : Updates an existing zjCheckLog.
     *
     * @param zjCheckLogDTO the zjCheckLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjCheckLogDTO,
     * or with status 400 (Bad Request) if the zjCheckLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjCheckLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-check-logs")
    @Timed
    public ResponseEntity<ZjCheckLogDTO> updateZjCheckLog(@Valid @RequestBody ZjCheckLogDTO zjCheckLogDTO) throws URISyntaxException {
        log.debug("REST request to update ZjCheckLog : {}", zjCheckLogDTO);
        if (zjCheckLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjCheckLogDTO result = zjCheckLogService.save(zjCheckLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjCheckLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-check-logs : get all the zjCheckLogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjCheckLogs in body
     */
    @GetMapping("/zj-check-logs")
    @Timed
    public ResponseEntity<List<ZjCheckLogDTO>> getAllZjCheckLogs(ZjCheckLogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjCheckLogs by criteria: {}", criteria);
        Page<ZjCheckLogDTO> page = zjCheckLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-check-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-check-logs/:id : get the "id" zjCheckLog.
     *
     * @param id the id of the zjCheckLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjCheckLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-check-logs/{id}")
    @Timed
    public ResponseEntity<ZjCheckLogDTO> getZjCheckLog(@PathVariable String id) {
        log.debug("REST request to get ZjCheckLog : {}", id);
        Optional<ZjCheckLogDTO> zjCheckLogDTO = zjCheckLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjCheckLogDTO);
    }

    /**
     * DELETE  /zj-check-logs/:id : delete the "id" zjCheckLog.
     *
     * @param id the id of the zjCheckLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-check-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjCheckLog(@PathVariable String id) {
        log.debug("REST request to delete ZjCheckLog : {}", id);
        zjCheckLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /zj-check-logs/list : Create a new zjCheckLog.
     *
     * @param zjCheckLogDTOs the zjCheckLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjCheckLogDTO, or with status 400 (Bad Request) if the zjCheckLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-check-logs/createZjCheckLogList")
    @Timed
    public ResponseEntity<ZjCheckLogDTO[]> createZjCheckLogList(@Valid @RequestBody ZjCheckLogDTO[] zjCheckLogDTOs) throws URISyntaxException {
        log.debug("REST request to save ZjCheckLog : {}", zjCheckLogDTOs);
        for (ZjCheckLogDTO zjCheckLogDTO : zjCheckLogDTOs) {
            zjCheckLogDTO = zjCheckLogService.save(zjCheckLogDTO);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ""))
            .body(zjCheckLogDTOs);
    }
}
