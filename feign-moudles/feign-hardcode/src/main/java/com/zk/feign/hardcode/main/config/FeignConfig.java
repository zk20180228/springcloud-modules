package com.zk.feign.hardcode.main.config;

import com.zk.feign.hardcode.main.feign.interceptor.FeignAuthRequestInterceptor;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zk
 * @Date: 2021/12/14 16:41
 * @Description:
 * @Modified:
 * @version: V1.0
 * 当前类被springboot扫描所以，该配置是全局生效的
 *
 *
 */
@Configuration
public class FeignConfig {


    /**
     * feign配置日志
     * 日志配置还要配合logging.level.feign接口包路径=debug
     *
     */
    @Bean
    public Logger.Level logLevel(){

        /**
         * NONE【性能最佳，适用于生产】：不记录任何日志（默认值）。
         * BASIC【适用于生产环境追踪问题】：仅记录请求方法、URL、响应状态代码以及
         * 执行时间。
         * HEADERS：记录BASIC级别的基础上，记录请求和响应的header。
         * FULL【比较适用于开发及测试环境定位问题】：记录请求和响应的header、body
         * 和元数据
         */
        return Logger.Level.FULL;
    }

    /**
     * feign的拦截器
     * @return
     */
    @Bean
    public FeignAuthRequestInterceptor feignAuthRequestInterceptor(){
        return new FeignAuthRequestInterceptor();
    }


    /**
     * feign的超时时间配置
     */
    @Bean
    public Request.Options requestOptions(){

        return new Request.Options(5, TimeUnit.SECONDS,5,TimeUnit.SECONDS,true);
    }

}
