package com.zk.sentinel.nacos.datasource.yml.main.config;

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
 * @Date: 2022/1/17 16:21
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Component
public class SpringMvcSentinelGlobalBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>() {{
            put("code", -2);
            put("msg", e instanceof FlowException ? "发生流控" : "熔断降级");
            put("data", "SpringMvcSentinelGlobalBlockExceptionHandler处理");
        }};

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(rs));
        response.flushBuffer();
    }
}
