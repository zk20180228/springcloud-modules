package com.zk.sentinel.degraderule.main.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zk
 * @Date: 2021/12/24 17:17
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RequestMapping("/sentinelTest")
@RestController
public class SentinelTestController {


    /**
     * 基于秒级RT的熔断规则
     *
     *
     * @return
     */
    @SentinelResource("degradeByRt")
    @RequestMapping("degradeByRt")
    public Map<String, Object> degradeByRt() {

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getRs("degradeByRt");
    }


    /**
     * 基于异常数的熔断规则
     * 如果不适用@SentinelResource("degradeByExRate")注解,springMvc和sentinel默认整合的模块是无法捕捉到
     * 当前资源的异常的，因为【当前方法异常时被全局异常捕获到进行处理，所以springMvc和sentinel整合的拦截器是无法捕捉到这个异常】
     * springMvc和sentinel是靠一个拦截器来整合的。当前处理器抛出异常，是不会抛给这个拦截器的，因此
     * 如果不加@SentinelResource("degradeByExRate")，对degradeByExRate资源进行熔断，而对springMvc和sentinel整合默认的
     * 资源进行熔断是没有用的【根本原因是无法捕捉到异常】
     * @return
     */
    @SentinelResource("degradeByExRate")
    @RequestMapping("degradeByExRate")
    public Map<String, Object> degradeByExRate() {

        this.codePart();

        return getRs("degradeByExRate");
    }


    /**
     * 基于分钟级异常数的熔断规则
     * 如果不使用@SentinelResource("degradeByExRate")注解,springMvc和sentinel默认整合的模块是无法捕捉到
     * 当前资源的异常的，因为【当前方法异常时被全局异常捕获到进行处理，所以springMvc和sentinel整合的拦截器是无法捕捉到这个异常】
     * springMvc和sentinel是靠一个拦截器来整合的。当前处理器抛出异常，是不会抛给这个拦截器的，因此
     * 如果不加@SentinelResource("degradeByExRate")，对degradeByExRate资源进行熔断，而对springMvc和sentinel整合默认的
     * 资源进行熔断是没有用的【根本原因是无法捕捉到异常】
     * 【因为是分钟级的，所以要想看到降级效果，必须一分钟之后才可以】
     *
     * @return
     */
    @SentinelResource("degradeByExCount")
    @RequestMapping("degradeByExCount")
    public Map<String, Object> degradeByExCount() {

        this.codePart();

        return getRs("degradeByExCount");
    }


    private void codePart(){

        Random random=new Random(System.currentTimeMillis());
        int nextInt = random.nextInt(100);
        if(nextInt%2==0||nextInt%3==0){
            nextInt=nextInt/0;
        }
    }


    private Map<String,Object> getRs(String type){
        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg",type);
            put("data", UUID.randomUUID().toString());
        }};
    }



}
