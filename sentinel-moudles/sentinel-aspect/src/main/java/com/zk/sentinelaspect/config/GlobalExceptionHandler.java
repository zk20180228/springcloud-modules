package com.zk.sentinelaspect.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/22 17:25
 * @Description:
 * @Modified:
 * @version: V1.0
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 代理方法上要声明抛出BlockException异常,才能被直接捕捉
     * 原因：代理方法未声明的检查异常，因为要必须处理(不能直接抛出,因为被代理方法没有声明),此时只能包装成
     * RuntimeException或者Error,非检查时异常进行抛出
     * @param e
     * @return
     */
    @ExceptionHandler(BlockException.class)
    public Map<String,Object> blockExceptionHandler(BlockException e){
        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg",e instanceof FlowException ?"流控降级":"熔断降级");
            put("data",null);
        }};
    }

    /**
     * https://blog.csdn.net/ywlmsm1224811/article/details/92618062
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> exceptionHandler(Exception e){

        if(e instanceof UndeclaredThrowableException){
            UndeclaredThrowableException ex= (UndeclaredThrowableException) e;
            Throwable undeclaredThrowable = ex.getUndeclaredThrowable();
            //如果代理方法上没有抛出BlockException，那么代理方法会抛出一个UndeclaredThrowableException
            if(undeclaredThrowable instanceof BlockException){
                return this.blockExceptionHandler((BlockException) undeclaredThrowable);
            }
            //other...
        }

        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg","发生错误,请稍后重试");
            put("data",null);
        }};
    }
}
