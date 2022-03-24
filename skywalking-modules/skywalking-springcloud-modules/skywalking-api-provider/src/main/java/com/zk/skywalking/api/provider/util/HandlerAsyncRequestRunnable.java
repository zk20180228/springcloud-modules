package com.zk.skywalking.api.provider.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: zk
 * @Date: 2022/1/26 17:21
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class HandlerAsyncRequestRunnable implements Runnable {

    private AsyncContext asyncContext;
    private Supplier<? extends Map<String,Object>> supplier;

    private HandlerAsyncRequestRunnable(AsyncContext asyncContext, Supplier<? extends Map<String,Object>> supplier){
        this.asyncContext=asyncContext;
        this.supplier=supplier;
    }


    @Override
    public void run() {
        //模拟长请求
        log.info("HandlerAsyncRequestRunnable.run方法执行中，当前线程名:{}",Thread.currentThread().getName());
        Map<String, Object> rs=new HashMap<>();
        try {
            rs=supplier.get();
        }catch (Exception e) {
            e.printStackTrace();
            rs=new HashMap<String, Object>() {{
                put("code", -1);
                put("msg", e.getMessage());
                put("data", null);
            }};
        }finally {
            try {
                ServletResponse response = asyncContext.getResponse();
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(rs));
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            asyncContext.complete();
            log.info("HandlerAsyncRequestRunnable.run方法执行结束，请求上下文已被释放，当前线程名:{}",Thread.currentThread().getName());
        }
    }


    public static class HandlerAsyncRequestRunnableBuilder{

        public static HandlerAsyncRequestRunnable build(Supplier<? extends Map<String,Object>> supplier){
            //获取当前线程的request,这里就不进行null等情况的判断了
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return new HandlerAsyncRequestRunnable(request.startAsync(),supplier);
        }

    }
}
