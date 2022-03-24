package com.zk.gateway.gatewayfilterfactory.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayGatewayFilterFactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayGatewayFilterFactoryApplication.class, args);
    }

}
