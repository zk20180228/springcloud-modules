package com.zk.nacos.springcloud.main.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2021/12/9 21:34
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Data
@Slf4j
@Configuration
@RefreshScope
public class Config01 {

    /**
     * 从默认的配置项中读取数据(实验证明，能够自动刷新)
     */
    @Value(value = "${userDesc:}")
    private String userDesc;

}
