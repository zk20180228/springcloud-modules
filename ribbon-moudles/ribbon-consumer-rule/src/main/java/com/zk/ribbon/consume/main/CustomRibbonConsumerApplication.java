package com.zk.ribbon.consume.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 开启服务注册与发现
 *
 *
 * @SpringBootApplication(exclude= {CustomRibbonRuleConfig.class}):
 * EnableAutoConfiguration被称为自动配置类，在spring.factories中以org.springframework.boot.autoconfigure.EnableAutoConfiguration=xxx,xxx形式配置
 * @SpringBootApplication中的exclude只能排除EnableAutoConfiguration配置的类，否则报异常
 */
@EnableDiscoveryClient
/**
 * exclude= {CustomRibbonRuleConfig.class}:排除自定义ribbon rule的配置，否则会变为全局配置
 */
@SpringBootApplication
//@RibbonClients指定不同被调用方的负载均衡规则，但推荐使用yml配置方式
//@RibbonClients(value = {
//        /**
//         * value:是被调用者微服务的名字
//         */
//        @RibbonClient(value = "ribbon-provider-custom-rule",configuration = CustomRibbonRuleConfig.class )/*,*/
//        /**@RibbonClient()*/
//})
public class CustomRibbonConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomRibbonConsumerApplication.class, args);
    }

}
