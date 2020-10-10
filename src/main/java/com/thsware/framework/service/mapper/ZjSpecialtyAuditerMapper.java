package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjSpecialtyAuditerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjSpecialtyAuditer and its DTO ZjSpecialtyAuditerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjSpecialtyAuditerMapper extends EntityMapper<ZjSpecialtyAuditerDTO, ZjSpecialtyAuditer> {



    default ZjSpecialtyAuditer fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjSpecialtyAuditer zjSpecialtyAuditer = new ZjSpecialtyAuditer();
        zjSpecialtyAuditer.setId(id);
        return zjSpecialtyAuditer;
    }
}
