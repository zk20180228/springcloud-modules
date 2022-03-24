package com.zk.sentinel.dashboard.hello.main.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/23 18:03
 * @Description:
 * @Modified:
 * @version: V1.0
 *
 *1 自定义BlockExceptionHandler,默认的sentinel整合了springMvc，但是如果被整合的资源(非 @SentinelResource标记的)不配置
 * BlockExceptionHandler那么默认会响应客户端Blocked by Sentinel (flow limiting)
 *2 被@SentinelResource标记的方法发生流控异常，如果没有找到blockHandler或者fallback【是不会】调用这个BlockExceptionHandler
 *3 总结这个BlockExceptionHandler，相当于给【sentinel整合了springMvc的【资源】】的一个默认处理handler,
 * 如果不配置默认会响应客户端Blocked by Sentinel (flow limiting)，即使定义了全局处理异常捕获BlockException（只针对handlerMapping,即接口）,也无效
 *4 AbstractSentinelInterceptor
 *
 */
@Component
public class GlobalSpringMvcSentinelExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {

        Map<String, Object> rs = new HashMap<String, Object>() {{
            put("code", -1);
            put("msg", e instanceof FlowException ? "发生流控" : "发生熔断");
            put("data", "经过MyBlockExceptionHandler处理");
        }};

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(rs));
        response.flushBuffer();
    }
}
