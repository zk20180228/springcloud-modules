package com.zk.skywalking.api.provider;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @EnableAsync:可以启动异步支持，但是这个和我们平时用的线程池来提交任务的执行方式没什么区别，并不能针对controller应用servlet的异步模型
 * 如果试图在一个controller的请求方法上使用@async，那么将返回empty,因此可以说这个注解没什么用，尽量不要用，如果想异步执行，
 * 自己定义异步执行的相关操作，如线程池，拒绝策略，统一异常处理
 *
 */
@EnableAsync
@MapperScan("com.zk.skywalking.api.provider.modules")
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableNacos(globalProperties = @NacosProperties)
@EnableDiscoveryClient
@SpringBootApplication
public class SkywalkingApiProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkywalkingApiProviderApplication.class, args);
    }

}
