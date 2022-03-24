package com.zk.gateway.globalfilter.config.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: zk
 * @Date: 2021/12/20 16:38
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Component
public class RequestTimeStatisGlobalGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();
        return chain.filter(exchange)
                    .then(Mono.fromRunnable(()->log.info("{}耗时:{}",exchange.getRequest().getURI(),System.currentTimeMillis()-startTime)));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
