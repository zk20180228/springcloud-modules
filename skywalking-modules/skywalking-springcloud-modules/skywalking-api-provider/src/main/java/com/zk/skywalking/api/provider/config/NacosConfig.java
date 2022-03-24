package com.zk.skywalking.api.provider.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zk
 * @Date: 2022/1/25 12:07
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Data
@Slf4j
@Configuration(proxyBeanMethods=false)
@RefreshScope
public class NacosConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${sys.tokenName:}")
    private volatile String tokenName;

    /***
     * 注意该方法的线程如果使用Out.this.getxx方式无论怎么执行(即使加了volatile)，当tokenName发生改变，这个线程读到的值仍然是旧值，甚至出现新旧值交替打印(this指向在交替变化)
     * 我在nacos中将qwewqeq修改为kkkkk，然后这个自旋线程一直打印qwewqeq，
     * 实际上tokenName已经变成kkkkk(通过接口访问得到的tokenName为kkkkk)，然后接口访问一次后，这个线程新旧值交替打印。。。。
     * 但是这个线程放到别的类中，不适用内部类的方式访问成员，就没问题。。。
     *
     * 最后使用applicationContext解决
     */
    @PostConstruct
    public void print(){
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    log.info("当前nacos配置为:{}", applicationContext.getBean(NacosConfig.class).getTokenName());
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        thread.setDaemon(true);
        thread.setName("NacosConfig-print");
        thread.start();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
