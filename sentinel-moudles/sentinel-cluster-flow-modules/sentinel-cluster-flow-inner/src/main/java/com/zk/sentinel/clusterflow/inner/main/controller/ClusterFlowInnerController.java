package com.zk.sentinel.clusterflow.inner.main.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2022/1/11 16:11
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RequestMapping("/clusterFlowInner")
@RestController
public class ClusterFlowInnerController {



    @SentinelResource("cluster_flow_inner_test01")
    @RequestMapping("/test01/{id}")
    public Map<String,Object> test01(@PathVariable("id") int id){


        return new HashMap<String,Object>(){{
                put("code","0");
                put("msg","id="+id);
                put("data", UUID.randomUUID().toString());
        }};
    }
}
