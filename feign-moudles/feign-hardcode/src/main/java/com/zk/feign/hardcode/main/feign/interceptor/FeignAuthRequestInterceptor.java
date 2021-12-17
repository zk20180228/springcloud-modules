package com.zk.feign.hardcode.main.feign.interceptor;

import com.alibaba.fastjson.JSON;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/14 17:17
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class FeignAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Request request = template.request();
        String url = request.url();
        Map<String, Collection<String>> headers = request.headers();
        String methodName = request.httpMethod().name();
        log.info("本次请求的url为:{},请求方法:{},请求头:{}",url,methodName, JSON.toJSONString(headers));
    }
}
