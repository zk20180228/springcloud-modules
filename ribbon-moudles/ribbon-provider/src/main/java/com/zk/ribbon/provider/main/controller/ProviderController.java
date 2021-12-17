package com.zk.ribbon.provider.main.controller;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/13 14:09
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("provider")
public class ProviderController implements EnvironmentAware {


    private Environment environment;


    @RequestMapping("/ribbonTest/{id}")
    public Map<String,Object> ribbonTest(@PathVariable("id") String id){

        return new HashMap<String,Object>(){{
            put("code",0);
            put("msg","id="+id);
            put("data","端口号="+environment.getProperty("server.port")+",applicationName="+environment.getProperty("spring.application.name"));
        }};
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }
}
