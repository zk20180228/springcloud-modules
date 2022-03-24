package com.zk.sentinel.clusterflow.outer.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelClusterFlowOuterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelClusterFlowOuterApplication.class, args);
    }

}
