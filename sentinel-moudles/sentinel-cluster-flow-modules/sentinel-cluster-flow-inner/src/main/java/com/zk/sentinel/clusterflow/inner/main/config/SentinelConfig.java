package com.zk.sentinel.clusterflow.inner.main.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2022/1/11 15:45
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class SentinelConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){

        return new SentinelResourceAspect();
    }
}
