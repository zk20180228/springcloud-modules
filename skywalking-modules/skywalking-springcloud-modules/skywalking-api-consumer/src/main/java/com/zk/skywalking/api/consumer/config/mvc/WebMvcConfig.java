package com.zk.skywalking.api.consumer.config.mvc;

import com.zk.skywalking.api.consumer.config.mvc.interceptors.RequestTimeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: zk
 * @Date: 2022/1/27 10:06
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedMethods(Arrays.stream(HttpMethod.values()).flatMap(e -> Stream.of(e.name())).collect(Collectors.toList()).toArray(new String[]{}))
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600L);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestTimeInterceptor());
    }



    /**
     * zk-todo
     * 1 异步执行的异常处理
     * 2 分业务接口进行使用不同线程池
     * 3 设置线程的属性
     * 4 callable拦截器
     * 5 DeferredResult拦截器
     * springmvc对异步的支持配置
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.registerCallableInterceptors(timeoutInterceptor());
        configurer.setTaskExecutor(threadPoolTaskExecutor());
        //设置响应时间 20s
        configurer.setDefaultTimeout(20000L);
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(10);
        t.setMaxPoolSize(100);
        t.setQueueCapacity(20);
        t.setThreadNamePrefix("self-Thread-");
        return t;
    }

}
