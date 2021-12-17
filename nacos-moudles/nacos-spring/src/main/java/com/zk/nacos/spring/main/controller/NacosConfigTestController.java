package com.zk.nacos.spring.main.controller;

import com.zk.nacos.spring.main.config.Config01;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zk
 * @Date: 2021/12/8 20:42
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("config")
public class NacosConfigTestController {

    @Resource
    private Config01 config01;


    @RequestMapping("/get")
    public String get(){
        return "config01="+config01.getUserDesc();
    }
}
