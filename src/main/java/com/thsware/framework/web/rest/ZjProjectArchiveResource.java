package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjProjectArchiveService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjProjectArchiveDTO;
import com.thsware.framework.service.dto.ZjProjectArchiveCriteria;
import com.thsware.framework.service.ZjProjectArchiveQueryService;
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
 * REST controller for managing ZjProjectArchive.
 */
@RestController
@RequestMapping("/api")
public class ZjProjectArchiveResource {

    private final Logger log = LoggerFactory.getLogger(ZjProjectArchiveResource.class);

    private static final String ENTITY_NAME = "zjProjectArchive";

    private final ZjProjectArchiveService zjProjectArchiveService;

    private final ZjProjectArchiveQueryService zjProjectArchiveQueryService;

    public ZjProjectArchiveResource(ZjProjectArchiveService zjProjectArchiveService, ZjProjectArchiveQueryService zjProjectArchiveQueryService) {
        this.zjProjectArchiveService = zjProjectArchiveService;
        this.zjProjectArchiveQueryService = zjProjectArchiveQueryService;
    }

    /**
     * POST  /zj-project-archives : Create a new zjProjectArchive.
     *
     * @param zjProjectArchiveDTO the zjProjectArchiveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjProjectArchiveDTO, or with status 400 (Bad Request) if the zjProjectArchive has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-project-archives")
    @Timed
    public ResponseEntity<ZjProjectArchiveDTO> createZjProjectArchive(@Valid @RequestBody ZjProjectArchiveDTO zjProjectArchiveDTO) throws URISyntaxException {
        log.debug("REST request to save ZjProjectArchive : {}", zjProjectArchiveDTO);
        if (zjProjectArchiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjProjectArchive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjProjectArchiveDTO result = zjProjectArchiveService.save(zjProjectArchiveDTO);
        return ResponseEntity.created(new URI("/api/zj-project-archives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-project-archives : Updates an existing zjProjectArchive.
     *
     * @param zjProjectArchiveDTO the zjProjectArchiveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjProjectArchiveDTO,
     * or with status 400 (Bad Request) if the zjProjectArchiveDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjProjectArchiveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-project-archives")
    @Timed
    public ResponseEntity<ZjProjectArchiveDTO> updateZjProjectArchive(@Valid @RequestBody ZjProjectArchiveDTO zjProjectArchiveDTO) throws URISyntaxException {
        log.debug("REST request to update ZjProjectArchive : {}", zjProjectArchiveDTO);
        if (zjProjectArchiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjProjectArchiveDTO result = zjProjectArchiveService.save(zjProjectArchiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjProjectArchiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-project-archives : get all the zjProjectArchives.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjProjectArchives in body
     */
    @GetMapping("/zj-project-archives")
    @Timed
    public ResponseEntity<List<ZjProjectArchiveDTO>> getAllZjProjectArchives(ZjProjectArchiveCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjProjectArchives by criteria: {}", criteria);
        Page<ZjProjectArchiveDTO> page = zjProjectArchiveQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-project-archives");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-project-archives/:id : get the "id" zjProjectArchive.
     *
     * @param id the id of the zjProjectArchiveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjProjectArchiveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-project-archives/{id}")
    @Timed
    public ResponseEntity<ZjProjectArchiveDTO> getZjProjectArchive(@PathVariable String id) {
        log.debug("REST request to get ZjProjectArchive : {}", id);
        Optional<ZjProjectArchiveDTO> zjProjectArchiveDTO = zjProjectArchiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjProjectArchiveDTO);
    }

    /**
     * DELETE  /zj-project-archives/:id : delete the "id" zjProjectArchive.
     *
     * @param id the id of the zjProjectArchiveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-project-archives/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjProjectArchive(@PathVariable String id) {
        log.debug("REST request to delete ZjProjectArchive : {}", id);
        zjProjectArchiveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
