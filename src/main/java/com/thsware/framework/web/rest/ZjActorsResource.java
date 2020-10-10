package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjActorsService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjActorsDTO;
import com.thsware.framework.service.dto.ZjActorsCriteria;
import com.thsware.framework.service.ZjActorsQueryService;
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
 * REST controller for managing ZjActors.
 */
@RestController
@RequestMapping("/api")
public class ZjActorsResource {

    private final Logger log = LoggerFactory.getLogger(ZjActorsResource.class);

    private static final String ENTITY_NAME = "zjActors";

    private final ZjActorsService zjActorsService;

    private final ZjActorsQueryService zjActorsQueryService;

    public ZjActorsResource(ZjActorsService zjActorsService, ZjActorsQueryService zjActorsQueryService) {
        this.zjActorsService = zjActorsService;
        this.zjActorsQueryService = zjActorsQueryService;
    }

    /**
     * POST  /zj-actors : Create a new zjActors.
     *
     * @param zjActorsDTO the zjActorsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjActorsDTO, or with status 400 (Bad Request) if the zjActors has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-actors")
    @Timed
    public ResponseEntity<ZjActorsDTO> createZjActors(@Valid @RequestBody ZjActorsDTO zjActorsDTO) throws URISyntaxException {
        log.debug("REST request to save ZjActors : {}", zjActorsDTO);
        if (zjActorsDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjActors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjActorsDTO result = zjActorsService.save(zjActorsDTO);
        return ResponseEntity.created(new URI("/api/zj-actors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-actors : Updates an existing zjActors.
     *
     * @param zjActorsDTO the zjActorsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjActorsDTO,
     * or with status 400 (Bad Request) if the zjActorsDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjActorsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-actors")
    @Timed
    public ResponseEntity<ZjActorsDTO> updateZjActors(@Valid @RequestBody ZjActorsDTO zjActorsDTO) throws URISyntaxException {
        log.debug("REST request to update ZjActors : {}", zjActorsDTO);
        if (zjActorsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjActorsDTO result = zjActorsService.save(zjActorsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjActorsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-actors : get all the zjActors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjActors in body
     */
    @GetMapping("/zj-actors")
    @Timed
    public ResponseEntity<List<ZjActorsDTO>> getAllZjActors(ZjActorsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjActors by criteria: {}", criteria);
        Page<ZjActorsDTO> page = zjActorsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-actors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-actors/:id : get the "id" zjActors.
     *
     * @param id the id of the zjActorsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjActorsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-actors/{id}")
    @Timed
    public ResponseEntity<ZjActorsDTO> getZjActors(@PathVariable String id) {
        log.debug("REST request to get ZjActors : {}", id);
        Optional<ZjActorsDTO> zjActorsDTO = zjActorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjActorsDTO);
    }

    /**
     * DELETE  /zj-actors/:id : delete the "id" zjActors.
     *
     * @param id the id of the zjActorsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-actors/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjActors(@PathVariable String id) {
        log.debug("REST request to delete ZjActors : {}", id);
        zjActorsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
