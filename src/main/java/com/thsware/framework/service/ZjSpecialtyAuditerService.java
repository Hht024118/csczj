package com.thsware.framework.service;

import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.domain.ZjSpecialtyAuditer;
import com.thsware.framework.repository.ZjSpecialtyAuditerRepository;
import com.thsware.framework.service.dto.ZjSpecialtyAuditerDTO;
import com.thsware.framework.service.mapper.ZjSpecialtyAuditerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing ZjSpecialtyAuditer.
 */
@Service
@Transactional
public class ZjSpecialtyAuditerService {

    private final Logger log = LoggerFactory.getLogger(ZjSpecialtyAuditerService.class);

    private final ZjSpecialtyAuditerRepository zjSpecialtyAuditerRepository;

    private final ZjSpecialtyAuditerMapper zjSpecialtyAuditerMapper;

    public ZjSpecialtyAuditerService(ZjSpecialtyAuditerRepository zjSpecialtyAuditerRepository, ZjSpecialtyAuditerMapper zjSpecialtyAuditerMapper) {
        this.zjSpecialtyAuditerRepository = zjSpecialtyAuditerRepository;
        this.zjSpecialtyAuditerMapper = zjSpecialtyAuditerMapper;
    }

    /**
     * Save a zjSpecialtyAuditer.
     *
     * @param zjSpecialtyAuditerDTO the entity to save
     * @return the persisted entity
     */
    public ZjSpecialtyAuditerDTO save(ZjSpecialtyAuditerDTO zjSpecialtyAuditerDTO) {
        log.debug("Request to save ZjSpecialtyAuditer : {}", zjSpecialtyAuditerDTO);
        ZjSpecialtyAuditer zjSpecialtyAuditer = zjSpecialtyAuditerMapper.toEntity(zjSpecialtyAuditerDTO);
        zjSpecialtyAuditer = zjSpecialtyAuditerRepository.save(zjSpecialtyAuditer);
        return zjSpecialtyAuditerMapper.toDto(zjSpecialtyAuditer);
    }

    /**
     * Get all the zjSpecialtyAuditers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjSpecialtyAuditerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjSpecialtyAuditers");
        return zjSpecialtyAuditerRepository.findAll(pageable)
            .map(zjSpecialtyAuditerMapper::toDto);
    }


    /**
     * Get one zjSpecialtyAuditer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjSpecialtyAuditerDTO> findOne(String id) {
        log.debug("Request to get ZjSpecialtyAuditer : {}", id);
        return zjSpecialtyAuditerRepository.findById(id)
            .map(zjSpecialtyAuditerMapper::toDto);
    }

    /**
     * Delete the zjSpecialtyAuditer by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjSpecialtyAuditer : {}", id);
        zjSpecialtyAuditerRepository.deleteById(id);
    }

    public void deleteBySpecialtyId(String specialtyId) {
        zjSpecialtyAuditerRepository.deleteBySpecialtyId(specialtyId);
    }

    public List<ZjSpecialtyAuditer> findAllByZjSpecialtyId(String zjSpecialtyId) {
        return zjSpecialtyAuditerRepository.findAllByZjSpecialtyId(zjSpecialtyId);
    }

    public List<ZjSpecialtyAuditer> findAllByZjSpecialtyIdAndPersonIdAndType(String zjSpecialtyId,String personId, String type) {
        return zjSpecialtyAuditerRepository.findAllByZjSpecialtyIdAndPersonIdAndType(zjSpecialtyId,personId,type);
    }

    public List<ZjSpecialtyAuditer> findAllByZjProjectIdAndType(String zjProjectId, String type) {
        return zjSpecialtyAuditerRepository.findAllByZjProjectIdAndType(zjProjectId, type);
    }

    public List<ZjSpecialtyAuditer> findAllByZjSpecialtyIdAndType(String zjSpecialtyId, String type) {
        return zjSpecialtyAuditerRepository.findAllByZjSpecialtyIdAndType(zjSpecialtyId, type);
    }

    public List<ZjSpecialtyAuditer> findAllByZjProjectIdAndPersonIdAndType(String zjProjectId, String personId, String type) {
        return zjSpecialtyAuditerRepository.findAllByZjProjectIdAndPersonIdAndType(zjProjectId, personId, type);
    }


}
