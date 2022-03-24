package com.zk.sentinel.rule.main.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/24 10:24
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RestControllerAdvice
public class SpringMvcGlobalExceptionHandler {


    /**
     * springmvc整合sentinel(@SentinelResource)时默认的统一异常处理的回调，@SentinelResource中就不用写fallback 或者blockHandler
     * @param e
     * @return
     */
    @ExceptionHandler(BlockException.class)
    public Map<String,Object> blockExceptionHandler(BlockException e){

        return new HashMap<String,Object>(){{
            put("code",-2);
            put("msg",e instanceof FlowException?"发生流控":"发生熔断");
            put("data","经过SpringMvcGlobalExceptionHandler处理");
        }};
    }


    /**
     * 在这里要认知一个概念：检查时异常和非检查时异常
     * @param throwable
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Map<String,Object> throwableHandler(Throwable throwable){
        if(throwable instanceof UndeclaredThrowableException){
            UndeclaredThrowableException undeclaredThrowableException= (UndeclaredThrowableException) throwable;
            Throwable undeclaredThrowable = undeclaredThrowableException.getUndeclaredThrowable();
            if(undeclaredThrowable instanceof BlockException){
                return this.blockExceptionHandler((BlockException) undeclaredThrowable);
            }
        }

        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg",throwable.getMessage());
            put("data",null);
        }};
    }


}
