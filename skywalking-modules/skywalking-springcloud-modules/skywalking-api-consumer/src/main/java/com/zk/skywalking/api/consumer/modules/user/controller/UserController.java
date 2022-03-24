package com.zk.skywalking.api.consumer.modules.user.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zk.skywalking.api.consumer.serviceitfs.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @Author: zk
 * @Date: 2022/1/27 15:31
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {


    @Resource
    private UserService userService;

    @SentinelResource(value = "/user/syncGet/{id}",entryType = EntryType.IN)
    @RequestMapping("/syncGet/{id}")
    public Map<String, Object> syncGet(@PathVariable("id") int id) {
        return userService.syncGet(id);
    }

    @SentinelResource(value = "/user/asyncGet/{id}",entryType = EntryType.IN)
    @RequestMapping("/asyncGet/{id}")
    public Callable<Map<String, Object>> asyncGet(@PathVariable("id") int id) {
        log.info("asyncGet----->当前线程名:{}",Thread.currentThread().getName());
        return () -> {
            log.info("asyncGet--call--->当前线程名:{}",Thread.currentThread().getName());
            return userService.asyncGet02(id);
        };
    }

}
