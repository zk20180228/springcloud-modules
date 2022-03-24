package com.zk.skywalking.api.alarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SkywalkingApiAlarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApiAlarmApplication.class, args);
    }

}
