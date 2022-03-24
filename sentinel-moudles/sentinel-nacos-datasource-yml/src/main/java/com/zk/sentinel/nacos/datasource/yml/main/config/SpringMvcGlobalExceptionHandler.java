package com.zk.sentinel.nacos.datasource.yml.main.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/17 16:22
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestControllerAdvice
public class SpringMvcGlobalExceptionHandler {


    @ExceptionHandler(BlockException.class)
    public Map<String,Object> blockExceptionHandler(BlockException e){

        return new HashMap<String,Object>(){{
            put("code",-2);
            put("msg",e instanceof FlowException ?"发生流控":"熔断降级");
            put("data","SpringMvcGlobalExceptionHandler处理");
        }};
    }

    @ExceptionHandler(Throwable.class)
    public Map<String,Object> throwableHandler(Throwable e){


        if(e instanceof UndeclaredThrowableException){
            UndeclaredThrowableException undeclaredThrowableException = (UndeclaredThrowableException) e;
            Throwable undeclaredThrowable = undeclaredThrowableException.getUndeclaredThrowable();
            if(undeclaredThrowable instanceof BlockException){
                return this.blockExceptionHandler((BlockException) undeclaredThrowable);
            }
        }

        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg",e.getMessage());
            put("data","发生异常-SpringMvcGlobalExceptionHandler处理");
        }};
    }

}
