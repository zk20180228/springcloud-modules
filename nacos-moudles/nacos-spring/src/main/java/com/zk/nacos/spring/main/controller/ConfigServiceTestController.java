package com.zk.nacos.spring.main.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2021/12/9 13:41
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RestController
@RequestMapping("configService")
public class ConfigServiceTestController {

    @NacosInjected
    private ConfigService configService;

    @RequestMapping("/get")
    public Object get() throws NacosException {

        //可用来判断nacos的状态
        String serverStatus = this.configService.getServerStatus();
        Map<String, Object> rs = new HashMap<>();
        //必须有配置项才能生效，如果没有配置项，和nacos-server只有连接，连接断开时，该serverStatus仍为UP
        rs.put("nacosOk", serverStatus);
        //获取某配置的全部内容
        String config = this.configService.getConfig("user01", "user", 3000);
        rs.put("user01-user-config-content", config);
        return rs;
    }

}
