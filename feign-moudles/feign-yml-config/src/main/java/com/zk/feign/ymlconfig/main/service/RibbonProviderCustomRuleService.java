package com.zk.feign.ymlconfig.main.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/15 10:49
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@FeignClient(value = "ribbon-provider-custom-rule", path = "provider")
public interface RibbonProviderCustomRuleService {

    @RequestMapping("/customRuleTest/{id}")
    Map<String,Object> customRuleTest(@PathVariable("id") String id, @RequestHeader(value = "token",required = false) String token);
}
