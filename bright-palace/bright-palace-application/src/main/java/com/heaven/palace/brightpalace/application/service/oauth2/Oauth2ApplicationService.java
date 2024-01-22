package com.heaven.palace.brightpalace.application.service.oauth2;

import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhoushengen
 * @Description: 统一认证应用服务接口
 * @DateTime: 2024/1/19 17:52
 **/
public interface Oauth2ApplicationService {

    /**
     * 登录认证
     * @param serverHttpRequest
     * @param response
     * @param loginFor
     * @param args
     */
    void auth(ServerHttpRequest serverHttpRequest, HttpServletResponse response, String loginFor, String... args);
}
