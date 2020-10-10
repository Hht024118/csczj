package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjSpecialtyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjSpecialty and its DTO ZjSpecialtyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjSpecialtyMapper extends EntityMapper<ZjSpecialtyDTO, ZjSpecialty> {



    default ZjSpecialty fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjSpecialty zjSpecialty = new ZjSpecialty();
        zjSpecialty.setId(id);
        return zjSpecialty;
    }
}
