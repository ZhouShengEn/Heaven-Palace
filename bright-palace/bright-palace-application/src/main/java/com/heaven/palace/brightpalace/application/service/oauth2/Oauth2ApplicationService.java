package com.heaven.palace.brightpalace.application.service.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhoushengen
 * @Description: 统一认证应用服务接口
 * @DateTime: 2024/1/19 17:52
 **/
public interface Oauth2ApplicationService {

    /**
     * 登录认证
     * @param request
     * @param response
     * @param clientId
     * @param args
     */
    void auth(HttpServletRequest request, HttpServletResponse response, String clientId, String... args);
}
