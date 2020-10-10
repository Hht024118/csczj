package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjActorsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjActors and its DTO ZjActorsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjActorsMapper extends EntityMapper<ZjActorsDTO, ZjActors> {



    default ZjActors fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjActors zjActors = new ZjActors();
        zjActors.setId(id);
        return zjActors;
    }
}
