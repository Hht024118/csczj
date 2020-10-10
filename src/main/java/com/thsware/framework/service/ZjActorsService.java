package com.thsware.framework.service;

import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.config.Constants;
import com.thsware.framework.domain.ZjActors;
import com.thsware.framework.domain.ZjProject;
import com.thsware.framework.repository.ZjActorsRepository;
import com.thsware.framework.security.ThsSecurityUtils;
import com.thsware.framework.service.dto.ZjActorsDTO;
import com.thsware.framework.service.mapper.ZjActorsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing ZjActors.
 */
@Service
@Transactional
public class ZjActorsService {

    private final Logger log = LoggerFactory.getLogger(ZjActorsService.class);

    private final ZjActorsRepository zjActorsRepository;

    private final ZjActorsMapper zjActorsMapper;

    public ZjActorsService(ZjActorsRepository zjActorsRepository, ZjActorsMapper zjActorsMapper) {
        this.zjActorsRepository = zjActorsRepository;
        this.zjActorsMapper = zjActorsMapper;
    }

    /**
     * Save a zjActors.
     *
     * @param zjActorsDTO the entity to save
     * @return the persisted entity
     */
    public ZjActorsDTO save(ZjActorsDTO zjActorsDTO) {
        log.debug("Request to save ZjActors : {}", zjActorsDTO);
        ZjActors zjActors = zjActorsMapper.toEntity(zjActorsDTO);
        zjActors = zjActorsRepository.save(zjActors);
        return zjActorsMapper.toDto(zjActors);
    }

    /**
     * Get all the zjActors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjActorsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjActors");
        return zjActorsRepository.findAll(pageable)
            .map(zjActorsMapper::toDto);
    }


    /**
     * Get one zjActors by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjActorsDTO> findOne(String id) {
        log.debug("Request to get ZjActors : {}", id);
        return zjActorsRepository.findById(id)
            .map(zjActorsMapper::toDto);
    }

    /**
     * Delete the zjActors by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjActors : {}", id);
        zjActorsRepository.deleteById(id);
    }

    public void saveActors(String projectId, String step, String personId) {
        List<ZjActors> zjActorsList = zjActorsRepository.findAllByProjectIdAndStepAndPersonId(projectId, step, personId);
        if (zjActorsList == null || zjActorsList.size() < 1) {
            ZjActorsDTO zjActorsDTO = new ZjActorsDTO();
            zjActorsDTO.setStep(step);
            zjActorsDTO.setProjectId(projectId);
            zjActorsDTO.setPersonId(personId);
            zjActorsDTO.setPersonName(ThsSecurityUtils.getDecodedDetails().get().get("entity_name").toString());
            this.save(zjActorsDTO);
        }
    }

    public List<ZjActors> findAllByProjectId(String projectId){
        return zjActorsRepository.findAllByProjectId(projectId);
    }
}
