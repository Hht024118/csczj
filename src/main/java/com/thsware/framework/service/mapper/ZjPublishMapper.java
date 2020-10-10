package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjPublishDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjPublish and its DTO ZjPublishDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjPublishMapper extends EntityMapper<ZjPublishDTO, ZjPublish> {



    default ZjPublish fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjPublish zjPublish = new ZjPublish();
        zjPublish.setId(id);
        return zjPublish;
    }
}
