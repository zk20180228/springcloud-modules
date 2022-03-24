package com.zk.sentinel.nacos.datasource.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2022/1/17 16:42
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Data
@Configuration
@RefreshScope
public class NacosConfigTest {


    @Value(value = "${config.name:}")
    private String name;
}
