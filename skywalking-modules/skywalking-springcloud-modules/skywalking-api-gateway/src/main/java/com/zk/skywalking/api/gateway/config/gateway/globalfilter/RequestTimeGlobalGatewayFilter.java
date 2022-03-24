package com.zk.skywalking.api.gateway.config.gateway.globalfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: zk
 * @Date: 2022/1/29 10:34
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Component
public class RequestTimeGlobalGatewayFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        return chain.filter(exchange).then(Mono.fromRunnable(()->log.info("{}耗时:{}ms",exchange.getRequest().getURI(),System.currentTimeMillis()-start)));
    }
}
