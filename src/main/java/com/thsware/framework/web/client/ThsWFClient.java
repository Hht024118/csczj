package com.thsware.framework.web.client;

import com.thsware.framework.client.AuthorizedUserFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AuthorizedUserFeignClient(name="thsworkflow")
public interface ThsWFClient {

    @RequestMapping(value ="/api/getAuditIdeaList/{businessId}",method= RequestMethod.GET)
    ResponseEntity<List<Map<String,String>>> getAuditIdea(@PathVariable(value = "businessId") String businessId);


}
