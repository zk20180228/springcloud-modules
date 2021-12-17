package com.zk.nacos.springcloud.main.controller;

import com.zk.nacos.springcloud.main.config.Config01;
import com.zk.nacos.springcloud.main.config.Config02;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: zk
 * @Date: 2021/12/9 21:35
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("nacosTest")
public class NacosTestController {

    @Resource
    private Config01 config01;

    @Resource
    private Config02 config02;


    @RequestMapping("/get01")
    public Object get01(){

        return config01.getUserDesc();
    }


    @RequestMapping("/get02")
    public Object get02(){

        return config02.getUser();
    }
}
