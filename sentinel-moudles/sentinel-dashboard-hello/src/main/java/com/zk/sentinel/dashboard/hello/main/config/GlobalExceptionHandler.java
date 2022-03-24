package com.zk.sentinel.dashboard.hello.main.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/23 11:54
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(BlockException.class)
    public Map<String,Object> blockExceptionHandler(BlockException e){

        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg", e instanceof FlowException ?"发生流控":"发生熔断");
            put("data","经过GlobalExceptionHandler处理");
        }};
    }


    @ExceptionHandler(Throwable.class)
    public Map<String,Object> exceptionHandler(Throwable e){

        if(e instanceof UndeclaredThrowableException){
            UndeclaredThrowableException undeclaredThrowableException= (UndeclaredThrowableException) e;
            Throwable throwable = undeclaredThrowableException.getUndeclaredThrowable();
            if(throwable instanceof BlockException){
                return this.blockExceptionHandler((BlockException) throwable);
            }
        }

        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg", ExceptionUtils.getMessage(e));
            put("data",null);
        }};
    }
}
