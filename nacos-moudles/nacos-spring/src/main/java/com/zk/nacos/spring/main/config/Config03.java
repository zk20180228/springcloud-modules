package com.zk.nacos.spring.main.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
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
import java.util.Properties;

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
public class Config03 implements EnvironmentAware {



    private Environment environment;
    private User user;

    /**
     * 使用sdk自己创建nacos
     */
    private ConfigService configService;

    @Override
    public void setEnvironment(Environment environment){
        this.environment=environment;
        //使用sdk初始化configService
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,environment.getProperty("nacos.config.server-addr03"));
        properties.put(PropertyKeyConst.NAMESPACE,Optional.ofNullable(environment.getProperty("nacos.config.namespace03")).orElse(StringUtils.EMPTY));
        properties.put(PropertyKeyConst.USERNAME,Optional.ofNullable(environment.getProperty("nacos.config.username03")).orElse("nacos"));
        properties.put(PropertyKeyConst.PASSWORD,Optional.ofNullable(environment.getProperty("nacos.config.password03")).orElse("nacos"));
        log.info("config03的nacos连接属性为:{}",JSON.toJSONString(properties));
        try {
            this.configService= NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void initUser() throws NacosException {
        String dataId = environment.getProperty("nacos.config.dataId03");
        String groupId = Optional.ofNullable(environment.getProperty("nacos.config.groupId03")).orElse(Constants.DEFAULT_GROUP);
        log.info("Config03---user-->dataId="+dataId+",groupId="+groupId);
        //获取配置，并添加监听
        String config = this.configService.getConfigAndSignListener(dataId, groupId, 3000, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                Config03.this.user = JSON.parseObject(configInfo, User.class);
                log.info("Config03---user:config is changed! now the user=" + JSON.toJSONString(Config03.this.user));
            }
        });

        //初始化配置
        if (StringUtils.isNoneBlank(config)) {
            this.user = JSON.parseObject(config, User.class);
            log.info("init Config03 user="+JSON.toJSONString(this.user));
        } else {
            log.warn("Config03---user:not find config");
        }
    }





}
