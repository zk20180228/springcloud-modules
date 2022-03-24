package com.zk.gateway.gatewayfilterfactory.main.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: zk
 * @Date: 2021/12/20 14:01
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Component
public class CheckAuthGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {

        return (exchange, chain) -> {
            log.info("认证信息{}---->{}",config.getName(),config.getValue())    ;
            return chain.filter(exchange);
        };
    }
}
