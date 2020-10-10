package com.thsware.framework.web.rest;


import com.thsware.framework.service.ExportTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: csczj
 * @description: 导出office模板
 * @author: Huanggx
 * @create: 2018-12-04 10:55
 **/
@RestController
@RequestMapping("/api")
public class ExportTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ExportTemplateResource.class);

    private final ExportTemplateService exportTemplateService;

    public ExportTemplateResource(ExportTemplateService exportTemplateService){
        this.exportTemplateService = exportTemplateService;
    }


    @PostMapping("/exprotTemplate")
    public ResponseEntity<Map<String, String>> exportTemplate(@RequestParam String templatePath, @RequestParam String filePath, @RequestParam String fk, @RequestParam String methodName){
        log.debug("进入exprotTemplate·····························");
        log.debug("templatePath··" + templatePath);
        log.debug("filePath·········"+ filePath);
        log.debug("methodName·····" + methodName);
        Map<String, String> map = new HashMap<>();
        map.put("success", "true");
        map.put("message","");
        Class clazz = null;
        Method method = null;
        try {
            clazz = exportTemplateService.getClass();
            method = clazz.getDeclaredMethod(methodName, String.class, String.class, String.class);
            method.invoke(exportTemplateService, templatePath, filePath, fk);

        }   catch (NoSuchMethodException e) {
            log.error("找不到导出模板的对应方法，请检查模板管理配置的导出方法是否正确:",e );
            map.put("success", "false");
            map.put("message", "找不到导出模板的对应方法，请检查模板管理配置的导出方法是否正确");
            map.put("error", e.toString());
        } catch (IllegalAccessException e) {
            log.error("导出模板的对应方法为私有方法，请检查模板管理配置的导出方法是否正确:",e );
            map.put("success", "false");
            map.put("message", "导出模板的对应方法为私有方法，请检查模板管理配置的导出方法是否正确");
            map.put("error", e.toString());
        } catch (InvocationTargetException e) {
            log.error("导出模板方法内部出现异常:",e );
            map.put("success", "false");
            map.put("message", "导出模板方法内部出现异常");
            map.put("error", e.toString());
        } catch (Exception e){
            log.error("执行导出模板时出现异常:",e );
            map.put("success", "false");
            map.put("message", "执行导出模板时出现异常");
            map.put("error", e.toString());
        }finally {
            return ResponseEntity.ok(map);
        }
    }

}
