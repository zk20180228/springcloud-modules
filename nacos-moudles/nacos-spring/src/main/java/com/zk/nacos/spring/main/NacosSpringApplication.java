package com.zk.nacos.spring.main;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableNacos(globalProperties = @NacosProperties)
@SpringBootApplication
public class NacosSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosSpringApplication.class, args);
    }

}
