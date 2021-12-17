package com.zk.ribbon.consume.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/13 14:09
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("consumer")
public class ConsumerController {


    @Resource
    private RestTemplate restTemplate;


    /**
     * 测试集成ribbon，并使用默认的负载均衡策略
     * @return
     */
    @RequestMapping("/ribbonTest")
    public Map<String,Object> ribbonTest(){

        String url ="http://ribbon-provider/provider/ribbonTest/"+ UUID.randomUUID().toString();
        return restTemplate.getForObject(url,Map.class);
    }


    /**
     * 测试集成ribbon，并使用自定义的负责均衡策略(nacos的同一集群内的随机)
     * @return
     */
    @RequestMapping("/customRuleTest")
    public Map<String,Object> customRuleTest(){

        String url ="http://ribbon-provider-custom-rule/provider/customRuleTest/"+ UUID.randomUUID().toString();
        return restTemplate.getForObject(url,Map.class);
    }


}
