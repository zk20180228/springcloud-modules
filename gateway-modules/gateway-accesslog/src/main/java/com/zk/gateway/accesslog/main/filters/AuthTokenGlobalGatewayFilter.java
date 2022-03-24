package com.zk.gateway.accesslog.main.filters;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @Author: zk
 * @Date: 2021/12/20 16:12
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Component
public class AuthTokenGlobalGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getQueryParams().getFirst("token");
        if(StringUtils.isNotBlank(token)&&token.equals("12332123")){
            log.info("AuthTokenGlobalGatewayFilter------>放行");
            return chain.filter(exchange);
        }else{
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.writeWith(Mono.fromSupplier(()->{
                DataBufferFactory bufferFactory = response.bufferFactory();
                //返回响应结果
                return bufferFactory.wrap(JSON.toJSONBytes(new HashMap<String, Object>() {{
                    put("code",HttpStatus.UNAUTHORIZED.value());
                    put("msg", HttpStatus.UNAUTHORIZED.getReasonPhrase());
                    put("data",null);
                }}));
            }));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
