package com.zk.nacos.spring.main.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.zk.nacos.spring.main.bean.User;
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
 * @Date: 2021/12/9 17:04
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Getter
@Configuration
public class Config04 implements EnvironmentAware {

    private Environment environment;
    private User user;

    /**
     * 配置NacosProperties来初始化ConfigService
     */
    @NacosInjected(properties = @NacosProperties(serverAddr="${nacos.config.server-addr04}",namespace = "${nacos.config.namespace04}",username = "${nacos.config.username04}",password = "${nacos.config.password04}"))
    private ConfigService configService;

    @Override
    public void setEnvironment(Environment environment){
        this.environment=environment;
    }

    @PostConstruct
    public void initUser() throws NacosException {
        String dataId = environment.getProperty("nacos.config.dataId04");
        String groupId = Optional.ofNullable(environment.getProperty("nacos.config.groupId04")).orElse(Constants.DEFAULT_GROUP);
        log.info("Config04---user-->dataId="+dataId+",groupId="+groupId);
        //获取配置，并添加监听
        String config = this.configService.getConfigAndSignListener(dataId, groupId, 3000, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                Config04.this.user = JSON.parseObject(configInfo, User.class);
                log.info("Config04---user:config is changed! now the user=" + JSON.toJSONString(Config04.this.user));
            }
        });

        //初始化配置
        if (StringUtils.isNoneBlank(config)) {
            this.user = JSON.parseObject(config, User.class);
            log.info("init Config04 user="+JSON.toJSONString(this.user));
        } else {
            log.warn("Config04---user:not find config");
        }
    }


}
