package com.zk.skywalking.api.consumer.config.fegin.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @Author: zk
 * @Date: 2022/1/27 14:53
 * @Description:
 * @Modified:
 * @version: V1.0
 */
public class RequestInterceptor01 implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        System.out.println("fegin.....RequestInterceptor01执行了");
    }
}
