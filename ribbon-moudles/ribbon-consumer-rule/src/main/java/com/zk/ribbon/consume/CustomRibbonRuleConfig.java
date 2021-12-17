package com.zk.ribbon.consume;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zk
 * @Date: 2021/12/14 10:25
 * @Description:
 * @Modified:
 * @version: V1.0
 * 自定义ribbon的rule,注意该配置类应该被springBoot依赖注入进行排除，否则就变成全局的rule了
 *写在入口函数外，就不会被springboot配置类自动扫描了
 *
 */
@Configuration
public class CustomRibbonRuleConfig {


    /**
     *
     * NacosRule是一个根据当前节点的clusterName优先选择clusterName一致的服务提供者
     *
     * @return
     */
    @Bean
    public IRule iRule(){


        return new NacosRule();
    }
}
