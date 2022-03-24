package com.zk.sentinel.rule.main.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/24 10:29
 * @Description:
 * @Modified:
 * @version: V1.0
 *
 * 修改speingmvc与sentinel整合时默认的BlockExceptionHandler为SentinelSpringMvcBlockExceptionHandler
 *
 */
@Slf4j
@Component
public class SentinelSpringMvcBlockExceptionHandler implements BlockExceptionHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("code",-3);
        map.put("msg",e.getMessage());
        map.put("data","经过SentinelSpringMvcBlockExceptionHandler处理");

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(map));
        response.flushBuffer();
    }
}
