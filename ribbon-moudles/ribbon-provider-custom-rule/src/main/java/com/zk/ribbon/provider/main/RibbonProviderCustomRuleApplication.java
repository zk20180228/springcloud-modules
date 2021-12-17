package com.zk.ribbon.provider.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RibbonProviderCustomRuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RibbonProviderCustomRuleApplication.class, args);
    }

}
