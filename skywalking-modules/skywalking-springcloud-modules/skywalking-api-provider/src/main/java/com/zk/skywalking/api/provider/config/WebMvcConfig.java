package com.zk.skywalking.api.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zk
 * @Date: 2022/1/25 16:14
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(false)
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiRequestTimeInterceptor());
    }


    /**
     * springmvc对异步的支持配置
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.registerCallableInterceptors(timeoutInterceptor());
        //配置一个异步执行的线程池
        configurer.setTaskExecutor(threadPoolTaskExecutor01());
        //设置响应时间 20s
        configurer.setDefaultTimeout(20000L);
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {

        //Callable执行的拦截器，该拦截器在超时时返回一个AsyncRequestTimeoutException异常对象
        return new TimeoutCallableProcessingInterceptor();
    }

    @Bean("WYF-Thread")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor01() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(10);
        t.setMaxPoolSize(100);
        t.setQueueCapacity(20);
        t.setThreadNamePrefix("WYF-Thread-");
        return t;
    }

    @Bean("test-Thread")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor02() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(10);
        t.setMaxPoolSize(100);
        t.setQueueCapacity(20);
        t.setThreadNamePrefix("test-Thread-");
        return t;
    }
}
