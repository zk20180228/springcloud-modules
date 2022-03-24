package com.zk.sentinel.other.rule.main.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("otherRuleTest")
@RestController
public class OtherRuleTestController {



    /********************************************系统负载流控降级*******************************/

    /**
     * 使用jMeter压测找个借口，然后，人工访问systemRuleTest,查看systemRuleTest是否被流控
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
     * Sentinel 系统自适应限流从整体维度对应用入口流量进行控制，结合应用的 Load、CPU 使用率、总体平均 RT、入口 
     * QPS 和并发线程数等几个维度的监控指标，通过自适应的流控策略，让系统的入口流量和系统的负载达到一个平衡，让
     * 系统尽可能跑在最大吞吐量的同时保证系统整体的稳定性。
     * Load 自适应（仅对 Linux/Unix­like 机器生效）：系统的 load1 作为启发指标，进行自适应系统保护。当系统
     * load1 超过设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护（BBR 阶段）。系统
     * 容量由系统的 maxQps * minRt 估算得出。【设定参考值一般是 CPU cores * 2.5】
     * https://www.cnblogs.com/gentlemanhai/p/8484839.html
     * CPU usage（1.5.0+ 版本）：当系统 CPU 使用率超过阈值即触发系统保护（取值范围 0.0­1.0），比较灵敏。
     * 平均 RT：当单台机器上【所有入口流量的平均 RT 达到阈值】即触发系统保护，单位是毫秒。
     * 并发线程数：当单台机器上所有入口流量的并发线程数达到阈值即触发系统保护。
     * 入口 QPS：当单台机器上所有入口流量的 QPS 达到阈值即触发系统保护。
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


    /**
     * 来源访问控制规则（AuthorityRule）非常简单，主要有以下配置项：
     * resource：资源名，即限流规则的作用对象。
     * limitApp：对应的黑名单/白名单(origin的值)，不同 origin 用 , 分隔，如 appA,appB。
     * strategy：限制模式，AUTHORITY_WHITE 为白名单模式，AUTHORITY_BLACK 为黑名单模式，默认为白名单模式。
     * @param id
     * @return
     */
    @SentinelResource(value = "authorityRuleTest")
    @RequestMapping("/authorityRuleTest")
    public Map<String,Object> authorityRuleTest(int id){

        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","ok");
            put("data",id);
        }};
    }




}
