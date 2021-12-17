package com.zk.nacos.spring.main.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @Author: zk
 * @Date: 2021/12/9 15:06
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Data
@Configuration
@Slf4j
public class Config01 implements EnvironmentAware {


    private Environment environment;

    private String userDesc;

    /**
     * 注入默认的ConfigService
     */
    @NacosInjected
    private ConfigService configService;

    @NacosConfigListener(dataId="${nacos.config.dataId01}",type = ConfigType.PROPERTIES)
    public void config01(String config){
        this.userDesc=config;
        log.info("config01--------配置变化了----config="+config);
    }


    @PostConstruct
    public void init(){
        String dataId01 = environment.getProperty("nacos.config.dataId01");
        log.info("初始化配置dataId01----->"+dataId01);
        try {
            String config = configService.getConfig(dataId01, Constants.DEFAULT_GROUP, 3000);
            this.userDesc=config;
        } catch (Exception e) {
            throw new RuntimeException("初始化Config01失败,原因:"+ ExceptionUtils.getMessage(e));
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }
}
