package com.zk.sentinel.degraderule.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelDegradeRuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelDegradeRuleApplication.class, args);
    }
}
