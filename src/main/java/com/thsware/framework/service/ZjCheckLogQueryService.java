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

import com.thsware.framework.domain.ZjCheckLog;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjCheckLogRepository;
import com.thsware.framework.service.dto.ZjCheckLogCriteria;

import com.thsware.framework.service.dto.ZjCheckLogDTO;
import com.thsware.framework.service.mapper.ZjCheckLogMapper;

/**
 * Service for executing complex queries for ZjCheckLog entities in the database.
 * The main input is a {@link ZjCheckLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjCheckLogDTO} or a {@link Page} of {@link ZjCheckLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjCheckLogQueryService extends QueryService<ZjCheckLog> {

    private final Logger log = LoggerFactory.getLogger(ZjCheckLogQueryService.class);

    private final ZjCheckLogRepository zjCheckLogRepository;

    private final ZjCheckLogMapper zjCheckLogMapper;

    public ZjCheckLogQueryService(ZjCheckLogRepository zjCheckLogRepository, ZjCheckLogMapper zjCheckLogMapper) {
        this.zjCheckLogRepository = zjCheckLogRepository;
        this.zjCheckLogMapper = zjCheckLogMapper;
    }

    /**
     * Return a {@link List} of {@link ZjCheckLogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjCheckLogDTO> findByCriteria(ZjCheckLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjCheckLog> specification = createSpecification(criteria);
        return zjCheckLogMapper.toDto(zjCheckLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjCheckLogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjCheckLogDTO> findByCriteria(ZjCheckLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjCheckLog> specification = createSpecification(criteria);
        return zjCheckLogRepository.findAll(specification, page)
            .map(zjCheckLogMapper::toDto);
    }

    /**
     * Function to convert ZjCheckLogCriteria to a {@link Specification}
     */
    private Specification<ZjCheckLog> createSpecification(ZjCheckLogCriteria criteria) {
        Specification<ZjCheckLog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjCheckLog_.id));
            }
            if (criteria.getZjSpecialtyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjSpecialtyId(), ZjCheckLog_.zjSpecialtyId));
            }
            if (criteria.getChekcStep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChekcStep(), ZjCheckLog_.chekcStep));
            }
            if (criteria.getOrderNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderNum(), ZjCheckLog_.orderNum));
            }
            if (criteria.getCheckItem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckItem(), ZjCheckLog_.checkItem));
            }
            if (criteria.getIsPass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsPass(), ZjCheckLog_.isPass));
            }
            if (criteria.getCheckAudit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckAudit(), ZjCheckLog_.checkAudit));
            }
            if (criteria.getChecker() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChecker(), ZjCheckLog_.checker));
            }
            if (criteria.getCheckTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCheckTime(), ZjCheckLog_.checkTime));
            }
        }
        return specification;
    }

}
