package com.thsware.framework.service;

import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.domain.ZjMember;
import com.thsware.framework.repository.ZjMemberRepository;
import com.thsware.framework.service.dto.ZjMemberDTO;
import com.thsware.framework.service.mapper.ZjMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing ZjMember.
 */
@Service
@Transactional
public class ZjMemberService {

    private final Logger log = LoggerFactory.getLogger(ZjMemberService.class);

    private final ZjMemberRepository zjMemberRepository;

    private final ZjMemberMapper zjMemberMapper;

    public ZjMemberService(ZjMemberRepository zjMemberRepository, ZjMemberMapper zjMemberMapper) {
        this.zjMemberRepository = zjMemberRepository;
        this.zjMemberMapper = zjMemberMapper;
    }

    /**
     * Save a zjMember.
     *
     * @param zjMemberDTO the entity to save
     * @return the persisted entity
     */
    public ZjMemberDTO save(ZjMemberDTO zjMemberDTO) {
        log.debug("Request to save ZjMember : {}", zjMemberDTO);
        ZjMember zjMember = zjMemberMapper.toEntity(zjMemberDTO);
        zjMember = zjMemberRepository.save(zjMember);
        return zjMemberMapper.toDto(zjMember);
    }

    /**
     * Get all the zjMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjMembers");
        return zjMemberRepository.findAll(pageable)
            .map(zjMemberMapper::toDto);
    }


    /**
     * Get one zjMember by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjMemberDTO> findOne(String id) {
        log.debug("Request to get ZjMember : {}", id);
        return zjMemberRepository.findById(id)
            .map(zjMemberMapper::toDto);
    }

    /**
     * Delete the zjMember by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjMember : {}", id);
        zjMemberRepository.deleteById(id);
    }

    /**
     * 删除项目团队成员根据项目id
     * @param zjProjectId
     */
    public void deleteMemberByZjProjectId(String zjProjectId) {
        zjMemberRepository.deleteMemberByZjProjectId(zjProjectId);
    }

    public List<ZjMember> findAllByZjProjectId(String zjProjectId){
        return zjMemberRepository.findAllByZjProjectId(zjProjectId);
    }
}
