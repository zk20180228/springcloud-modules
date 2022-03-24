package com.zk.gateway.accesslog.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayAccesslogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAccesslogApplication.class, args);
    }

}
