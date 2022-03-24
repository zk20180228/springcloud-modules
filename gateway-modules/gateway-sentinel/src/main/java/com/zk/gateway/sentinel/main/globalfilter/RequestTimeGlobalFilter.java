package com.zk.gateway.sentinel.main.globalfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: zk
 * @Date: 2022/1/19 17:26
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Component
public class RequestTimeGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        return chain.filter(exchange).then(Mono.fromRunnable(()->log.info("请求uri:{},耗时:{}ms",exchange.getRequest().getURI(),System.currentTimeMillis()-startTime)));
    }
}
