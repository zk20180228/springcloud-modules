package com.zk.skywalking.api.provider.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zk
 * @Date: 2022/1/25 14:57
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Component
public class AuthorityOriginParse implements RequestOriginParser {

    @Resource
    private NacosConfig nacosConfig;

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String tokenValue = request.getHeader(nacosConfig.getTokenName());
        if(StringUtils.isBlank(tokenValue)){
            return "limit";
        }

        return tokenValue;
    }
}
