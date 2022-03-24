package com.zk.skywalking.api.gateway;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableNacos(globalProperties = @NacosProperties)
@EnableDiscoveryClient
@SpringBootApplication
public class SkywalkingApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApiGatewayApplication.class, args);
    }

}
