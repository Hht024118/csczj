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

import com.thsware.framework.domain.ZjAssistant;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjAssistantRepository;
import com.thsware.framework.service.dto.ZjAssistantCriteria;

import com.thsware.framework.service.dto.ZjAssistantDTO;
import com.thsware.framework.service.mapper.ZjAssistantMapper;

/**
 * Service for executing complex queries for ZjAssistant entities in the database.
 * The main input is a {@link ZjAssistantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjAssistantDTO} or a {@link Page} of {@link ZjAssistantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjAssistantQueryService extends QueryService<ZjAssistant> {

    private final Logger log = LoggerFactory.getLogger(ZjAssistantQueryService.class);

    private final ZjAssistantRepository zjAssistantRepository;

    private final ZjAssistantMapper zjAssistantMapper;

    public ZjAssistantQueryService(ZjAssistantRepository zjAssistantRepository, ZjAssistantMapper zjAssistantMapper) {
        this.zjAssistantRepository = zjAssistantRepository;
        this.zjAssistantMapper = zjAssistantMapper;
    }

    /**
     * Return a {@link List} of {@link ZjAssistantDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZjAssistantDTO> findByCriteria(ZjAssistantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjAssistant> specification = createSpecification(criteria);
        return zjAssistantMapper.toDto(zjAssistantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjAssistantDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZjAssistantDTO> findByCriteria(ZjAssistantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjAssistant> specification = createSpecification(criteria);
        return zjAssistantRepository.findAll(specification, page)
            .map(zjAssistantMapper::toDto);
    }

    /**
     * Function to convert ZjAssistantCriteria to a {@link Specification}
     */
    private Specification<ZjAssistant> createSpecification(ZjAssistantCriteria criteria) {
        Specification<ZjAssistant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjAssistant_.id));
            }
            if (criteria.getZjSpecialtyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjSpecialtyId(), ZjAssistant_.zjSpecialtyId));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonId(), ZjAssistant_.personId));
            }
            if (criteria.getPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonName(), ZjAssistant_.personName));
            }
            if (criteria.getWorkloadRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWorkloadRate(), ZjAssistant_.workloadRate));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), ZjAssistant_.remark));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ZjAssistant_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ZjAssistant_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ZjAssistant_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ZjAssistant_.lastModifiedDate));
            }
            if (criteria.getMultiTenancyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMultiTenancyId(), ZjAssistant_.multiTenancyId));
            }
        }
        return specification;
    }

}
