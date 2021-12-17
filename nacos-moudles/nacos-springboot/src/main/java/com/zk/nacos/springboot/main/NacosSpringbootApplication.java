package com.zk.nacos.springboot.main;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这里不用添加nacos的驱动注解了(这里演示加了,是为了nacos-spring,nacos-springcboot,nacos-springcloud三种场景下的行为一致)
 */
@EnableNacos(globalProperties = @NacosProperties)
@SpringBootApplication
public class NacosSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosSpringbootApplication.class, args);
    }

}
