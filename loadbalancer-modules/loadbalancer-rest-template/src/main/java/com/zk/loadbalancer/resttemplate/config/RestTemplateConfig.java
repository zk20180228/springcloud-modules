package com.zk.loadbalancer.resttemplate.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zk
 * @Date: 2021/12/14 14:43
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class RestTemplateConfig {


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
