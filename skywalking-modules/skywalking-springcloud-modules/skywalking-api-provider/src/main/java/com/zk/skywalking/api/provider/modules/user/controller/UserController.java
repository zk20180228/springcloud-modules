package com.zk.skywalking.api.provider.modules.user.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zk.skywalking.api.provider.config.NacosConfig;
import com.zk.skywalking.api.provider.serviceitfs.UserService;
import com.zk.skywalking.api.provider.util.HandlerAsyncRequestRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zk
 * @Date: 2022/1/25 12:08
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RequestMapping("user")
@RestController
public class UserController {


    @Resource
    private NacosConfig nacosConfig;

    @Resource
    private UserService userService;

    @Resource
    private ExecutorService common;

    /**
     * 这种方式，业务处理线程就是请求线程，可以认为是同步的
     * @param id
     * @return
     */
    @SentinelResource(value = "/user/syncGet/{id}",entryType = EntryType.IN)
    @RequestMapping("/syncGet/{id}")
    public Map<String,Object> syncGet(@PathVariable("id") int id){

        try {
            //模拟长请求
            log.info("syncGet方法执行中，当前线程名:{}",Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, Object> rs = new HashMap<String, Object>() {{
            put("code", 0);
            put("msg", String.format("token=%s", nacosConfig.getTokenName()));
            put("data", userService.getById(id));
        }};

        return rs;
    }


    /**
     * 自己封装的异步模型
     * 1 更高级的做法是：使用动态代理，把整个controller方法全部做成异步模型
     * 2 要注意和@SentinelResource进行整和，这里就没进行整合
     * 3 当然了，自定义的一些springmvc的拦截器的工作并不是预期值，如请求时长的统计，
     * 因为是异步线程模型，因此真正统计的请求时长并不准确
     * 4 可以进行泛型话封装
     * 5 线程安全性
     * 6 其它等等。。。
     * 【注意：这种方式可以不同接口可以自定义线程池，
     * 但是springmvc的本身的组件可能不会被使用，如果是纯接口，
     * 没有视图，还是推荐这种方式的，如果有视图，需要看源码修改】
     *
     * @param id
     */
    @SentinelResource(value = "/user/asyncGet01/{id}",entryType = EntryType.IN)
    @RequestMapping("/asyncGet01/{id}")
    public void asyncGet01(@PathVariable("id") int id){
        log.info("asyncGet01-->方法执行开始，当前线程名:{}",Thread.currentThread().getName());
        common.submit(HandlerAsyncRequestRunnable.HandlerAsyncRequestRunnableBuilder.build(()->{
            //以下都是业务处理
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //指定HashMap初始化大小为2的指数倍
            return  new HashMap<String, Object>(4) {{
                put("code", 0);
                put("msg", String.format("token=%s", nacosConfig.getTokenName()));
                put("data", userService.getById(id));
            }};
        }));
        log.info("asyncGet01-->方法执行结束，当前线程名:{}",Thread.currentThread().getName());
    }


    /**
     * 1 这个方法也可以异步执行而且springboot是支持的
     * 2 和asyncGet01还是有去区别的.asyncGet01的拦截器的执行和请求线程是同一个线程，
     * 但是asyncGet02拦截器的执行和请求线程不是同一个线程，asyncGet02拦截器是在callable的call方法执行后才执行，
     * 而且call是的线程是自定义异步支持的线程池，但是asyncGet02拦截器仍然是服务器的请求线程执行的
     * 3 asyncGet02拦截器会执行2次，第一次只会执行preHandle(在call方法之前)，第二次在【客户端接收到请求后执行】，preHandle，postHandle，afterCompletion都会执行
     *
     * springmvc异步模型的流程如下(这也解释了拦截器为什么是在有结果后再次被执行一次):
     * 控制器返回Callable
     * Spring异步处理，将Callable 提交到 TaskExecutor 使用一个隔离的线程进行执行
     * DispatcherServlet和所有的Filter退出web容器的线程，但是response 保持打开状态；
     * Callable返回结果，SpringMVC将请求重新派发给容器，恢复之前的处理；
     * 根据Callable返回的结果。SpringMVC继续进行视图渲染流程等（从收请求-视图渲染）。
     * 【这种方式的缺点是不同api无法自定义不同的线程池，如果想自定义线线程池，可能需要修改源码】
     *
     * @param id
     * @return
     */
    @SentinelResource(value = "/user/asyncGet02/{id}",entryType = EntryType.IN)
    @RequestMapping("/asyncGet02/{id}")
    public Callable<Map<String,Object>> asyncGet02(@PathVariable("id") int id){

        log.info("asyncGet02-->方法执行开始，当前线程名:{}",Thread.currentThread().getName());
        //返回一个Callable
        Callable<Map<String,Object>> callable=new Callable<Map<String, Object>>() {
            @Override
            public Map<String, Object> call() throws Exception {
                try {
                    //模拟长请求
                    log.info("asyncGet02方法执行中，当前线程名:{}",Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return new HashMap<String, Object>() {{
                    put("code", 0);
                    put("msg", String.format("token=%s", nacosConfig.getTokenName()));
                    put("data", userService.getById(id));
                }};
            }
        };
        log.info("asyncGet02-->方法执行结束，当前线程名:{}",Thread.currentThread().getName());

        return callable;
    }


//    @Trace
//    public void testTrace(){
//
//        log.info("");
//    }

}
