package com.zk.sentinelaspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy=true)
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelAspectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelAspectApplication.class, args);
    }

}
