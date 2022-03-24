package com.zk.skywalking.api.consumer.config.nacos;

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
 * @Date: 2022/1/27 14:48
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Data
@Slf4j
@Configuration
@RefreshScope
public class ConsumerSysConfig implements ApplicationContextAware {

    @Value("${sys.mark:}")
    private String mark;





    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }




    @PostConstruct
    public void print(){
        Thread thread = new Thread(() -> {
           while(true){
               try {
                   log.info("当前mark={}",this.applicationContext.getBean(ConsumerSysConfig.class).getMark());
                   TimeUnit.SECONDS.sleep(3L);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });
        thread.setDaemon(true);
        thread.setName("ConsumerSysConfig-print");
        thread.start();
    }
}
