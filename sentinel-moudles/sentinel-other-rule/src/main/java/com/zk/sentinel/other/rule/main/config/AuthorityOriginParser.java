package com.zk.sentinel.other.rule.main.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zk
 * @Date: 2022/1/6 11:37
 * @Description:
 * @Modified:
 * @version: V1.0
 */
@Component
public class AuthorityOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String token = request.getHeader("token");
        //如果为空,返回一个默认值，该默认值可配置到黑名单或者白名单
        if(StringUtils.isBlank(token)){
            return "limit";
        }

        return token;
    }
}
