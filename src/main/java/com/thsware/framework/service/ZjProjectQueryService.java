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

import com.thsware.framework.domain.ZjProject;
import com.thsware.framework.domain.*; // for static metamodels
import com.thsware.framework.repository.ZjProjectRepository;
import com.thsware.framework.service.dto.ZjProjectCriteria;

import com.thsware.framework.service.dto.ZjProjectDTO;
import com.thsware.framework.service.mapper.ZjProjectMapper;

/**
 * Service for executing complex queries for ZjProject entities in the database.
 * The main input is a {@link ZjProjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZjProjectDTO} or a {@link Page} of {@link ZjProjectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZjProjectQueryService extends QueryService<ZjProject> {

    private final Logger log = LoggerFactory.getLogger(ZjProjectQueryService.class);

    private final ZjProjectRepository zjProjectRepository;

    private final ZjProjectMapper zjProjectMapper;

    public ZjProjectQueryService(ZjProjectRepository zjProjectRepository, ZjProjectMapper zjProjectMapper) {
        this.zjProjectRepository = zjProjectRepository;
        this.zjProjectMapper = zjProjectMapper;
    }

    /**
     * Return a {@link List} of {@link ZjProjectDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public List<ZjProjectDTO> findByCriteria(ZjProjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZjProject> specification = createSpecification(criteria);
        return zjProjectMapper.toDto(zjProjectRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ZjProjectDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjProjectDTO> findByCriteria(ZjProjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZjProject> specification = createSpecification(criteria);
        return zjProjectRepository.findAll(specification, page)
            .map(zjProjectMapper::toDto);
    }

    /**
     * Function to convert ZjProjectCriteria to a {@link Specification}
     */
    private Specification<ZjProject> createSpecification(ZjProjectCriteria criteria) {
        Specification<ZjProject> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZjProject_.id));
            }
            if (criteria.getProjectNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectNo(), ZjProject_.projectNo));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractId(), ZjProject_.contractId));
            }
            if (criteria.getContractNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractNo(), ZjProject_.contractNo));
            }
            if (criteria.getContractName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContractName(), ZjProject_.contractName));
            }
            if (criteria.getProjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectName(), ZjProject_.projectName));
            }
            if (criteria.getRegisterDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisterDate(), ZjProject_.registerDate));
            }
            if (criteria.getPublishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublishDate(), ZjProject_.publishDate));
            }
            if (criteria.getDelegateUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelegateUnit(), ZjProject_.delegateUnit));
            }
            if (criteria.getDelegateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelegateDate(), ZjProject_.delegateDate));
            }
            if (criteria.getDelegateDept() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelegateDept(), ZjProject_.delegateDept));
            }
            if (criteria.getDelegateLinkman() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelegateLinkman(), ZjProject_.delegateLinkman));
            }
            if (criteria.getRequiredDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequiredDate(), ZjProject_.requiredDate));
            }
            if (criteria.getIsGovernmentInvest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsGovernmentInvest(), ZjProject_.isGovernmentInvest));
            }
            if (criteria.getProjectType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectType(), ZjProject_.projectType));
            }
            if (criteria.getProjectIndustry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectIndustry(), ZjProject_.projectIndustry));
            }
            if (criteria.getProjectSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectSource(), ZjProject_.projectSource));
            }
            if (criteria.getBusiType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusiType(), ZjProject_.busiType));
            }
            if (criteria.getSpecialty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecialty(), ZjProject_.specialty));
            }
            if (criteria.getProjectScale() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectScale(), ZjProject_.projectScale));
            }
            if (criteria.getCostMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostMoney(), ZjProject_.costMoney));
            }
            if (criteria.getSubmitMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitMoney(), ZjProject_.submitMoney));
            }
            if (criteria.getProjectManager() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectManager(), ZjProject_.projectManager));
            }
            if (criteria.getProjectManagerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectManagerName(), ZjProject_.projectManagerName));
            }
            if (criteria.getPlanFinishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlanFinishDate(), ZjProject_.planFinishDate));
            }
            if (criteria.getImplementUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImplementUnit(), ZjProject_.implementUnit));
            }
            if (criteria.getContentScope() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContentScope(), ZjProject_.contentScope));
            }
            if (criteria.getIsOutsource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsOutsource(), ZjProject_.isOutsource));
            }
            if (criteria.getOutsourceDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutsourceDesc(), ZjProject_.outsourceDesc));
            }
            if (criteria.getProjectProgress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectProgress(), ZjProject_.projectProgress));
            }
            if (criteria.getProjectBudget() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectBudget(), ZjProject_.projectBudget));
            }
            if (criteria.getNeedMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNeedMoney(), ZjProject_.needMoney));
            }
            if (criteria.getFlowState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlowState(), ZjProject_.flowState));
            }
            if (criteria.getProjectState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectState(), ZjProject_.projectState));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), ZjProject_.remark));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ZjProject_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ZjProject_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ZjProject_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), ZjProject_.lastModifiedDate));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentId(), ZjProject_.parentId));
            }
            if (criteria.getReportConclusion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReportConclusion(), ZjProject_.reportConclusion));
            }
            if (criteria.getLinkageBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLinkageBranch(), ZjProject_.linkageBranch));
            }
            if (criteria.getContractMoney() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractMoney(), ZjProject_.contractMoney));
            }
            if (criteria.getEstimatedIncome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedIncome(), ZjProject_.estimatedIncome));
            }
            if (criteria.getIsCcbClient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsCcbClient(), ZjProject_.isCcbClient));
            }
            if (criteria.getAttentionProjectType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAttentionProjectType(), ZjProject_.attentionProjectType));
            }
            if (criteria.getCityWhere() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityWhere(), ZjProject_.cityWhere));
            }
            if (criteria.getTotalProjectCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalProjectCost(), ZjProject_.totalProjectCost));
            }
            if (criteria.getCooperativeCompany() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCooperativeCompany(), ZjProject_.cooperativeCompany));
            }
            if (criteria.getIsCold() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsCold(), ZjProject_.isCold));
            }
            if (criteria.getIsGood() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsGood(), ZjProject_.isGood));
            }
            if (criteria.getMarketRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMarketRate(), ZjProject_.marketRate));
            }
        }
        return specification;
    }

}
