package com.zk.sentinel.clusterflow.inner.main.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/11 15:47
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Component
public class SpringMvcSentinelGlobalBlockExceptionHandler implements BlockExceptionHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {

        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> rsMap = new HashMap<String, Object>() {{
            put("code", "-2");
            put("msg", "SpringMvcSentinelGlobalBlockExceptionHandler处理");
            put("data", "sentinel:"+e.getMessage());
        }};

        response.getWriter().write(JSON.toJSONString(rsMap));
        response.flushBuffer();
    }
}
