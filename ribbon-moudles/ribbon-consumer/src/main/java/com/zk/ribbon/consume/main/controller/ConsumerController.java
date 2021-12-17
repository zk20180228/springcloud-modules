package com.zk.ribbon.consume.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/13 14:09
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("consumer")
public class ConsumerController {


    @Resource
    private RestTemplate restTemplate;


    @RequestMapping("/ribbonTest")
    public Map<String,Object> ribbonTest(){

        String url ="http://ribbon-provider/provider/ribbonTest/"+ UUID.randomUUID().toString();
        return restTemplate.getForObject(url,Map.class);
    }


}
