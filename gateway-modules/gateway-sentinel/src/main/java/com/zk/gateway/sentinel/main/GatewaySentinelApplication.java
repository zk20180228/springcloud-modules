package com.zk.gateway.sentinel.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewaySentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySentinelApplication.class, args);
    }

}
