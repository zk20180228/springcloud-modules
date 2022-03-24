package com.zk.gateway.cors.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @Author: zk
 * @Date: 2021/12/20 17:26
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Configuration
public class GatewayConfig {


    /**
     * 配置文件或代码进行配置二选一
     * @return
     */
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource corsConfigurationSource=new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsWebFilter(corsConfigurationSource);
    }
}
