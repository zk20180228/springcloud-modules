package com.zk.skywalking.helloworld.main.modules.user.controller;

import com.zk.skywalking.helloworld.main.serviceitfs.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/24 11:54
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RequestMapping("/user")
@RestController
public class UserController {


    @Resource
    private UserService userService;

    @RequestMapping("/test/{id}")
    public Map<String,Object>  test(@PathVariable("id") int id){


        return new HashMap<String,Object>(){{
                put("code",0);
                put("msg","success");
                put("data", userService.getById(id));
        }};
    }


    @RequestMapping("/testError")
    public Map<String,Object>  testError(){


        return new HashMap<String,Object>(){{
            put("code",-1);
            put("msg","success");
            put("data", UserController.this.th());

        }};
    }

    public Object th(){
        throw new RuntimeException("出错了...");
    }
}
