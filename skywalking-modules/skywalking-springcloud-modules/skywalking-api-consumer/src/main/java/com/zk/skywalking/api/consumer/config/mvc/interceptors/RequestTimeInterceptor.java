package com.zk.skywalking.api.consumer.config.mvc.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zk
 * @Date: 2022/1/27 14:50
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
public class RequestTimeInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<Long> THREAD_LOCAL=new ThreadLocal();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        THREAD_LOCAL.set(System.currentTimeMillis());
        log.info("preHandle......执行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle......执行");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            log.info("afterCompletion......执行,共耗时:{}ms",System.currentTimeMillis()-THREAD_LOCAL.get());
        } finally {
            THREAD_LOCAL.remove();
        }
    }
}
