package com.zk.nacos.springcloud.main.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zk.nacos.springcloud.main.bean.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @Author: zk
 * @Date: 2021/12/9 22:24
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Getter
@Slf4j
@Configuration
public class Config02 implements EnvironmentAware {


    private User user;

    private Environment environment;

    /**
     * 坑:
     *springcloud中要在启动类上加上@EnableNacos(globalProperties = @NacosProperties)才能使用NacosInjected注解
     */
    @NacosInjected(properties = @NacosProperties(serverAddr="${nacos.config.server-addr01}",namespace = "${nacos.config.namespace01}",username = "${nacos.config.username01}",password = "${nacos.config.password01}"))
    private ConfigService configService;


    @PostConstruct
    public void init() throws NacosException {
        String dataId = environment.getProperty("nacos.config.dataId01");
        String groupId = Optional.ofNullable(environment.getProperty("nacos.config.groupId01")).orElse(Constants.DEFAULT_GROUP);
        String config=this.configService.getConfigAndSignListener(dataId, groupId, 3000, new AbstractListener(){
            @Override
            public void receiveConfigInfo(String configInfo) {
                Config02.this.user= JSON.parseObject(configInfo,User.class);
                log.info("Config02---user:config is changed! now the user=" + JSON.toJSONString(Config02.this.user));
            }
        });

        //初始化配置
        if (StringUtils.isNoneBlank(config)) {
            this.user = JSON.parseObject(config, User.class);
            log.info("init Config02 user="+JSON.toJSONString(this.user));
        } else {
            log.warn("Config02---user:not find config");
        }
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }
}
