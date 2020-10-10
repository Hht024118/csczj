package com.thsware.framework.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thsware.framework.service.ZjMemberService;
import com.thsware.framework.web.rest.errors.BadRequestAlertException;
import com.thsware.framework.web.rest.util.HeaderUtil;
import com.thsware.framework.web.rest.util.PaginationUtil;
import com.thsware.framework.service.dto.ZjMemberDTO;
import com.thsware.framework.service.dto.ZjMemberCriteria;
import com.thsware.framework.service.ZjMemberQueryService;
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
 * REST controller for managing ZjMember.
 */
@RestController
@RequestMapping("/api")
public class ZjMemberResource {

    private final Logger log = LoggerFactory.getLogger(ZjMemberResource.class);

    private static final String ENTITY_NAME = "zjMember";

    private final ZjMemberService zjMemberService;

    private final ZjMemberQueryService zjMemberQueryService;

    public ZjMemberResource(ZjMemberService zjMemberService, ZjMemberQueryService zjMemberQueryService) {
        this.zjMemberService = zjMemberService;
        this.zjMemberQueryService = zjMemberQueryService;
    }

    /**
     * POST  /zj-members : Create a new zjMember.
     *
     * @param zjMemberDTO the zjMemberDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zjMemberDTO, or with status 400 (Bad Request) if the zjMember has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zj-members")
    @Timed
    public ResponseEntity<ZjMemberDTO> createZjMember(@Valid @RequestBody ZjMemberDTO zjMemberDTO) throws URISyntaxException {
        log.debug("REST request to save ZjMember : {}", zjMemberDTO);
        if (zjMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new zjMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZjMemberDTO result = zjMemberService.save(zjMemberDTO);
        return ResponseEntity.created(new URI("/api/zj-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zj-members : Updates an existing zjMember.
     *
     * @param zjMemberDTO the zjMemberDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zjMemberDTO,
     * or with status 400 (Bad Request) if the zjMemberDTO is not valid,
     * or with status 500 (Internal Server Error) if the zjMemberDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zj-members")
    @Timed
    public ResponseEntity<ZjMemberDTO> updateZjMember(@Valid @RequestBody ZjMemberDTO zjMemberDTO) throws URISyntaxException {
        log.debug("REST request to update ZjMember : {}", zjMemberDTO);
        if (zjMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZjMemberDTO result = zjMemberService.save(zjMemberDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zjMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zj-members : get all the zjMembers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zjMembers in body
     */
    @GetMapping("/zj-members")
    @Timed
    public ResponseEntity<List<ZjMemberDTO>> getAllZjMembers(ZjMemberCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZjMembers by criteria: {}", criteria);
        Page<ZjMemberDTO> page = zjMemberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zj-members");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /zj-members/:id : get the "id" zjMember.
     *
     * @param id the id of the zjMemberDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zjMemberDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zj-members/{id}")
    @Timed
    public ResponseEntity<ZjMemberDTO> getZjMember(@PathVariable String id) {
        log.debug("REST request to get ZjMember : {}", id);
        Optional<ZjMemberDTO> zjMemberDTO = zjMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zjMemberDTO);
    }

    /**
     * DELETE  /zj-members/:id : delete the "id" zjMember.
     *
     * @param id the id of the zjMemberDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zj-members/{id}")
    @Timed
    public ResponseEntity<Void> deleteZjMember(@PathVariable String id) {
        log.debug("REST request to delete ZjMember : {}", id);
        zjMemberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
