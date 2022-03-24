package com.zk.sentinel.nacos.datasource;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableNacos(globalProperties=@NacosProperties())
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelNacosDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelNacosDatasourceApplication.class, args);
    }

}
