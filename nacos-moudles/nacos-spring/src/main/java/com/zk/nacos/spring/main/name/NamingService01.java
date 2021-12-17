package com.zk.nacos.spring.main.name;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/9 18:15
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
@Data
@Configuration
public class NamingService01 {


    /**
     * 使用默认配置初始化NamingService
     */
    @NacosInjected
    private NamingService namingService;


    @PostConstruct
    public void initPrintNamingServices() throws NacosException {
        List<Instance> allInstances = namingService.getAllInstances(UUID.randomUUID().toString());
        log.info("NamingService01---allInstances="+ JSON.toJSONString(allInstances));
    }


}
