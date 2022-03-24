package com.zk.sentinel.nacos.datasource.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.property.SentinelProperty;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.*;
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
public class SentinelConfig implements EnvironmentAware {

    private Environment environment;

    @NacosInjected(properties = @NacosProperties(serverAddr="${spring.cloud.nacos.config.server-addr}",namespace ="${spring.cloud.nacos.config.namespace}" ,username ="${spring.cloud.nacos.config.username}" ,password = "${spring.cloud.nacos.config.password}"))
    private ConfigService configService;


    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){

        return new SentinelResourceAspect();
    }


    @PostConstruct
    public void loadRules(){
        //两种做法不能共存,会出现相互覆盖的问题
        this.initRulesByRegister2Property();
        //this.initRulesByManualApiLoad();
        this.printRules();
    }


    /**
     * 通过注册动态规则源加载和监听规则
     */
    public void initRulesByRegister2Property(){
        String serverAddr=this.environment.getProperty("spring.cloud.nacos.config.server-addr");
        String dataId=this.environment.getProperty("sentinel.test02.dataId");
        String groupId=Optional.ofNullable(this.environment.getProperty("sentinel.test02.groupId")).orElse(Constants.DEFAULT_GROUP);
        log.info("Register2Property添加nacos配置监听器,serverAddr={},dataId={},group={}",serverAddr,dataId,groupId);
        Converter<String,List<FlowRule>> converter=jsonRules->{

            log.info("Register2Property检测到流控规则发生变化，正在被自动加载中...,新的流控规则信息为:{}",jsonRules);
            return JSON.parseObject(jsonRules,new TypeReference<List<FlowRule>>(){});
        };
        ReadableDataSource<String,List<FlowRule>> readableDataSource=new NacosDataSource(serverAddr,groupId,dataId,converter);
        SentinelProperty<List<FlowRule>> sentinelProperty = readableDataSource.getProperty();
        FlowRuleManager.register2Property(sentinelProperty);
    }


    /**
     * 通过自定义配置，依靠手动api方式加载规则
     */
    public void initRulesByManualApiLoad(){
        String dataId=environment.getProperty("sentinel.test01.dataId");
        String group= Optional.ofNullable(environment.getProperty("sentinel.test01.groupId")).orElse(Constants.DEFAULT_GROUP);
        log.info("ApiLoad添加nacos配置监听器,dataId={},group={}",dataId,group);
        try {
            String jsonRules = this.configService.getConfigAndSignListener(dataId, group, 3000L, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("检测到自定义流控规则发生变更，开始加载新的流控规则...新的流控规则信息为:{}",configInfo);
                    SentinelConfig.this.parseAndLoadJsonRules(configInfo);
                }
            });
            this.parseAndLoadJsonRules(jsonRules);
        } catch (NacosException e) {
            e.printStackTrace();
            log.warn("监听自定义规则失败，原因:{}", ExceptionUtils.getMessage(e));
        }
    }

    public void parseAndLoadJsonRules(String jsonRules){
        log.info("通过api手动加载规则:{}",jsonRules);
        List<FlowRule> flowRules = JSON.parseObject(jsonRules, new TypeReference<List<FlowRule>>(){});
        FlowRuleManager.loadRules(flowRules);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

    public void printRules(){

       new Thread(()->{
           while(true){
               List<FlowRule> rules = FlowRuleManager.getRules();
               log.info("当前rules={}",JSON.toJSONString(rules));
               try {
                   TimeUnit.SECONDS.sleep(3L);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }).start();
    }
}
