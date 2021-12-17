package com.zk.ribbon.consume.main.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.context.annotation.Bean;

/**
 * @Author: zk
 * @Date: 2021/12/14 10:20
 * @Description:
 * @Modified:
 * @version: V1.0
 * 注意当前类被@ComponentScan扫描到，所以是全局的负责均衡规则
 *实验证明，当通过yml方式自定义配置，就不能使用配置类进行全局或者局部配置了，否则配置类会覆盖yml配置方式
 *
 */
//@Configuration
public class GlobalRibbonRuleConfig {


    /**
     * ZoneAvoidanceRule也是ribbon的默认的负责均衡规则
     * @return
     */
    @Bean
    public IRule iRule(){

        return new ZoneAvoidanceRule();
    }
}
