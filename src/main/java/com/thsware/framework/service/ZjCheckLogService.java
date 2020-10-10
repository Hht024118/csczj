package com.thsware.framework.service;

import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.domain.ZjCheckLog;
import com.thsware.framework.repository.ZjCheckLogRepository;
import com.thsware.framework.service.dto.ZjCheckLogDTO;
import com.thsware.framework.service.mapper.ZjCheckLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ZjCheckLog.
 */
@Service
@Transactional
public class ZjCheckLogService {

    private final Logger log = LoggerFactory.getLogger(ZjCheckLogService.class);

    private final ZjCheckLogRepository zjCheckLogRepository;

    private final ZjCheckLogMapper zjCheckLogMapper;

    public ZjCheckLogService(ZjCheckLogRepository zjCheckLogRepository, ZjCheckLogMapper zjCheckLogMapper) {
        this.zjCheckLogRepository = zjCheckLogRepository;
        this.zjCheckLogMapper = zjCheckLogMapper;
    }

    /**
     * Save a zjCheckLog.
     *
     * @param zjCheckLogDTO the entity to save
     * @return the persisted entity
     */
    public ZjCheckLogDTO save(ZjCheckLogDTO zjCheckLogDTO) {
        log.debug("Request to save ZjCheckLog : {}", zjCheckLogDTO);
        ZjCheckLog zjCheckLog = zjCheckLogMapper.toEntity(zjCheckLogDTO);
        zjCheckLog = zjCheckLogRepository.save(zjCheckLog);
        return zjCheckLogMapper.toDto(zjCheckLog);
    }

    /**
     * Get all the zjCheckLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @ThsMultiTenancyFilter
    @Transactional(readOnly = true)
    public Page<ZjCheckLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjCheckLogs");
        return zjCheckLogRepository.findAll(pageable)
            .map(zjCheckLogMapper::toDto);
    }


    /**
     * Get one zjCheckLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjCheckLogDTO> findOne(String id) {
        log.debug("Request to get ZjCheckLog : {}", id);
        return zjCheckLogRepository.findById(id)
            .map(zjCheckLogMapper::toDto);
    }

    /**
     * Delete the zjCheckLog by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjCheckLog : {}", id);
        zjCheckLogRepository.deleteById(id);
    }

    public void cleanCheckLogByZjSpecialtyId(String zjSpecialtyId, String chekcStepOne, String chekcStepTwo) {
        zjCheckLogRepository.cleanCheckLogByZjSpecialtyId(zjSpecialtyId, chekcStepOne , chekcStepTwo);
    }

    public void cleanCheckLog(String zjSpecialtyId) {
        zjCheckLogRepository.cleanCheckLog(zjSpecialtyId);
    }

    public void cleanCheckLogES(String zjSpecialtyId) {
        zjCheckLogRepository.cleanCheckLogES(zjSpecialtyId);
    }

    public void cleanCheckLogS(String zjSpecialtyId) {
        zjCheckLogRepository.cleanCheckLogS(zjSpecialtyId);
    }
}
