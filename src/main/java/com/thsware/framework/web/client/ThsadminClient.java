package com.thsware.framework.web.client;

import com.thsware.framework.client.AuthorizedUserFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AuthorizedUserFeignClient(name="thsadmin")
public interface ThsadminClient {

    @RequestMapping(value = "/api/sys-dict-details",method = RequestMethod.GET)
    ResponseEntity<List<Map<Object, Object>>> getZdByType(@RequestParam Map<String, String> map);

    @RequestMapping(value ="/api/org-personnels/{id}",method= RequestMethod.GET)
    ResponseEntity<Map<String,String>> getRyById(@PathVariable(value="id") String id);

    @RequestMapping(value = "/api/sys-notifications",method = RequestMethod.POST)
    ResponseEntity<Map<Object, Object>> createSysNotification(@RequestBody Map<String, Object> map);

    @RequestMapping(value = "/api/sys-dicts",method = RequestMethod.GET)
    ResponseEntity<List<Map<String, Object>>> getDicts(@RequestParam Map<String, String> map);

    @RequestMapping(value = "/api/sys-dict-details",method = RequestMethod.GET)
    ResponseEntity<List<Map<String, Object>>> getDictDetails(@RequestParam Map<String, String> map);

}
