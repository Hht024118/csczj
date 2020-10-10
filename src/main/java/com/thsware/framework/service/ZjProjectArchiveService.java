package com.thsware.framework.service;

import com.thsware.framework.annotation.ThsMultiTenancyFilter;
import com.thsware.framework.domain.ZjProjectArchive;
import com.thsware.framework.repository.ZjProjectArchiveRepository;
import com.thsware.framework.service.dto.ZjProjectArchiveDTO;
import com.thsware.framework.service.mapper.ZjProjectArchiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ZjProjectArchive.
 */
@Service
@Transactional
public class ZjProjectArchiveService {

    private final Logger log = LoggerFactory.getLogger(ZjProjectArchiveService.class);

    private final ZjProjectArchiveRepository zjProjectArchiveRepository;

    private final ZjProjectArchiveMapper zjProjectArchiveMapper;

    public ZjProjectArchiveService(ZjProjectArchiveRepository zjProjectArchiveRepository, ZjProjectArchiveMapper zjProjectArchiveMapper) {
        this.zjProjectArchiveRepository = zjProjectArchiveRepository;
        this.zjProjectArchiveMapper = zjProjectArchiveMapper;
    }

    /**
     * Save a zjProjectArchive.
     *
     * @param zjProjectArchiveDTO the entity to save
     * @return the persisted entity
     */
    public ZjProjectArchiveDTO save(ZjProjectArchiveDTO zjProjectArchiveDTO) {
        log.debug("Request to save ZjProjectArchive : {}", zjProjectArchiveDTO);
        ZjProjectArchive zjProjectArchive = zjProjectArchiveMapper.toEntity(zjProjectArchiveDTO);
        zjProjectArchive = zjProjectArchiveRepository.save(zjProjectArchive);
        return zjProjectArchiveMapper.toDto(zjProjectArchive);
    }

    /**
     * Get all the zjProjectArchives.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Page<ZjProjectArchiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZjProjectArchives");
        return zjProjectArchiveRepository.findAll(pageable)
            .map(zjProjectArchiveMapper::toDto);
    }


    /**
     * Get one zjProjectArchive by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    @ThsMultiTenancyFilter
    public Optional<ZjProjectArchiveDTO> findOne(String id) {
        log.debug("Request to get ZjProjectArchive : {}", id);
        return zjProjectArchiveRepository.findById(id)
            .map(zjProjectArchiveMapper::toDto);
    }

    /**
     * Delete the zjProjectArchive by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ZjProjectArchive : {}", id);
        zjProjectArchiveRepository.deleteById(id);
    }
}
