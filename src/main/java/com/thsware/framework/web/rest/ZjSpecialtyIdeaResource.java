package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjSpecialtyIdeaService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjSpecialtyIdeaDTO;
import com.thsware.framework.service.dto.ZjSpecialtyIdeaCriteria;
import com.thsware.framework.service.ZjSpecialtyIdeaQueryService;
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
 * REST controller for managing ZjSpecialtyIdea.
 */
@RestController
@RequestMapping("/api")
public class ZjSpecialtyIdeaResource {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyIdeaResource.class);

    private static final String ENTITY_NAME = "zjSpecialtyIdea";

    private final ZjSpecialtyIdeaService zjSpecialtyIdeaService;

    private final ZjSpecialtyIdeaQueryService zjSpecialtyIdeaQueryService;

    public ZjSpecialtyIdeaResource(ZjSpecialtyIdeaService zjSpecialtyIdeaService, ZjSpecialtyIdeaQueryService zjSpecialtyIdeaQueryService) {
        this.zjSpecialtyIdeaService = zjSpecialtyIdeaService;
        this.zjSpecialtyIdeaQueryService = zjSpecialtyIdeaQueryService;
    }

    /**
     * POST  /zj-specialty-ideas : Create a new zjSpecialtyIdea.
     *
     * @param zjSpecialtyIdeaDTO the zjSpecialtyIdeaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjSpecialtyIdeaDTO, or with status 400 (Bad Request) if the zjSpecialtyIdea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-specialty-ideas")
    @Timed
    public ResponseEntity<ZjSpecialtyIdeaDTO> createZjSpecialtyIdea(@Valid @RequestBody ZjSpecialtyIdeaDTO zjSpecialtyIdeaDTO) throws URISyntaxException {
        log.debug("REST request to save ZjSpecialtyIdea : {}", zjSpecialtyIdeaDTO);
        if (zjSpecialtyIdeaDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjSpecialtyIdea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjSpecialtyIdeaDTO result = zjSpecialtyIdeaService.save(zjSpecialtyIdeaDTO);
        return ResponseEntity.created(new URI("/api/zj-specialty-ideas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-specialty-ideas : Updates an existing zjSpecialtyIdea.
     *
     * @param zjSpecialtyIdeaDTO the zjSpecialtyIdeaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjSpecialtyIdeaDTO,
     * or with status 400 (Bad Request) if the zjSpecialtyIdeaDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjSpecialtyIdeaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-specialty-ideas")
    @Timed
    public ResponseEntity<ZjSpecialtyIdeaDTO> updateZjSpecialtyIdea(@Valid @RequestBody ZjSpecialtyIdeaDTO zjSpecialtyIdeaDTO) throws URISyntaxException {
        log.debug("REST request to update ZjSpecialtyIdea : {}", zjSpecialtyIdeaDTO);
        ZjSpecialtyIdeaDTO result = zjSpecialtyIdeaService.save(zjSpecialtyIdeaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjSpecialtyIdeaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-specialty-ideas : get all the zjSpecialtyIdeas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjSpecialtyIdeas in body
     */
    @GetMapping("/zj-specialty-ideas")
    @Timed
    public ResponseEntity<List<ZjSpecialtyIdeaDTO>> getAllZjSpecialtyIdeas(ZjSpecialtyIdeaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjSpecialtyIdeas by criteria: {}", criteria);
        Page<ZjSpecialtyIdeaDTO> page = zjSpecialtyIdeaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-specialty-ideas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-specialty-ideas/:id : get the "id" zjSpecialtyIdea.
     *
     * @param id the id of the zjSpecialtyIdeaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjSpecialtyIdeaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-specialty-ideas/{id}")
    @Timed
    public ResponseEntity<ZjSpecialtyIdeaDTO> getZjSpecialtyIdea(@PathVariable String id) {
        log.debug("REST request to get ZjSpecialtyIdea : {}", id);
        Optional<ZjSpecialtyIdeaDTO> zjSpecialtyIdeaDTO = zjSpecialtyIdeaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjSpecialtyIdeaDTO);
    }

    /**
     * DELETE  /zj-specialty-ideas/:id : delete the "id" zjSpecialtyIdea.
     *
     * @param id the id of the zjSpecialtyIdeaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-specialty-ideas/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjSpecialtyIdea(@PathVariable String id) {
        log.debug("REST request to delete ZjSpecialtyIdea : {}", id);
        zjSpecialtyIdeaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
