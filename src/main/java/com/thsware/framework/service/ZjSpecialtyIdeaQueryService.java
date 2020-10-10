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

import com.thsware.framework.domain.ZjSpecialtyIdea;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjSpecialtyIdeaRepository;
import com.thsware.framework.service.dto.ZjSpecialtyIdeaCriteria;

import com.thsware.framework.service.dto.ZjSpecialtyIdeaDTO;
import com.thsware.framework.service.mapper.ZjSpecialtyIdeaMapper;

/**
 * Service for executing complex queries for ZjSpecialtyIdea entities in the database.
 * The main input is a {@link ZjSpecialtyIdeaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjSpecialtyIdeaDTO} or a {@link Page} of {@link ZjSpecialtyIdeaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjSpecialtyIdeaQueryService extends QueryService<ZjSpecialtyIdea> {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyIdeaQueryService.class);

    private final ZjSpecialtyIdeaRepository zjSpecialtyIdeaRepository;

    private final ZjSpecialtyIdeaMapper zjSpecialtyIdeaMapper;

    public ZjSpecialtyIdeaQueryService(ZjSpecialtyIdeaRepository zjSpecialtyIdeaRepository, ZjSpecialtyIdeaMapper zjSpecialtyIdeaMapper) {
        this.zjSpecialtyIdeaRepository = zjSpecialtyIdeaRepository;
        this.zjSpecialtyIdeaMapper = zjSpecialtyIdeaMapper;
    }

    /**
     * Return a {@link List} of {@link ZjSpecialtyIdeaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjSpecialtyIdeaDTO> findByCriteria(ZjSpecialtyIdeaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjSpecialtyIdea> specification = createSpecification(criteria);
        return zjSpecialtyIdeaMapper.toDto(zjSpecialtyIdeaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjSpecialtyIdeaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjSpecialtyIdeaDTO> findByCriteria(ZjSpecialtyIdeaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjSpecialtyIdea> specification = createSpecification(criteria);
        return zjSpecialtyIdeaRepository.findAll(specification, page)
            .map(zjSpecialtyIdeaMapper::toDto);
    }

    /**
     * Function to convert ZjSpecialtyIdeaCriteria to a {@link Specification}
     */
    private Specification<ZjSpecialtyIdea> createSpecification(ZjSpecialtyIdeaCriteria criteria) {
        Specification<ZjSpecialtyIdea> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjSpecialtyIdea_.id));
            }
            if (criteria.getZjProjectId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjProjectId(), ZjSpecialtyIdea_.zjProjectId));
            }
            if (criteria.getZjSpecialtyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjSpecialtyId(), ZjSpecialtyIdea_.zjSpecialtyId));
            }
            if (criteria.getAuditType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditType(), ZjSpecialtyIdea_.auditType));
            }
            if (criteria.getAuditerId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditerId(), ZjSpecialtyIdea_.auditerId));
            }
            if (criteria.getAuditerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditerName(), ZjSpecialtyIdea_.auditerName));
            }
            if (criteria.getAuditResult() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditResult(), ZjSpecialtyIdea_.auditResult));
            }
            if (criteria.getAuditIdea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditIdea(), ZjSpecialtyIdea_.auditIdea));
            }
            if (criteria.getAuditDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuditDate(), ZjSpecialtyIdea_.auditDate));
            }
            if (criteria.getIsHistory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsHistory(), ZjSpecialtyIdea_.isHistory));
            }
            if (criteria.getValidEjsh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidEjsh(), ZjSpecialtyIdea_.validEjsh));
            }
        }
        return specification;
    }

}
