package com.zk.loadbalancer.resttemplate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/14 14:51
 * @Description:
 * @Modified:
 * @version: V1.0
 */

@RequestMapping("/loadBalancer")
@RestController
public class LoadBalancerController {


    @Resource
    private RestTemplate restTemplate;

    /**
     * 测试发现默认不配置是使用轮询的负载均衡规则
     * @return
     */
    @RequestMapping("/customRuleTest")
    public Map<String,Object> customRuleTest(){

        String url ="http://ribbon-provider-custom-rule/provider/customRuleTest/"+ UUID.randomUUID().toString();
        return this.restTemplate.getForObject(url,Map.class);
    }

}
