package com.zk.skywalking.api.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zk
 * @Date: 2022/1/25 16:11
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class ApiRequestTimeInterceptor implements HandlerInterceptor {

    public static final ThreadLocal<Long> THREAD_LOCAL=new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle....{}",Thread.currentThread().getName());
        THREAD_LOCAL.set(System.currentTimeMillis());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle....{}",Thread.currentThread().getName());
        //针对不存在的资源的处理，其他方法都尝试过，不太行
//        if(response.getStatus()==404||request.getRequestURI().equals("/error")){
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().write(JSON.toJSONString(new HashMap<String, Object>() {{
//                put("code", response.getStatus());
//                put("msg", "请稍后重试");
//                put("data", "ApiRequestTimeInterceptor.afterCompletion处理");
//            }}));
//
//            response.flushBuffer();
//        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            log.info("afterCompletion...{}",Thread.currentThread().getName());
        } finally {
            log.info("url={}，本次请求耗时:{}ms",request.getRequestURL(),System.currentTimeMillis()-THREAD_LOCAL.get());
            THREAD_LOCAL.remove();
        }

    }
}
