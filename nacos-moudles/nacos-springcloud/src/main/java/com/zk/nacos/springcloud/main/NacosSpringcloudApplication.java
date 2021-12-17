package com.zk.nacos.springcloud.main;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 坑:
 * 不加@EnableNacos(globalProperties = @NacosProperties),就无法使用@NacosInjected
 */
@EnableNacos(globalProperties = @NacosProperties)
@SpringBootApplication
public class NacosSpringcloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosSpringcloudApplication.class, args);
    }

}
