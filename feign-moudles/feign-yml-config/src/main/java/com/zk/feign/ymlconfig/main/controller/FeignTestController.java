package com.zk.feign.ymlconfig.main.controller;

import com.zk.feign.ymlconfig.main.service.RibbonProviderCustomRuleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/15 10:47
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("feignTest")
public class FeignTestController {


    @Resource
    private RibbonProviderCustomRuleService ribbonProviderCustomRuleService;

    @RequestMapping("/testRpc/{id}")
    public Map<String,Object> testRpc(@PathVariable("id") String id){

        return ribbonProviderCustomRuleService.customRuleTest(id, UUID.randomUUID().toString());
    }
}
