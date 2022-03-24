package com.zk.skywalking.helloworld.main.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/24 14:17
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestControllerAdvice
public class SpringMvcGlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public Map<String,Object> throwableHandler(Throwable e){
        e.printStackTrace();

        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg",String.format("error=%s",e.getMessage()));
            put("data",null );
        }};
    }
}
