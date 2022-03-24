package com.zk.sentinel.core.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SentinelCoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelCoreApiApplication.class, args);
    }

}
