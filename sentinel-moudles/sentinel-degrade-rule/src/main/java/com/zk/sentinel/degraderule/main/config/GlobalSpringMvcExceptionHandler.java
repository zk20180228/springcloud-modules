package com.zk.sentinel.degraderule.main.config;

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
 * @Date: 2021/12/24 17:38
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalSpringMvcExceptionHandler {


    @ExceptionHandler(BlockException.class)
    public Map<String,Object> blockExceptionHandler(BlockException e){

        return new HashMap<String,Object>(){{
            put("code",-2);
            put("msg",e instanceof FlowException ?"发生流控":"发生熔断");
            put("data","经过SpringMvcGlobalExceptionHandler处理");
        }};
    }


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
