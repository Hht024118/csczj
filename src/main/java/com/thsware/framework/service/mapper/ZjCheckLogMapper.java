package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjCheckLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjCheckLog and its DTO ZjCheckLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjCheckLogMapper extends EntityMapper<ZjCheckLogDTO, ZjCheckLog> {



    default ZjCheckLog fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjCheckLog zjCheckLog = new ZjCheckLog();
        zjCheckLog.setId(id);
        return zjCheckLog;
    }
}
