package com.zk.sentinel.rule.main.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.aop.framework.AopContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zk
 * @Date: 2021/12/24 10:20
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@RequestMapping("sentinelTest")
@RestController
public class SentinelTestController {


    /**
     * 基于秒级的qps流控
     * @param id
     * @return
     */
    @SentinelResource(value = "test",entryType = EntryType.IN)
    @RequestMapping("/test/{id}")
    public Map<String, Object> test(@PathVariable("id") String id) {


        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","ok");
            put("data",id);
        }};
    }


    /**
     *当两个资源之间具有资源争抢或者依赖关系的时候，这两个资源便具有了关联。比如对数据库同一个字段的读操作和写
     * 操作存在争抢，读的速度过高会影响写得速度，写的速度过高会影响读的速度。如果放任读写操作争抢资源，则争抢本
     * 身带来的开销会降低整体的吞吐量。可使用关联限流来避免具有关联关系的资源之间过度的争抢，举例来说，read_db 和
     * write_db 这两个资源分别代表数据库读写，我们可以给 read_db 设置限流规则来达到写优先的目的：设置 strategy 为
     * RuleConstant.STRATEGY_RELATE 同时设置 refResource 为 write_db。这样当写库操作过于频繁时，读数据的请求会被限流。
     *
     * 当设置write的阈值类型是qps,单机阈值是2,流控模式为关联,关联资源为read,当访问read接口qps超过2,那么write会被流控
     * @return
     */
    @SentinelResource(value = "read",entryType = EntryType.IN)
    @RequestMapping("/read")
    public Map<String, Object> read() {


        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","read");
            put("data", UUID.randomUUID().toString());
        }};
    }


    @SentinelResource(value = "write",entryType = EntryType.IN)
    @RequestMapping("/write")
    public Map<String, Object> write() {


        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","write");
            put("data", UUID.randomUUID().toString());
        }};
    }

/****************************************************分隔符***********************************************/

    /**
     * sentinel默认与springMvc整合后的入口资源名为：sentinel_spring_web_context
     * 【如果想使用链路流控，链路必须填写的是入口资源名，即根资源名，不能是第二，第三...等层级的资源名】
     * 也因此，【默认所有的api的根资源名为sentinel_spring_web_context，因此链路流控不会生效，】
     * 或者填写sentinel_spring_web_context但达不到链路流控的效果，因此要将以下配置为false
     * spring.cloud.sentinel.web‐context‐unify: false
     *
     *
     * @return
     */
    @SentinelResource(value = "routeTest01",entryType = EntryType.IN)
    @RequestMapping("/routeTest01")
    public Map<String, Object> routeTest01() {


        return ((SentinelTestController)AopContext.currentProxy()).routeTest();
    }

    @SentinelResource(value = "routeTest02",entryType = EntryType.IN)
    @RequestMapping("/routeTest02")
    public Map<String, Object> routeTest02() {


        return ((SentinelTestController)AopContext.currentProxy()).routeTest();
    }

    @SentinelResource(value = "routeTest",entryType = EntryType.IN)
    public Map<String, Object> routeTest() {


        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","routeTest");
            put("data", UUID.randomUUID().toString());
        }};
    }

    /***
     * 【测试发现预热挺好用的】
     * 流控效果:Warm Up
     *Warm Up:预热启动
     *
     *Warm Up（RuleConstant.CONTROL_BEHAVIOR_WARM_UP）方式，即预热启动方式。当系统长期处于低水位的情况下，当流量
     * 突然增加时【激增流量】，直接把系统拉升到高水位可能瞬间把系统压垮。通过"冷启动"，让通过的流量缓慢增加，在一定时间内逐渐
     * 增加到阈值上限，给冷系统一个预热的时间，避免冷系统被压垮。
     * 冷加载因子: 【codeFactor 默认是3，即请求 QPS 从 threshold / 3 开始，经预热时长逐渐升至设定的 QPS 阈值。】
     * 预热时长: 经过多长时间升至设定的QPS阈值
     * @return
     */
    @SentinelResource(value = "warmUpTest",entryType = EntryType.IN)
    @RequestMapping("warmUpTest")
    public Map<String, Object> warmUpTest() {


        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","warmUpTest");
            put("data", UUID.randomUUID().toString());
        }};
    }


    /**
     * 流控效果: 排队等待
     * 超时时长:当超过这个时间，进行降级处理
     * 可以达到匀速的效果
     *这种方式主要用于处理间隔性突发的流量，例如消息队列。想象一下这样的【脉冲流量】场景，在某一秒有大量的请求到来，而接下
     * 来的几秒则处于空闲状态，我们希望系统能够在接下来的空闲期间逐渐处理这些请求，而不是在第一秒直接拒绝多余的
     * 请求。
     * 【注意：匀速排队模式暂时不支持 QPS > 1000 的场景。】
     * 总结：【这种方式就尽量不要用啦】
     *
     *
     * @return
     */
    @SentinelResource(value = "queueWait",entryType = EntryType.IN)
    @RequestMapping("queueWait")
    public Map<String, Object> queueWait() {


        return new HashMap<String, Object>() {{
            put("code",0);
            put("msg","queueWait");
            put("data", UUID.randomUUID().toString());
        }};
    }


}
