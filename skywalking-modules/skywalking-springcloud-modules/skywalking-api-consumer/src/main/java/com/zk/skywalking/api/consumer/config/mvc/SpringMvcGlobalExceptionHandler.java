package com.zk.skywalking.api.consumer.config.mvc;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/27 10:07
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestControllerAdvice
public class SpringMvcGlobalExceptionHandler {


    @ExceptionHandler(BlockException.class)
    public Map<String,Object> blockExcpetionHandler(BlockException e){

        return new HashMap<String,Object>(){{
            put("code",-2);
            put("msg",e instanceof FlowException ?"发生流控":"熔断降级");
            put("data","SpringMvcGlobalExceptionHandler处理");
        }};
    }

    /**
     * 可以添加HttpServletRequest request, HttpServletResponse response这两个参数
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Map<String,Object> exceptionHandler( Throwable e){

        if(e instanceof UndeclaredThrowableException){
            UndeclaredThrowableException undeclaredThrowableException = (UndeclaredThrowableException) e;
            Throwable undeclaredThrowable = undeclaredThrowableException.getUndeclaredThrowable();
            if(undeclaredThrowable instanceof BlockException){
                return this.blockExcpetionHandler((BlockException) undeclaredThrowable);
            }
        }

        //200
//        int status = response.getStatus();
//        System.out.println("status---------------->"+status);

        return new HashMap<String,Object>(){{
            put("code",e instanceof NoHandlerFoundException ?404:-1);
            put("msg",e.getMessage());
            put("data","发生异常-SpringMvcGlobalExceptionHandler处理");
        }};
    }
}
