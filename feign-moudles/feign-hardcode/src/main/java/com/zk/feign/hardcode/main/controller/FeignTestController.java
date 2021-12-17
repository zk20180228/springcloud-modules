package com.zk.feign.hardcode.main.controller;

import com.zk.feign.hardcode.main.service.RibbonProviderCustomeRuleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/14 16:41
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RequestMapping("feignTest")
@RestController
public class FeignTestController {

    @Resource
    private RibbonProviderCustomeRuleService ribbonProviderCustomeRuleService;

    @RequestMapping("/testRpc/{id}")
    public Map<String,Object> testRpc(@PathVariable("id") String id){

        return ribbonProviderCustomeRuleService.customRuleTest(id, UUID.randomUUID().toString());
    }

}
