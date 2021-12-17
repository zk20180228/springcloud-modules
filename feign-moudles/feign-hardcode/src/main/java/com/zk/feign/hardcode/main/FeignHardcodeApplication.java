package com.zk.feign.hardcode.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @EnableFeignClients: 开启feign的支持
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignHardcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignHardcodeApplication.class, args);
    }

}
