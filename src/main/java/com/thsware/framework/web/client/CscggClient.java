package com.thsware.framework.web.client;

import com.thsware.framework.client.AuthorizedUserFeignClient;
import com.thsware.framework.service.dto.ProjectPersonDTO;
import com.thsware.framework.service.dto.SysBusiformDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@AuthorizedUserFeignClient(name="cscgg")
public interface CscggClient {

    //合同
    @RequestMapping(value ="/api/pub-contracts/{id}",method= RequestMethod.GET)
    ResponseEntity<Map<String,String>> getHtById(@PathVariable(value="id") String id);

    //委托单位
    @RequestMapping(value ="/api/pub-customers/{id}",method= RequestMethod.GET)
    ResponseEntity<Map<String,String>> getWtdwById(@PathVariable(value="id") String id);

    //合同
    @RequestMapping(value ="/api/sys-busiforms/deleteByProjectId/{id}",method= RequestMethod.DELETE)
    ResponseEntity<Void> deleteSysBusiform(@PathVariable(value="id") String id);

    @RequestMapping(value = "/api/project-people",method = RequestMethod.POST)
    ResponseEntity<ProjectPersonDTO> createProjectPerson(@Valid @RequestBody ProjectPersonDTO projectPersonDTO);

    @RequestMapping(value = "/api/per-infos/getIsPerInfos",method = RequestMethod.POST)
    Boolean getIsPerInfos( @RequestParam(value="projectNo") String projectNo,
                           @RequestParam(value="personName") String personName,
                           @RequestParam(value="technicalPost") String technicalPost);

    @RequestMapping(value ="/api/sys-busiforms/updateByProject",method= RequestMethod.PUT)
    ResponseEntity<SysBusiformDTO> updateBusiforms(@RequestBody SysBusiformDTO sysBusiformDTO);

    @RequestMapping(value = "/api/sys-busiforms/getSysBusiformByBusiId/{busiId}",method = RequestMethod.GET)
    ResponseEntity<SysBusiformDTO> getBusiforms(@PathVariable(value = "busiId") String busiId);
}
