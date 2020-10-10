package com.thsware.framework.web.client;

import com.thsware.framework.client.AuthorizedUserFeignClient;
import com.thsware.framework.service.dto.SysUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AuthorizedUserFeignClient(name="thsuaa")
public interface ThsUAAClient {

    @RequestMapping(value = "/api/sys-users/findListByMultiTenancyIdAndRoleName/{multiTenancyId}/{roleName}",method = RequestMethod.GET)
    ResponseEntity<List<SysUserDTO>> findListByMultiTenancyIdAndRoleName(@PathVariable(value = "multiTenancyId") String multiTenancyId,
                                                  @PathVariable(value = "roleName") String roleName);
}
