package com.zk.sentinel.nacos.datasource.yml.main.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zk.sentinel.nacos.datasource.yml.main.config.NacosConfigTest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zk
 * @Date: 2022/1/17 16:23
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RequestMapping("/sentinelNacosDataSource")
@RestController
public class SentinelNacosDataSourceTest {

    @Resource
    private NacosConfigTest configTest;


    /**
     * 测试api手动加载规则源
     * @param id
     * @return
     */
    @SentinelResource(value = "testByApi",entryType = EntryType.IN)
    @RequestMapping("/testByApi/{id}")
    public Map<String,Object> testByApi(@PathVariable(value = "id",required = false) int id){

        return new HashMap<String,Object>(){{
                put("code",0);
                put("msg","id="+id);
                put("data","data.name="+configTest.getName());
        }};
    }

    /**
     * 测试自动注册规则源方式
     * @param id
     * @return
     */
    @SentinelResource(value = "testByDynamicRuleDatasource",entryType = EntryType.IN)
    @RequestMapping("/testByDynamicRuleDatasource/{id}")
    public Map<String,Object> testByDynamicRuleDatasource (@PathVariable(value = "id",required = false) int id){

        return new HashMap<String,Object>(){{
            put("code",0);
            put("msg","id="+id);
            put("data","data.name="+configTest.getName());
        }};
    }

}
