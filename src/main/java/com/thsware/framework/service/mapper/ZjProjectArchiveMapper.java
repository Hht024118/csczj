package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjProjectArchiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjProjectArchive and its DTO ZjProjectArchiveDTO.
 */
@Mapper(componentModel = "spring", uses = {ZjProjectMapper.class})
public interface ZjProjectArchiveMapper extends EntityMapper<ZjProjectArchiveDTO, ZjProjectArchive> {

    @Mappings({
        @Mapping(source = "zjProject.id", target = "zjProjectId"),
    })
    ZjProjectArchiveDTO toDto(ZjProjectArchive zjProjectArchive);

    @Mappings({
        @Mapping(source = "zjProjectId", target = "zjProject"),
    })
    ZjProjectArchive toEntity(ZjProjectArchiveDTO zjProjectArchiveDTO);

    default ZjProjectArchive fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjProjectArchive zjProjectArchive = new ZjProjectArchive();
        zjProjectArchive.setId(id);
        return zjProjectArchive;
    }
}
