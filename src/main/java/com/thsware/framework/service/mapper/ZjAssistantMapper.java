package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjAssistantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjAssistant and its DTO ZjAssistantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjAssistantMapper extends EntityMapper<ZjAssistantDTO, ZjAssistant> {



    default ZjAssistant fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjAssistant zjAssistant = new ZjAssistant();
        zjAssistant.setId(id);
        return zjAssistant;
    }
}
