package com.zk.sentinel.openfeign.main.service.impl;

import com.zk.sentinel.openfeign.main.service.RibbonProviderCustomRuleService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/5 16:02
 * @Description:
 * @Modified:
 * @version: V1.0
 *
 * 【要使用@Component来修饰哦】
 */
@Component
public class RibbonProviderCustomRuleServiceFallbackImpl implements RibbonProviderCustomRuleService {

    @Override
    public Map<String, Object> customRuleTest(String id, String token) {
        return new HashMap<String,Object>(){{
            put("code","-1");
            put("msg","业务繁忙，亲稍后重试");
            put("data",null);
        }};
    }
}
