package com.zk.gateway.globalfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayGlobalfilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayGlobalfilterApplication.class, args);
    }

}
