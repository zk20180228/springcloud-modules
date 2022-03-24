package com.zk.skywalking.api.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zk
 * @Date: 2022/1/26 16:55
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class ExectorConfig {


    @Bean
    public ExecutorService common(){

        return Executors.newFixedThreadPool(10);
    }
}
