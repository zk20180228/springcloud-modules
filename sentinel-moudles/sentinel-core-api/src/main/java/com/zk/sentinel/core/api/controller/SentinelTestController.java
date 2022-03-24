package com.zk.sentinel.core.api.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: zk
 * @Date: 2021/12/22 11:11
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("sentinel")
public class SentinelTestController {


    @RequestMapping("/test/{id}")
    public void test(@PathVariable("id") String id){
        Entry test=null;
        try {
            test = SphU.entry("test");
            log.info("业务操作执行中.........{}",id);
            int i=1/0;
        } catch (BlockException e) {
            e.printStackTrace();
            log.error("被限流了,限流类型:{},限流信息:{}",e.getClass().getSimpleName(), JSON.toJSONString(e));
        }catch (Exception e){
            //记录异常，用于降级统计
            if(test!=null){
                Tracer.traceEntry(e,test);
                log.error("发生了业务异常，该业务异常已被sentinel统计");
            }else{
                log.error("发生业务异常:{}", ExceptionUtils.getMessage(e));
            }
        }finally {
           if(test!=null){
               //完成当前资源条目并在上下文中恢复条目堆栈(可以认为是释放信号量,释放资源，统计信息等)
               test.exit();
           }
        }
    }


    @PostConstruct
    public void initRules(){


//        List<FlowRule> rules=new ArrayList<>();
//        //创建流控规则
//        FlowRule flowRule=new FlowRule();
//        //设置资源
//        flowRule.setResource("test");
//        //设置基于qps的限流
//        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        //qps设置为2
//        flowRule.setCount(2);
//
//        rules.add(flowRule);
//
//        //加载流控规则
//        FlowRuleManager.loadRules(rules);


        List<DegradeRule> degradeRules=new ArrayList<>();
        //创建降级规则
        DegradeRule degradeRule=new DegradeRule();
        //断路器熔断时长，单位s
        degradeRule.setTimeWindow(2);
        //按照分钟级异常数降级
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        degradeRule.setResource("test");
        //统计样本的最小值,如果不够这个数量是不会进行熔断的
        degradeRule.setMinRequestAmount(3);
        //统计窗口大小
        degradeRule.setStatIntervalMs(1000);

        degradeRules.add(degradeRule);

        DegradeRuleManager.loadRules(degradeRules);
    }

}
