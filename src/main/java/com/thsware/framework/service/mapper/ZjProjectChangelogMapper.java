package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjProjectChangelogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjProjectChangelog and its DTO ZjProjectChangelogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjProjectChangelogMapper extends EntityMapper<ZjProjectChangelogDTO, ZjProjectChangelog> {



    default ZjProjectChangelog fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjProjectChangelog zjProjectChangelog = new ZjProjectChangelog();
        zjProjectChangelog.setId(id);
        return zjProjectChangelog;
    }
}
