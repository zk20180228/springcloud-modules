package com.zk.skywalking.api.consumer.config.sentienl;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2022/1/27 10:06
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
