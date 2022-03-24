package com.zk.sentinel.rule.main.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2021/12/24 10:30
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
