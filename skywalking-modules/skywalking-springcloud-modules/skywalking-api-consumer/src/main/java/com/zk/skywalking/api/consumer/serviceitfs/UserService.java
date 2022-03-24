package com.zk.skywalking.api.consumer.serviceitfs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/27 15:32
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@FeignClient(value = "skywalking-api-privider",path = "user")
public interface UserService {


    @RequestMapping("/asyncGet02/{id}")
    Map<String,Object> asyncGet02(@PathVariable("id") int id);

    @RequestMapping("/syncGet/{id}")
    Map<String,Object> syncGet(@PathVariable("id") int id);
}
