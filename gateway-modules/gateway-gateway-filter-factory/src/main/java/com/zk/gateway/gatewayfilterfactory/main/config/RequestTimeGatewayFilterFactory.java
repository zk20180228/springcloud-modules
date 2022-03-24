package com.zk.gateway.gatewayfilterfactory.main.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Author: zk
 * @Date: 2021/12/20 13:44
 * @Description:
 * @Modified:
 * @version: V1.0
 *
 * 注意该filter并没有config相关配置
 *
 */
@Slf4j
@Component
public class RequestTimeGatewayFilterFactory implements GatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {
            long startTime=System.currentTimeMillis();
            return chain.filter(exchange).then(Mono.fromRunnable(()->log.info("耗时:{}ms",System.currentTimeMillis()-startTime)));
        };
    }

    @Override
    public Class getConfigClass() {
        return Void.class;
    }
}
