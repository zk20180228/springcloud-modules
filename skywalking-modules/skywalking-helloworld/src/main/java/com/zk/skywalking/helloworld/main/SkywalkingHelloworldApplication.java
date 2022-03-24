package com.zk.skywalking.helloworld.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan(value = "com.zk.skywalking.helloworld.main.modules")
@EnableDiscoveryClient
@SpringBootApplication
public class SkywalkingHelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingHelloworldApplication.class, args);
    }

}
