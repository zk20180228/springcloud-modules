package com.zk.sentinel.clusterflow.inner.main.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/11 15:48
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestControllerAdvice
public class SpringMvcGlobalExceptionHandler {

    @ExceptionHandler(BlockException.class)
    public Map<String, Object> blockHandler(BlockException be) {

        return new HashMap<String, Object>() {{
            put("code", "-2");
            put("msg", "SpringMvcGlobalExceptionHandler处理");
            put("data", "sentinel:"+be.getMessage());
        }};
    }


    @ExceptionHandler(Throwable.class)
    public Map<String, Object> throwableHaandler(Throwable twe) {

        if(twe instanceof UndeclaredThrowableException){
            UndeclaredThrowableException undeclaredThrowableException= (UndeclaredThrowableException) twe;
            Throwable undeclaredThrowable = undeclaredThrowableException.getUndeclaredThrowable();
            if(undeclaredThrowable instanceof BlockException){
                return this.blockHandler((BlockException) undeclaredThrowable);
            }
        }

        return new HashMap<String, Object>() {{
            put("code", "-1");
            put("msg", "SpringMvcGlobalExceptionHandler处理");
            put("data", "系统异常:"+twe.getMessage());
        }};
    }
}
