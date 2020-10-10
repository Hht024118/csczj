package com.thsware.framework.client;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ths-uaa
 * @description: 配置feignClient的日志输出级别
 * @author: Huanggx
 * @create: 2018-09-21 09:17
 **/

@Configuration
public class ThsClientConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {

        return Logger.Level.FULL;

    }
}
