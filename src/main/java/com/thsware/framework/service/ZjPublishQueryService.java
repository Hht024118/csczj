package com.thsware.framework.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.thsware.framework.domain.ZjPublish;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjPublishRepository;
import com.thsware.framework.service.dto.ZjPublishCriteria;

import com.thsware.framework.service.dto.ZjPublishDTO;
import com.thsware.framework.service.mapper.ZjPublishMapper;

/**
 * Service for executing complex queries for ZjPublish entities in the database.
 * The main input is a {@link ZjPublishCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjPublishDTO} or a {@link Page} of {@link ZjPublishDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjPublishQueryService extends QueryService<ZjPublish> {

    private final Logger log = LoggerFactory.getLogger(ZjPublishQueryService.class);

    private final ZjPublishRepository zjPublishRepository;

    private final ZjPublishMapper zjPublishMapper;

    public ZjPublishQueryService(ZjPublishRepository zjPublishRepository, ZjPublishMapper zjPublishMapper) {
        this.zjPublishRepository = zjPublishRepository;
        this.zjPublishMapper = zjPublishMapper;
    }

    /**
     * Return a {@link List} of {@link ZjPublishDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZjPublishDTO> findByCriteria(ZjPublishCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjPublish> specification = createSpecification(criteria);
        return zjPublishMapper.toDto(zjPublishRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjPublishDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZjPublishDTO> findByCriteria(ZjPublishCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjPublish> specification = createSpecification(criteria);
        return zjPublishRepository.findAll(specification, page)
            .map(zjPublishMapper::toDto);
    }

    /**
     * Function to convert ZjPublishCriteria to a {@link Specification}
     */
    private Specification<ZjPublish> createSpecification(ZjPublishCriteria criteria) {
        Specification<ZjPublish> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjPublish_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), ZjPublish_.title));
            }
            if (criteria.getZjProjectId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjProjectId(), ZjPublish_.zjProjectId));
            }
            if (criteria.getFlowState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlowState(), ZjPublish_.flowState));
            }
            if (criteria.getPublishTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublishTime(), ZjPublish_.publishTime));
            }
        }
        return specification;
    }

}
