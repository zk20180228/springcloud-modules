package com.zk.sentinel.parameter.rule.main.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2022/1/5 16:43
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
