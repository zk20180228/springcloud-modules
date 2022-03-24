package com.zk.gateway.sentinel.main.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: zk
 * @Date: 2022/1/19 17:06
 * @Description:
 * @Modified:
 * @version: V1.0
 * <p>
 * 配置发生限流时，全局的处理器
 */
@Slf4j
@Configuration
public class SentinelGatewayConfig {

    @PostConstruct
    public void init() {

        this.initBlockRequestHandler();

        this.initCustomizedApis();
    }


    public void initBlockRequestHandler(){
        BlockRequestHandler blockRequestHandler = (exchange, ex) -> ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\n" +
                        "  \"code\":-1,\n" +
                        "  \"msg\":\"流控\",\n" +
                        "  \"data\":null\n" +
                        "}"));

        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }


    /**
     * 这个可以和nacos相结合
     * api分组名和路由id就是资源名
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition apiDefinition = new ApiDefinition("some_customized_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    //精准匹配
//                    add(new ApiPathPredicateItem().setPattern("/gateway-sentinel/provider/customRuleTest/5"));
                    //前缀匹配【这个前缀客户端访问的path的前缀，不是网关去掉前缀后的path前缀】
                    add(new ApiPathPredicateItem().setPattern("/gateway-sentinel/provider/customRuleTest/**")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(apiDefinition);

        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }


    /**
     * 只是个示例
     */
    public void initGatewayRules(){

        GatewayRuleManager.loadRules(null);
    }

}
