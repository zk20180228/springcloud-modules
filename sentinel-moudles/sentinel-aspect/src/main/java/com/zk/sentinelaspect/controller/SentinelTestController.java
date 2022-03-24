package com.zk.sentinelaspect.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/22 13:44
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RequestMapping("sentinel")
@RestController
public class SentinelTestController {


    /**
     * 在这里说一下对EntryType的理解
     * EntryType.IN:代表入口流量
     * EntryType.OUT:代表出口流量(实际上理解为内部流量更好理解)
     * 系统负载导致流控时，会对入口流量进行流控，因此对外提供的接口定义为入口流量，可以在系统负载规则触发时进行对AP的保护
     * EntryType.OUT可以理解为对内部资源调用的保护,系统负载导致流控时也不会影响这类资源
     *
     *
     * 注意：如果没有fallback，blockHandler，该异常会继续向上抛，但因为当前是代理对象,且没声明抛出的异常，因此会抛出UndeclaredThrowableException
     * ,fallback = "fallback",fallbackClass = {SentinelTestFallback.class},blockHandler = "blockHandler",blockHandlerClass = {SentinelTestFallback.class}
     * @param id
     * @return
     */
    @SentinelResource(value="test",entryType = EntryType.IN)
    @RequestMapping("/test/{id}")
    public Map<String,Object> test(@PathVariable("id") String id)throws BlockException {



        if(id.contains("2")){
            //这里有一个非常重要的概念
            /**
             * Exception 以及Exception的子类(Runtime和其子类不算)是检查类型异常，必须处理要么声明抛出,要么必须try...catch
             * RuntimeException和Error是非检查异常，可以直接抛出，而不需要声明
             * 以下三句话编译都会通过
             */
            //throw new IllegalArgumentException();
            throw  new Error();
            //throw new RuntimeException();
        }

        return new HashMap<String,Object>(){{
            put("msg","success");
            put("code",0);
            put("data",id);
        }};
    }


    @PostConstruct
    public void initRules(){

        List<FlowRule> rules=new ArrayList<>();
        //创建流控规则
        FlowRule flowRule=new FlowRule();
        //设置资源
        flowRule.setResource("test");
        //设置基于qps的限流
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //qps设置为2
        flowRule.setCount(2);

        rules.add(flowRule);

        //加载流控规则
        FlowRuleManager.loadRules(rules);


        List<DegradeRule> degradeRules=new ArrayList<>();
        //创建降级规则
        DegradeRule degradeRule=new DegradeRule();
        //断路器熔断时长，单位s
        degradeRule.setTimeWindow(5);
        //按照分钟级异常数降级
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        degradeRule.setResource("test");
        //统计样本的最小值,如果不够这个数量是不会进行熔断的
        degradeRule.setMinRequestAmount(2);
        //统计窗口大小
        degradeRule.setStatIntervalMs(1000);

        degradeRules.add(degradeRule);

        DegradeRuleManager.loadRules(degradeRules);
    }




}
