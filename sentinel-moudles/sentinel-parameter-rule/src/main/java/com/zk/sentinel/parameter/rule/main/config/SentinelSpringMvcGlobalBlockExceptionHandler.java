package com.zk.sentinel.parameter.rule.main.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @Author: zk
 * @Date: 2022/1/5 17:01
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Component
public class SentinelSpringMvcGlobalBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new HashMap<String, Object>() {{
            put("code", -2);
            //实际上这里是流控降级(e instanceof FlowException 的判断不严格，主要是懒得写了)
            put("msg", e instanceof FlowException ? "发生流控" : "熔断降级");
            put("data", "SpringMvcSentinelGlobalBlockExceptionHandler处理");
        }}));

        response.flushBuffer();
    }
}
