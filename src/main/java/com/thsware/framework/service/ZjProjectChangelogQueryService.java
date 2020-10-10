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

import com.thsware.framework.domain.ZjProjectChangelog;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjProjectChangelogRepository;
import com.thsware.framework.service.dto.ZjProjectChangelogCriteria;

import com.thsware.framework.service.dto.ZjProjectChangelogDTO;
import com.thsware.framework.service.mapper.ZjProjectChangelogMapper;

/**
 * Service for executing complex queries for ZjProjectChangelog entities in the database.
 * The main input is a {@link ZjProjectChangelogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjProjectChangelogDTO} or a {@link Page} of {@link ZjProjectChangelogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjProjectChangelogQueryService extends QueryService<ZjProjectChangelog> {

    private final Logger log = LoggerFactory.getLogger(ZjProjectChangelogQueryService.class);

    private final ZjProjectChangelogRepository zjProjectChangelogRepository;

    private final ZjProjectChangelogMapper zjProjectChangelogMapper;

    public ZjProjectChangelogQueryService(ZjProjectChangelogRepository zjProjectChangelogRepository, ZjProjectChangelogMapper zjProjectChangelogMapper) {
        this.zjProjectChangelogRepository = zjProjectChangelogRepository;
        this.zjProjectChangelogMapper = zjProjectChangelogMapper;
    }

    /**
     * Return a {@link List} of {@link ZjProjectChangelogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZjProjectChangelogDTO> findByCriteria(ZjProjectChangelogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjProjectChangelog> specification = createSpecification(criteria);
        return zjProjectChangelogMapper.toDto(zjProjectChangelogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjProjectChangelogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZjProjectChangelogDTO> findByCriteria(ZjProjectChangelogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjProjectChangelog> specification = createSpecification(criteria);
        return zjProjectChangelogRepository.findAll(specification, page)
            .map(zjProjectChangelogMapper::toDto);
    }

    /**
     * Function to convert ZjProjectChangelogCriteria to a {@link Specification}
     */
    private Specification<ZjProjectChangelog> createSpecification(ZjProjectChangelogCriteria criteria) {
        Specification<ZjProjectChangelog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjProjectChangelog_.id));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectId(), ZjProjectChangelog_.projectId));
            }
            if (criteria.getSpecialtyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecialtyId(), ZjProjectChangelog_.specialtyId));
            }
            if (criteria.getChangeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChangeType(), ZjProjectChangelog_.changeType));
            }
            if (criteria.getChangeVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChangeVersion(), ZjProjectChangelog_.changeVersion));
            }
            if (criteria.getChangeReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChangeReason(), ZjProjectChangelog_.changeReason));
            }
            if (criteria.getChangeTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChangeTime(), ZjProjectChangelog_.changeTime));
            }
            if (criteria.getOldFieldId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOldFieldId(), ZjProjectChangelog_.oldFieldId));
            }
            if (criteria.getOldFieldText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOldFieldText(), ZjProjectChangelog_.oldFieldText));
            }
            if (criteria.getNewFieldId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewFieldId(), ZjProjectChangelog_.newFieldId));
            }
            if (criteria.getNewFieldText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewFieldText(), ZjProjectChangelog_.newFieldText));
            }
            if (criteria.getCurrentBusinessState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentBusinessState(), ZjProjectChangelog_.currentBusinessState));
            }
            if (criteria.getFlowState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlowState(), ZjProjectChangelog_.flowState));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), ZjProjectChangelog_.remark));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ZjProjectChangelog_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ZjProjectChangelog_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ZjProjectChangelog_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ZjProjectChangelog_.lastModifiedDate));
            }
            if (criteria.getMultiTenancyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMultiTenancyId(), ZjProjectChangelog_.multiTenancyId));
            }
        }
        return specification;
    }

}
