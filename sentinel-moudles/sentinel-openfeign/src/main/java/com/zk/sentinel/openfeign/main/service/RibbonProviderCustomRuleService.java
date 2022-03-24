package com.zk.sentinel.openfeign.main.service;

import com.zk.sentinel.openfeign.main.service.impl.RibbonProviderCustomRuleServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/5 14:47
 * @Description:
 * @Modified:
 * @version: V1.0
 *
 * 这里如果不写fallback,会抛出BlockException【包括流控和熔断】,controller曾能捕捉到这个异常从而返回自定义降级信息
 * 但【仔细想想，openFeign多用于应用内部间的相互调用,没有必要进行限流,但是熔断是有必要的】
 * 【限流一般都在入口做，内不应用间没必要限流，如果限流会乱掉的】
 * 【内部间的应用之间的调用，熔断措施是有必要的】
 * fallback: 是指远程调用出错时,进行的回调，对于上层，无感知，即如果是下游服务出现了故障，当前服务调用失败，而进行的回调，
 * 但是注意啦，如果这里发生了流控，返回的信息时看不到的，因为上层无法捕捉的这个异常
 * 因此【建议不要写fallback，一路向上抛】
 */
@FeignClient(value = "ribbon-provider-custom-rule"/*,path = "/provider"*/,fallback = RibbonProviderCustomRuleServiceFallbackImpl.class)
public interface RibbonProviderCustomRuleService {


    @RequestMapping("customRuleTest/{id}")
    Map<String,Object> customRuleTest(@PathVariable("id") String id, @RequestHeader(value = "token",required = false) String token);
}
