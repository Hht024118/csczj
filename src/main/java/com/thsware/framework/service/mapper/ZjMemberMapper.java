package com.thsware.framework.service.mapper;

import com.thsware.framework.domain.*;
import com.thsware.framework.service.dto.ZjMemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZjMember and its DTO ZjMemberDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZjMemberMapper extends EntityMapper<ZjMemberDTO, ZjMember> {



    default ZjMember fromId(String id) {
        if (id == null) {
            return null;
        }
        ZjMember zjMember = new ZjMember();
        zjMember.setId(id);
        return zjMember;
    }
}
