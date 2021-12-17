package com.zk.ribbon.consume.main.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zk
 * @Date: 2021/12/13 14:18
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration(proxyBeanMethods = false)
public class RestfulConfig {

    /**
     * @LoadBalanced 启用ribbon客户端负载均衡器
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }
}
