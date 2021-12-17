package com.zk.gateway.globalexception.main.config.exception;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @Author: zk
 * @Date: 2021/12/16 14:47
 * @Description:
 * @Modified:
 * @version: V1.0
 *
 * gateway全局异常处理方法之一
 *
 *
 */
@Slf4j
@Order(-1)
@Configuration
public class GatewayGlobalExceptionConfig implements ErrorWebExceptionHandler {


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if(response.isCommitted()){
            return Mono.error(ex);
        }
        HttpHeaders headers = response.getHeaders();
        //设置响应类型
        headers.setContentType(MediaType.APPLICATION_JSON);
        //返回与特定异常一致的响应码
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                //返回响应结果
                return bufferFactory.wrap(JSON.toJSONBytes(new HashMap<String, Object>() {{
                    if(ex instanceof ResponseStatusException){
                        put("code",((ResponseStatusException) ex).getStatus().value());
                    }else{
                        put("code",500);
                    }
                    put("msg", ExceptionUtils.getMessage(ex));
                    put("data",null);
                }}));
            }
            catch (Exception e) {
                log.error("Error writing response", ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
