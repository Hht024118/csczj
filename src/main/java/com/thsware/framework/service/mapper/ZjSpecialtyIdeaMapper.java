package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjSpecialtyIdeaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjSpecialtyIdea and its DTO ZjSpecialtyIdeaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjSpecialtyIdeaMapper extends EntityMapper<ZjSpecialtyIdeaDTO, ZjSpecialtyIdea> {



    default ZjSpecialtyIdea fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjSpecialtyIdea zjSpecialtyIdea = new ZjSpecialtyIdea();
        zjSpecialtyIdea.setId(id);
        return zjSpecialtyIdea;
    }
}
