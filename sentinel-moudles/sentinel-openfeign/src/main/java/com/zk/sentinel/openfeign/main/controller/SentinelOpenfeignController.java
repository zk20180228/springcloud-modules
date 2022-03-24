package com.zk.sentinel.openfeign.main.controller;

import com.zk.sentinel.openfeign.main.service.RibbonProviderCustomRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/5 13:20
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RequestMapping("sentinelOpenfeign")
@RestController
public class SentinelOpenfeignController {


    @Resource
    private RibbonProviderCustomRuleService ribbonProviderCustomRuleService;


    @RequestMapping("/sentinelOpenfeignTest/{id}")
    public Map<String,Object> sentinelOpenfeignTest(@PathVariable("id") String id,@RequestHeader(value = "token",required = false) String token){

        return ribbonProviderCustomRuleService.customRuleTest(id,token);
    }
}
