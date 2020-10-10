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

import com.thsware.framework.domain.ZjProjectArchive;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjProjectArchiveRepository;
import com.thsware.framework.service.dto.ZjProjectArchiveCriteria;

import com.thsware.framework.service.dto.ZjProjectArchiveDTO;
import com.thsware.framework.service.mapper.ZjProjectArchiveMapper;

/**
 * Service for executing complex queries for ZjProjectArchive entities in the database.
 * The main input is a {@link ZjProjectArchiveCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjProjectArchiveDTO} or a {@link Page} of {@link ZjProjectArchiveDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjProjectArchiveQueryService extends QueryService<ZjProjectArchive> {

    private final Logger log = LoggerFactory.getLogger(ZjProjectArchiveQueryService.class);

    private final ZjProjectArchiveRepository zjProjectArchiveRepository;

    private final ZjProjectArchiveMapper zjProjectArchiveMapper;

    public ZjProjectArchiveQueryService(ZjProjectArchiveRepository zjProjectArchiveRepository, ZjProjectArchiveMapper zjProjectArchiveMapper) {
        this.zjProjectArchiveRepository = zjProjectArchiveRepository;
        this.zjProjectArchiveMapper = zjProjectArchiveMapper;
    }

    /**
     * Return a {@link List} of {@link ZjProjectArchiveDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjProjectArchiveDTO> findByCriteria(ZjProjectArchiveCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjProjectArchive> specification = createSpecification(criteria);
        return zjProjectArchiveMapper.toDto(zjProjectArchiveRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjProjectArchiveDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjProjectArchiveDTO> findByCriteria(ZjProjectArchiveCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjProjectArchive> specification = createSpecification(criteria);
        return zjProjectArchiveRepository.findAll(specification, page)
            .map(zjProjectArchiveMapper::toDto);
    }

    /**
     * Function to convert ZjProjectArchiveCriteria to a {@link Specification}
     */
    private Specification<ZjProjectArchive> createSpecification(ZjProjectArchiveCriteria criteria) {
        Specification<ZjProjectArchive> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjProjectArchive_.id));
            }
            if (criteria.getZjProjectId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getZjProjectId(), ZjProjectArchive_.zjProject, ZjProject_.id));
            }
            if (criteria.getArchiveNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArchiveNo(), ZjProjectArchive_.archiveNo));
            }
            if (criteria.getIsComplete() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsComplete(), ZjProjectArchive_.isComplete));
            }
            if (criteria.getArchivedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArchivedBy(), ZjProjectArchive_.archivedBy));
            }
            if (criteria.getArchiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArchiveDate(), ZjProjectArchive_.archiveDate));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), ZjProjectArchive_.remark));
            }if (criteria.getPaperArchiveBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaperArchiveBy(), ZjProjectArchive_.paperArchiveBy));
            }if (criteria.getPaperArchiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaperArchiveDate(), ZjProjectArchive_.paperArchiveDate));
            }
        }
        return specification;
    }

}
