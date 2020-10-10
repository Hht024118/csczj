package com.thsware.framework.service;

import java.util.List;

import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.thsware.framework.domain.ZjActors;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjActorsRepository;
import com.thsware.framework.service.dto.ZjActorsCriteria;

import com.thsware.framework.service.dto.ZjActorsDTO;
import com.thsware.framework.service.mapper.ZjActorsMapper;

/**
 * Service for executing complex queries for ZjActors entities in the database.
 * The main input is a {@link ZjActorsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjActorsDTO} or a {@link Page} of {@link ZjActorsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjActorsQueryService extends QueryService<ZjActors> {

    private final Logger log = LoggerFactory.getLogger(ZjActorsQueryService.class);

    private final ZjActorsRepository zjActorsRepository;

    private final ZjActorsMapper zjActorsMapper;

    public ZjActorsQueryService(ZjActorsRepository zjActorsRepository, ZjActorsMapper zjActorsMapper) {
        this.zjActorsRepository = zjActorsRepository;
        this.zjActorsMapper = zjActorsMapper;
    }

    /**
     * Return a {@link List} of {@link ZjActorsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjActorsDTO> findByCriteria(ZjActorsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjActors> specification = createSpecification(criteria);
        return zjActorsMapper.toDto(zjActorsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjActorsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjActorsDTO> findByCriteria(ZjActorsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjActors> specification = createSpecification(criteria);
        return zjActorsRepository.findAll(specification, page)
            .map(zjActorsMapper::toDto);
    }

    /**
     * Function to convert ZjActorsCriteria to a {@link Specification}
     */
    private Specification<ZjActors> createSpecification(ZjActorsCriteria criteria) {
        Specification<ZjActors> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjActors_.id));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectId(), ZjActors_.projectId));
            }
            if (criteria.getStep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStep(), ZjActors_.step));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonId(), ZjActors_.personId));
            }
            if (criteria.getPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonName(), ZjActors_.personName));
            }
        }
        return specification;
    }

}
