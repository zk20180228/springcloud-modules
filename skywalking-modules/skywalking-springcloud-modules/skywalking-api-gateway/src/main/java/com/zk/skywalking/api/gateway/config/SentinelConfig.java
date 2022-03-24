package com.zk.skywalking.api.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: zk
 * @Date: 2022/1/29 10:54
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class SentinelConfig implements EnvironmentAware {

    private Environment environment;


    @NacosInjected(properties=@NacosProperties(serverAddr = "${gateway.nacos.sentinel.rule.serverAddr}",username = "${gateway.nacos.sentinel.rule.username}",password = "${gateway.nacos.sentinel.rule.password}",namespace = "${gateway.nacos.sentinel.rule.namespace}"))
    private ConfigService configService;

    /**
     * 监听规则
     * @throws NacosException
     */
    @PostConstruct
    public void initRules() throws NacosException {

        String dataId=environment.getProperty("gateway.nacos.sentinel.rule.dataId");
        String groupId= Optional.ofNullable(environment.getProperty("gateway.nacos.sentinel.rule.groupId")).orElse(Constants.DEFAULT_GROUP);
        String config = this.configService.getConfigAndSignListener(dataId, groupId, 3000L, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                SentinelConfig.this.setRules(configInfo);
            }
        });
        this.setRules(config);
        //设置发生流控时的callBack
        BlockRequestHandler blockRequestHandler = (exchange, ex) -> ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\n" +
                        "  \"code\":-1,\n" +
                        "  \"msg\":\"流控\",\n" +
                        "  \"data\":null\n" +
                        "}"));
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }


    private void setRules(String configInfo){
        List<Map<String, Object>> list = JSON.parseObject(configInfo, new TypeReference<List<Map<String, Object>>>() {});
        //对规则进行分组
        //省略
        //GatewayApiDefinitionManager.loadApiDefinitions();
        //设置规则
        //省略
        //GatewayRuleManager.loadRules(null);


    }
}
