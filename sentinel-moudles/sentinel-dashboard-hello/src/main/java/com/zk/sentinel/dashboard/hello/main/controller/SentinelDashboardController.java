package com.zk.sentinel.dashboard.hello.main.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/23 11:55
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RequestMapping("sentinelDashboard")
@RestController
public class SentinelDashboardController {


    /**
     * 注意：
     * 1 不用写SentinelResource注解，再sentinel中也可以看到该资源，
     * 说明sentinel对springMvc进行了整合(sentinel 1.7.1 引入了sentinel-spring-webmvc-adapter.jar),
     * 但是发生流控时，不会抛出异常，默认会给客户端返回：Blocked by Sentinel (flow limiting)，需要配置BlockExceptionHandler
     * sentinel和springMvc的整合参考AbstractSentinelInterceptor
     *
     * 2 如果被SentinelResource标记，且对定义的资源名进行流控，会抛异常，会找对应的注解中声明的handler处理，
     * 如果没有handler,会找全局异常处理拦截处理(当然这个只是针对springMvc的处理场景)
     *
     * 3注意：2的情况下是不会找BlockExceptionHandler的，详情参考config下的相关配置说明
     *
     *             ,fallbackClass = {SentinelDashboardControllerFallback.class},fallback = "fallback",
     *             blockHandlerClass = {SentinelDashboardControllerFallback.class},blockHandler = "blockHandler"
     * @param id
     * @return
     */
    @SentinelResource(value="test",entryType = EntryType.IN)
    @RequestMapping("/test/{id}")
    public Map<String,Object> test(@PathVariable("id") String id){

        if(StringUtils.isBlank(id) ||id.contains("2")){
            throw new IllegalArgumentException("id不正确");
        }

//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return new HashMap<String,Object>(){{
            put("data",id);
            put("code",0);
            put("msg", UUID.randomUUID().toString());
        }};
    }



}
