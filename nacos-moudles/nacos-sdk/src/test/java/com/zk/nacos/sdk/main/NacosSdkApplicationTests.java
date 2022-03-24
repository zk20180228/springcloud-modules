package com.zk.nacos.sdk.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class NacosSdkApplicationTests {

    @Test
    void contextLoads() {
    }



    @Test
    public void testGetConfig() throws Exception {

        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"2ac4fbee-e911-4f03-b076-125d4e6cea85");
        ConfigService configService = NacosFactory.createConfigService(properties);
        if("UP".equals(configService.getServerStatus())){
            String configInfo = configService.getConfigAndSignListener("user", "DEFAULT_GROUP", 3000L, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("配置发生变化了,结果为:{}", configInfo);
                }
            });

            log.info("当前配置内容为:{}",configInfo);
            //阻塞当前线程,不让其立马退出
            Thread.sleep(100000L);
            configService.shutDown();
        }
    }

    @Test
    public void testSetConfig() throws Exception{
        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"2ac4fbee-e911-4f03-b076-125d4e6cea85");
        ConfigService configService = NacosFactory.createConfigService(properties);
        if("UP".equals(configService.getServerStatus())){
            String configInfo = configService.getConfigAndSignListener("user", "DEFAULT_GROUP", 3000L, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("配置发生变化了,结果为:{}", configInfo);
                }
            });

            log.info("当前配置内容为:{}",configInfo);
            TimeUnit.SECONDS.sleep(6);
            boolean pubOk = configService.publishConfig("user", "DEFAULT_GROUP", "aa=bb\ncc=dd\nhh=ff", ConfigType.PROPERTIES.getType());
            log.info("发布配置信息:{}",pubOk?"成功":"失败");
            Thread.sleep(100000L);
            //阻塞当前线程,不让其立马退出
            configService.shutDown();
        }
    }


    @Test
    public void testRemoveConfig()throws Exception{

        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"d9c3b807-dc84-45d1-96ce-d166bc309ae1");
        ConfigService configService = ConfigFactory.createConfigService(properties);
        if("UP".equals(configService.getServerStatus())){
            String configInfo = configService.getConfigAndSignListener("config01", "DEFAULT_GROUP", 3000L, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("配置发生变化了,结果为:{}", configInfo);
                }
            });

            log.info("当前配置内容为:{}",configInfo);
            TimeUnit.SECONDS.sleep(6);
            boolean pubOk = configService.removeConfig("config01", "DEFAULT_GROUP");
            log.info("删除配置信息:{}",pubOk?"成功":"失败");
            Thread.sleep(100000L);
            //阻塞当前线程,不让其立马退出
            configService.shutDown();
        }

    }




    @Test
    public void testRegisterInstance()throws Exception{
        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"d9c3b807-dc84-45d1-96ce-d166bc309ae1");
        NamingService namingService = NamingFactory.createNamingService(properties);
        if("UP".equals(namingService.getServerStatus())){
            namingService.registerInstance("service01","127.0.0.1",8080);
            namingService.registerInstance("service02","GROUP","192.168.241.13",80,"service02");
            Instance instance=new Instance();
            instance.setClusterName("service03");
            instance.setEnabled(false);
            instance.setEphemeral(false);
            instance.setInstanceId(UUID.randomUUID().toString());
            instance.setHealthy(true);
            instance.setIp("127.10.0.1");
            instance.setPort(80);
            instance.setWeight(20);
            instance.setServiceName("service03");
            Map<String, String> metaData = new HashMap<>();
            metaData.put("aa","bb");
            metaData.put("cc","dd");
            instance.setMetadata(metaData);

            //发现2.0.3的api中并没有instance.setService和instance.setCluster
            namingService.registerInstance("service03",instance);

            TimeUnit.SECONDS.sleep(30L);

            //关闭连接
            namingService.shutDown();
        }

    }


    @Test
    public void testGetAllInstances()throws Exception{

        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"d9c3b807-dc84-45d1-96ce-d166bc309ae1");
        NamingService namingService = NamingFactory.createNamingService(properties);

        List<Instance> allInstances = namingService.getAllInstances("service03");
        log.info(JSON.toJSONString(allInstances));

        namingService.shutDown();
    }



    @Test
    public void testSelectOneHealthyInstance()throws Exception{

        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"d9c3b807-dc84-45d1-96ce-d166bc309ae1");
        NamingService namingService = NamingFactory.createNamingService(properties);

        //2.0.3版本在测试时,这行报错了java.lang.ArrayIndexOutOfBoundsException: -1
        Instance instance = namingService.selectOneHealthyInstance("service03");
        log.info(JSON.toJSONString(instance));

        namingService.shutDown();
    }


    @Test
    public void testSubscribe()throws Exception{
        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"d9c3b807-dc84-45d1-96ce-d166bc309ae1");
        NamingService namingService = NamingFactory.createNamingService(properties);

        namingService.subscribe("service03",e->{
            if(e instanceof NamingEvent){
                NamingEvent event= (NamingEvent) e;
                log.info(event.getServiceName()+":");
                //这里仅仅返回的是健康的实例集合
                log.info(JSON.toJSONString(event.getInstances()));
            }
        });


        while (true){
            Thread.sleep(10000L);
        }
    }


    @Test
    public void testDeregisterInstance()throws Exception{
        Properties properties=new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR,"192.168.241.13:8848");
        properties.put(PropertyKeyConst.USERNAME,"nacos");
        properties.put(PropertyKeyConst.PASSWORD,"nacos");
        properties.put(PropertyKeyConst.NAMESPACE,"d9c3b807-dc84-45d1-96ce-d166bc309ae1");
        NamingService namingService = NamingFactory.createNamingService(properties);
        //注销实例
        namingService.deregisterInstance("service03","127.10.0.1",80);

        namingService.shutDown();
    }






}
