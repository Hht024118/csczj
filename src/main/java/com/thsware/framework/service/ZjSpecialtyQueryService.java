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

import com.thsware.framework.domain.ZjSpecialty;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjSpecialtyRepository;
import com.thsware.framework.service.dto.ZjSpecialtyCriteria;

import com.thsware.framework.service.dto.ZjSpecialtyDTO;
import com.thsware.framework.service.mapper.ZjSpecialtyMapper;

/**
 * Service for executing complex queries for ZjSpecialty entities in the database.
 * The main input is a {@link ZjSpecialtyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjSpecialtyDTO} or a {@link Page} of {@link ZjSpecialtyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjSpecialtyQueryService extends QueryService<ZjSpecialty> {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyQueryService.class);

    private final ZjSpecialtyRepository zjSpecialtyRepository;

    private final ZjSpecialtyMapper zjSpecialtyMapper;

    public ZjSpecialtyQueryService(ZjSpecialtyRepository zjSpecialtyRepository, ZjSpecialtyMapper zjSpecialtyMapper) {
        this.zjSpecialtyRepository = zjSpecialtyRepository;
        this.zjSpecialtyMapper = zjSpecialtyMapper;
    }

    /**
     * Return a {@link List} of {@link ZjSpecialtyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjSpecialtyDTO> findByCriteria(ZjSpecialtyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjSpecialty> specification = createSpecification(criteria);
        return zjSpecialtyMapper.toDto(zjSpecialtyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjSpecialtyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjSpecialtyDTO> findByCriteria(ZjSpecialtyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjSpecialty> specification = createSpecification(criteria);
        return zjSpecialtyRepository.findAll(specification, page)
            .map(zjSpecialtyMapper::toDto);
    }

    /**
     * Function to convert ZjSpecialtyCriteria to a {@link Specification}
     */
    private Specification<ZjSpecialty> createSpecification(ZjSpecialtyCriteria criteria) {
        Specification<ZjSpecialty> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjSpecialty_.id));
            }
            if (criteria.getZjProjectId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjProjectId(), ZjSpecialty_.zjProjectId));
            }
            if (criteria.getZjPublishId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjPublishId(), ZjSpecialty_.zjPublishId));
            }
            if (criteria.getEngineeringName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEngineeringName(), ZjSpecialty_.engineeringName));
            }
            if (criteria.getSpecialtyType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecialtyType(), ZjSpecialty_.specialtyType));
            }
            if (criteria.getEstablishmentPersonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstablishmentPersonId(), ZjSpecialty_.establishmentPersonId));
            }
            if (criteria.getEstablishmentPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstablishmentPersonName(), ZjSpecialty_.establishmentPersonName));
            }
            if (criteria.getWorkloadRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWorkloadRate(), ZjSpecialty_.workloadRate));
            }
            if (criteria.getEstablishmentMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstablishmentMoney(), ZjSpecialty_.establishmentMoney));
            }
            if (criteria.getApprovalMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovalMoney(), ZjSpecialty_.approvalMoney));
            }
            if (criteria.getCreaseRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreaseRate(), ZjSpecialty_.creaseRate));
            }
            if (criteria.getSubmitMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitMoney(), ZjSpecialty_.submitMoney));
            }
            if (criteria.getBeginDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBeginDate(), ZjSpecialty_.beginDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ZjSpecialty_.endDate));
            }
            if(criteria.getActualFinishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualFinishDate(), ZjSpecialty_.actualFinishDate));
            }
            if (criteria.getPlanDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlanDuration(), ZjSpecialty_.planDuration));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), ZjSpecialty_.state));
            }
            if (criteria.getReject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReject(), ZjSpecialty_.reject));
            }
            if (criteria.getFirstTrialMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstTrialMoney(), ZjSpecialty_.firstTrialMoney));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ZjSpecialty_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ZjSpecialty_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ZjSpecialty_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ZjSpecialty_.lastModifiedDate));
            }
        }
        return specification;
    }

}
