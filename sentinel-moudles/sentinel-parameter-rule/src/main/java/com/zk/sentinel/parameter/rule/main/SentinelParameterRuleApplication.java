package com.zk.sentinel.parameter.rule.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableDiscoveryClient
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class SentinelParameterRuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelParameterRuleApplication.class, args);
    }

}
