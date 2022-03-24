package com.zk.sentinel.nacos.datasource.yml.main.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zk
 * @Date: 2022/1/17 16:23
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Configuration
public class SentinelConfig {


    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {

        return new SentinelResourceAspect();
    }


    @PostConstruct
    public void logFlowRules() {

        new Thread(() -> {
            while (true) {
                List<FlowRule> rules = FlowRuleManager.getRules();
                log.info("当前rules={}", JSON.toJSONString(rules));
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
