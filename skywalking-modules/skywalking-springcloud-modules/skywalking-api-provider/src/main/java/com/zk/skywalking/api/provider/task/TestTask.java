package com.zk.skywalking.api.provider.task;

import com.zk.skywalking.api.provider.config.NacosConfig;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zk
 * @Date: 2022/1/26 10:40
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Slf4j
//@Configuration
public class TestTask {

    @Resource
    private NacosConfig nacosConfig;

    @PostConstruct
    public void print(){
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    log.info("当前nacos配置为:{}", nacosConfig.getTokenName());
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        thread.setDaemon(true);
        thread.setName("TestTask-print");
        thread.start();
    }


}
