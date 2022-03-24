package com.zk.skywalking.api.consumer.config.sentienl;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zk
 * @Date: 2022/1/27 14:52
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Component
public class SysRequestOriginParse implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            return "limit";
        }
        return token;
    }
}
