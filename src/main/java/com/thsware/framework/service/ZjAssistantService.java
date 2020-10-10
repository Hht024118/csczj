package com.thsware.framework.service;

import com.thsware.framework.domain.ZjAssistant;
import com.thsware.framework.repository.ZjAssistantRepository;
import com.thsware.framework.service.dto.ZjAssistantDTO;
import com.thsware.framework.service.mapper.ZjAssistantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ZjAssistant.
 */
@Service
@Transactional
public class ZjAssistantService {

    private final Logger log = LoggerFactory.getLogger(ZjAssistantService.class);

    private final ZjAssistantRepository zjAssistantRepository;

    private final ZjAssistantMapper zjAssistantMapper;

    public ZjAssistantService(ZjAssistantRepository zjAssistantRepository, ZjAssistantMapper zjAssistantMapper) {
        this.zjAssistantRepository = zjAssistantRepository;
        this.zjAssistantMapper = zjAssistantMapper;
    }

    /**
     * Save a zjAssistant.
     *
     * @param zjAssistantDTO the entity to save
     * @return the persisted entity
     */
    public ZjAssistantDTO save(ZjAssistantDTO zjAssistantDTO) {
        log.debug("Request to save ZjAssistant : {}", zjAssistantDTO);
        ZjAssistant zjAssistant = zjAssistantMapper.toEntity(zjAssistantDTO);
        zjAssistant = zjAssistantRepository.save(zjAssistant);
        return zjAssistantMapper.toDto(zjAssistant);
    }

    /**
     * Get all the zjAssistants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ZjAssistantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjAssistants");
        return zjAssistantRepository.findAll(pageable)
            .map(zjAssistantMapper::toDto);
    }


    /**
     * Get one zjAssistant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ZjAssistantDTO> findOne(String id) {
        log.debug("Request to get ZjAssistant : {}", id);
        return zjAssistantRepository.findById(id)
            .map(zjAssistantMapper::toDto);
    }

    /**
     * Delete the zjAssistant by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjAssistant : {}", id);
        zjAssistantRepository.deleteById(id);
    }

    public void deleteByZjSpecialtyId(String id) {
        log.debug("Request to delete ZjAssistant : {}", id);
        zjAssistantRepository.deleteByZjSpecialtyId(id);
    }
}
