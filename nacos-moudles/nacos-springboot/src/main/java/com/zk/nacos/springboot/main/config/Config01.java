package com.zk.nacos.springboot.main.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
public class Config01 {

    /**
     * 从默认的配置项中读取数据(实验证明，能够自动刷新)
     */
    @NacosValue(value = "${userDesc:''}",autoRefreshed = true)
    private String userDesc;

}
