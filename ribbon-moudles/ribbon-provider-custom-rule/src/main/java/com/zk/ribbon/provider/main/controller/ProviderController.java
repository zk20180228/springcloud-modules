package com.zk.ribbon.provider.main.controller;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/14 10:50
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("provider")
public class ProviderController implements EnvironmentAware {

    private Environment environment;



    @RequestMapping("/customRuleTest/{id}")
    public Map<String,Object> customRuleTest(@PathVariable("id") String id, @RequestHeader(value = "token",required = false) String token){

        return new HashMap<String,Object>(){{
            put("code",0);
            put("msg","id="+id);
            put("data","端口号="+environment.getProperty("server.port")+",applicationName="+environment.getProperty("spring.application.name")+",token="+token);
        }};
    }







    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }
}
