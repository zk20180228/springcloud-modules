package com.zk.feign.ymlconfig.main.config.feign;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: zk
 * @Date: 2021/12/15 11:14
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class FeignRequestLogInterceptor01 implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Request request = template.request();
        log.info("FeignRequestLogInterceptor01---->请求地址:{}",request.url());
    }
}
