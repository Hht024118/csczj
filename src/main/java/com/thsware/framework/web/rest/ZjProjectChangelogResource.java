package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjProjectChangelogService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjProjectChangelogDTO;
import com.thsware.framework.service.dto.ZjProjectChangelogCriteria;
import com.thsware.framework.service.ZjProjectChangelogQueryService;
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
 * REST controller for managing ZjProjectChangelog.
 */
@RestController
@RequestMapping("/api")
public class ZjProjectChangelogResource {

    private final Logger log = LoggerFactory.getLogger(ZjProjectChangelogResource.class);

    private static final String ENTITY_NAME = "zjProjectChangelog";

    private final ZjProjectChangelogService zjProjectChangelogService;

    private final ZjProjectChangelogQueryService zjProjectChangelogQueryService;

    public ZjProjectChangelogResource(ZjProjectChangelogService zjProjectChangelogService, ZjProjectChangelogQueryService zjProjectChangelogQueryService) {
        this.zjProjectChangelogService = zjProjectChangelogService;
        this.zjProjectChangelogQueryService = zjProjectChangelogQueryService;
    }

    /**
     * POST  /zj-project-changelogs : Create a new zjProjectChangelog.
     *
     * @param zjProjectChangelogDTO the zjProjectChangelogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjProjectChangelogDTO, or with status 400 (Bad Request) if the zjProjectChangelog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-project-changelogs")
    @Timed
    public ResponseEntity<ZjProjectChangelogDTO> createZjProjectChangelog(@Valid @RequestBody ZjProjectChangelogDTO zjProjectChangelogDTO) throws URISyntaxException {
        log.debug("REST request to save ZjProjectChangelog : {}", zjProjectChangelogDTO);
        if (zjProjectChangelogDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjProjectChangelog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjProjectChangelogDTO result = zjProjectChangelogService.save(zjProjectChangelogDTO);
        return ResponseEntity.created(new URI("/api/zj-project-changelogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-project-changelogs : Updates an existing zjProjectChangelog.
     *
     * @param zjProjectChangelogDTO the zjProjectChangelogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjProjectChangelogDTO,
     * or with status 400 (Bad Request) if the zjProjectChangelogDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjProjectChangelogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-project-changelogs")
    @Timed
    public ResponseEntity<ZjProjectChangelogDTO> updateZjProjectChangelog(@Valid @RequestBody ZjProjectChangelogDTO zjProjectChangelogDTO) throws URISyntaxException {
        log.debug("REST request to update ZjProjectChangelog : {}", zjProjectChangelogDTO);
        if (zjProjectChangelogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjProjectChangelogDTO result = zjProjectChangelogService.save(zjProjectChangelogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjProjectChangelogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-project-changelogs : get all the zjProjectChangelogs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjProjectChangelogs in body
     */
    @GetMapping("/zj-project-changelogs")
    @Timed
    public ResponseEntity<List<ZjProjectChangelogDTO>> getAllZjProjectChangelogs(ZjProjectChangelogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjProjectChangelogs by criteria: {}", criteria);
        Page<ZjProjectChangelogDTO> page = zjProjectChangelogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-project-changelogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-project-changelogs/:id : get the "id" zjProjectChangelog.
     *
     * @param id the id of the zjProjectChangelogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjProjectChangelogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-project-changelogs/{id}")
    @Timed
    public ResponseEntity<ZjProjectChangelogDTO> getZjProjectChangelog(@PathVariable String id) {
        log.debug("REST request to get ZjProjectChangelog : {}", id);
        Optional<ZjProjectChangelogDTO> zjProjectChangelogDTO = zjProjectChangelogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjProjectChangelogDTO);
    }

    /**
     * DELETE  /zj-project-changelogs/:id : delete the "id" zjProjectChangelog.
     *
     * @param id the id of the zjProjectChangelogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-project-changelogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjProjectChangelog(@PathVariable String id) {
        log.debug("REST request to delete ZjProjectChangelog : {}", id);
        zjProjectChangelogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
