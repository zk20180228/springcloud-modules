package com.zk.sentinelaspect.common;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/22 17:10
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class SentinelTestFallback {



    public static Map<String,Object> fallback(String id, BlockException be){
        log.error("sentinel生效啦,id-->{},be--->{}",id, ExceptionUtils.getMessage(be));

        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg",be instanceof FlowException ?"流控降级":"熔断降级");
            put("data",null);
        }};
    }

    public static Map<String,Object> blockHandler(String id, BlockException be){

        return SentinelTestFallback.fallback(id,be);
    }



}
