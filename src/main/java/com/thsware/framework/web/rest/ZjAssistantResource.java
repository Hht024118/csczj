package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjAssistantService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjAssistantDTO;
import com.thsware.framework.service.dto.ZjAssistantCriteria;
import com.thsware.framework.service.ZjAssistantQueryService;
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
 * REST controller for managing ZjAssistant.
 */
@RestController
@RequestMapping("/api")
public class ZjAssistantResource {

    private final Logger log = LoggerFactory.getLogger(ZjAssistantResource.class);

    private static final String ENTITY_NAME = "zjAssistant";

    private final ZjAssistantService zjAssistantService;

    private final ZjAssistantQueryService zjAssistantQueryService;

    public ZjAssistantResource(ZjAssistantService zjAssistantService, ZjAssistantQueryService zjAssistantQueryService) {
        this.zjAssistantService = zjAssistantService;
        this.zjAssistantQueryService = zjAssistantQueryService;
    }

    /**
     * POST  /zj-assistants : Create a new zjAssistant.
     *
     * @param zjAssistantDTO the zjAssistantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjAssistantDTO, or with status 400 (Bad Request) if the zjAssistant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-assistants")
    @Timed
    public ResponseEntity<ZjAssistantDTO> createZjAssistant(@Valid @RequestBody ZjAssistantDTO zjAssistantDTO) throws URISyntaxException {
        log.debug("REST request to save ZjAssistant : {}", zjAssistantDTO);
        if (zjAssistantDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjAssistant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjAssistantDTO result = zjAssistantService.save(zjAssistantDTO);
        return ResponseEntity.created(new URI("/api/zj-assistants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-assistants : Updates an existing zjAssistant.
     *
     * @param zjAssistantDTO the zjAssistantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjAssistantDTO,
     * or with status 400 (Bad Request) if the zjAssistantDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjAssistantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-assistants")
    @Timed
    public ResponseEntity<ZjAssistantDTO> updateZjAssistant(@Valid @RequestBody ZjAssistantDTO zjAssistantDTO) throws URISyntaxException {
        log.debug("REST request to update ZjAssistant : {}", zjAssistantDTO);
        if (zjAssistantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjAssistantDTO result = zjAssistantService.save(zjAssistantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjAssistantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-assistants : get all the zjAssistants.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjAssistants in body
     */
    @GetMapping("/zj-assistants")
    @Timed
    public ResponseEntity<List<ZjAssistantDTO>> getAllZjAssistants(ZjAssistantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjAssistants by criteria: {}", criteria);
        Page<ZjAssistantDTO> page = zjAssistantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-assistants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-assistants/:id : get the "id" zjAssistant.
     *
     * @param id the id of the zjAssistantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjAssistantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-assistants/{id}")
    @Timed
    public ResponseEntity<ZjAssistantDTO> getZjAssistant(@PathVariable String id) {
        log.debug("REST request to get ZjAssistant : {}", id);
        Optional<ZjAssistantDTO> zjAssistantDTO = zjAssistantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjAssistantDTO);
    }

    /**
     * DELETE  /zj-assistants/:id : delete the "id" zjAssistant.
     *
     * @param id the id of the zjAssistantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-assistants/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjAssistant(@PathVariable String id) {
        log.debug("REST request to delete ZjAssistant : {}", id);
        zjAssistantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
