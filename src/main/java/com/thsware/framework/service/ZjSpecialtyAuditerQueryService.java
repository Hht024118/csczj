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

import com.thsware.framework.domain.ZjSpecialtyAuditer;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjSpecialtyAuditerRepository;
import com.thsware.framework.service.dto.ZjSpecialtyAuditerCriteria;

import com.thsware.framework.service.dto.ZjSpecialtyAuditerDTO;
import com.thsware.framework.service.mapper.ZjSpecialtyAuditerMapper;

/**
 * Service for executing complex queries for ZjSpecialtyAuditer entities in the database.
 * The main input is a {@link ZjSpecialtyAuditerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjSpecialtyAuditerDTO} or a {@link Page} of {@link ZjSpecialtyAuditerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjSpecialtyAuditerQueryService extends QueryService<ZjSpecialtyAuditer> {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyAuditerQueryService.class);

    private final ZjSpecialtyAuditerRepository zjSpecialtyAuditerRepository;

    private final ZjSpecialtyAuditerMapper zjSpecialtyAuditerMapper;

    public ZjSpecialtyAuditerQueryService(ZjSpecialtyAuditerRepository zjSpecialtyAuditerRepository, ZjSpecialtyAuditerMapper zjSpecialtyAuditerMapper) {
        this.zjSpecialtyAuditerRepository = zjSpecialtyAuditerRepository;
        this.zjSpecialtyAuditerMapper = zjSpecialtyAuditerMapper;
    }

    /**
     * Return a {@link List} of {@link ZjSpecialtyAuditerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjSpecialtyAuditerDTO> findByCriteria(ZjSpecialtyAuditerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjSpecialtyAuditer> specification = createSpecification(criteria);
        return zjSpecialtyAuditerMapper.toDto(zjSpecialtyAuditerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjSpecialtyAuditerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjSpecialtyAuditerDTO> findByCriteria(ZjSpecialtyAuditerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjSpecialtyAuditer> specification = createSpecification(criteria);
        return zjSpecialtyAuditerRepository.findAll(specification, page)
            .map(zjSpecialtyAuditerMapper::toDto);
    }

    /**
     * Function to convert ZjSpecialtyAuditerCriteria to a {@link Specification}
     */
    private Specification<ZjSpecialtyAuditer> createSpecification(ZjSpecialtyAuditerCriteria criteria) {
        Specification<ZjSpecialtyAuditer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjSpecialtyAuditer_.id));
            }
            if (criteria.getZjProjectId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjProjectId(), ZjSpecialtyAuditer_.zjProjectId));
            }
            if (criteria.getZjSpecialtyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjSpecialtyId(), ZjSpecialtyAuditer_.zjSpecialtyId));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ZjSpecialtyAuditer_.type));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonId(), ZjSpecialtyAuditer_.personId));
            }
            if (criteria.getPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonName(), ZjSpecialtyAuditer_.personName));
            }
        }
        return specification;
    }

}
