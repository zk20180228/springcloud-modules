package com.zk.sentinel.dashboard.hello.main.fallbacks;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/23 12:21
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class SentinelDashboardControllerFallback {


    /**
     * 该方法一定要是静态的
     * @param id
     * @param e
     * @return
     */
    public static Map<String,Object> fallback(String id, BlockException e){

        log.warn("发生熔断");
        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg","发生熔断");
            put("data",id);
        }};
    }

    /**
     * 该方法一定要是静态的
     * @param id
     * @param e
     * @return
     */
    public static Map<String,Object> blockHandler(String id, BlockException e){
        log.warn("发生流控");
        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg", "发生流控");
            put("data",id);
        }};
    }


}
