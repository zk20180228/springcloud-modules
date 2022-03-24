package com.zk.skywalking.api.consumer;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableFeignClients
@EnableNacos(globalProperties=@NacosProperties)
@EnableDiscoveryClient
@SpringBootApplication
public class SkywalkingApiConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApiConsumerApplication.class, args);
    }

}
