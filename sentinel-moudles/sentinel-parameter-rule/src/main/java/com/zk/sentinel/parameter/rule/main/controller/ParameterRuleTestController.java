package com.zk.sentinel.parameter.rule.main.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/5 16:54
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RequestMapping("parameterRuleTest")
@RestController
public class ParameterRuleTestController {


    /**
     * 热点参数限流会统计传入参数中的热点参数【值】，并根据配置的限流阈值与模式，对包含热点参数【值】的资源调用进行限流。热
     * 点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效。
     *
     * 1. 热点规则需要使用@SentinelResource("resourceName")注解，否则不生效
     * 2. 参数必须是7种基本数据类型才会生效
     *
     * @param id
     * @return
     */
    @SentinelResource(value = "parameterFlowRuleTest",entryType = EntryType.IN)
    @RequestMapping("/parameterFlowRuleTest/{id}")
    public Map<String,Object> parameterFlowRuleTest(@PathVariable("id") int id){

        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","ok");
            put("data",id);
        }};
    }





    /*********************************分割线************************************************/

    /**
     * 使用jMeter压测找个借口，然后，人工访问systemRuleTest
     * @param id
     * @return
     */
    @RequestMapping("/jMeterTest")
    public Map<String,Object> jMeterTest(int id){

        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","ok");
            put("data",id);
        }};
    }

    /**
     * 基于系统规则的流控
     * 系统规则的流控真针对【入口流量(EntryType.IN)】
     * @param id
     * @return
     */
    @SentinelResource(value = "systemRuleTest",entryType = EntryType.IN)
    @RequestMapping("/systemRuleTest")
    public Map<String,Object> systemRuleTest(int id){

        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","ok");
            put("data",id);
        }};
    }

}
