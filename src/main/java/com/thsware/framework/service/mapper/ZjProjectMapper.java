package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjProject and its DTO ZjProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjProjectMapper extends EntityMapper<ZjProjectDTO, ZjProject> {



    default ZjProject fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjProject zjProject = new ZjProject();
        zjProject.setId(id);
        return zjProject;
    }
}
