package com.zk.gateway.routepredicatefactory.main.config.gateway;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author: zk
 * @Date: 2021/12/20 10:59
 * @Description:
 * @Modified:
 * @version: V1.0
 * 自定义网关断言工厂
 * 1. 必须spring组件 bean
 * 2. 类必须加上RoutePredicateFactory作为结尾
 * 3. 必须继承AbstractRoutePredicateFactory
 * 4. 必须声明静态内部类   声明属性来接收 配置文件中对应的断言的信息
 * 5. 需要结合shortcutFieldOrder进行绑定
 * 6.通过apply进行逻辑判断  true就是匹配成功   false匹配失败
 *
 */
@Component
@Slf4j
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {


    public CheckAuthRoutePredicateFactory() {
        super(CheckAuthRoutePredicateFactory.Config.class);
    }

    /**
     * apply进行断言匹配
     * @param config
     * @return
     */
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {

        return serverWebExchange-> {
            if(StringUtils.isNotBlank(config.getToken())){
               return config.getToken().equals(serverWebExchange.getRequest().getQueryParams().getFirst("token"));
            }
            return true;
        };
    }

    /**
     * 进行参数值和属性的绑定
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {

        return Collections.singletonList("token");
    }

    /**
     * 必须声明静态内部类   声明属性来接收 配置文件中对应的断言的信息
     */
    @Data
    public static class Config{

        private String token;
    }
}
