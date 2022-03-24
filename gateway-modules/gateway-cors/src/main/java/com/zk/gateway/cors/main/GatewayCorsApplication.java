package com.zk.gateway.cors.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayCorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayCorsApplication.class, args);
    }

}
