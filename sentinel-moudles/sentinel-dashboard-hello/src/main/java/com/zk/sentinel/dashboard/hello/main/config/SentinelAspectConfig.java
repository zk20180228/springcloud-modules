package com.zk.sentinel.dashboard.hello.main.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2021/12/23 12:20
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class SentinelAspectConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){

        return new SentinelResourceAspect();
    }
}
