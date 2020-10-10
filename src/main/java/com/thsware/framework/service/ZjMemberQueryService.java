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

import com.thsware.framework.domain.ZjMember;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjMemberRepository;
import com.thsware.framework.service.dto.ZjMemberCriteria;

import com.thsware.framework.service.dto.ZjMemberDTO;
import com.thsware.framework.service.mapper.ZjMemberMapper;

/**
 * Service for executing complex queries for ZjMember entities in the database.
 * The main input is a {@link ZjMemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjMemberDTO} or a {@link Page} of {@link ZjMemberDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjMemberQueryService extends QueryService<ZjMember> {

    private final Logger log = LoggerFactory.getLogger(ZjMemberQueryService.class);

    private final ZjMemberRepository zjMemberRepository;

    private final ZjMemberMapper zjMemberMapper;

    public ZjMemberQueryService(ZjMemberRepository zjMemberRepository, ZjMemberMapper zjMemberMapper) {
        this.zjMemberRepository = zjMemberRepository;
        this.zjMemberMapper = zjMemberMapper;
    }

    /**
     * Return a {@link List} of {@link ZjMemberDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjMemberDTO> findByCriteria(ZjMemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjMember> specification = createSpecification(criteria);
        return zjMemberMapper.toDto(zjMemberRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjMemberDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjMemberDTO> findByCriteria(ZjMemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjMember> specification = createSpecification(criteria);
        return zjMemberRepository.findAll(specification, page)
            .map(zjMemberMapper::toDto);
    }

    /**
     * Function to convert ZjMemberCriteria to a {@link Specification}
     */
    private Specification<ZjMember> createSpecification(ZjMemberCriteria criteria) {
        Specification<ZjMember> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjMember_.id));
            }
            if (criteria.getZjProjectId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZjProjectId(), ZjMember_.zjProjectId));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), ZjMember_.type));
            }
            if (criteria.getOrderNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderNum(), ZjMember_.orderNum));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonId(), ZjMember_.personId));
            }
            if (criteria.getPersonName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonName(), ZjMember_.personName));
            }
        }
        return specification;
    }

}
