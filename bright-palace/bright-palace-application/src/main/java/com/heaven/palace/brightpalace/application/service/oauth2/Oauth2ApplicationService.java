package com.heaven.palace.brightpalace.application.service.oauth2;

import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.jasperpalace.base.factory.service.MultiServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhoushengen
 * @Description: 统一认证应用服务接口
 * @DateTime: 2024/1/19 17:52
 **/
public interface Oauth2ApplicationService extends MultiServiceInterface {

    /**
     * 认证
     * @param request
     * @param response
     * @param clientId
     */
    void auth(HttpServletRequest request, HttpServletResponse response, String clientId);

    /**
     * 用户登录
     * @param userLoginPhoneAndPasswordVO
     * @param redirectUrl
     * @param request
     * @param response
     */
    void login(UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO, String redirectUrl
        , HttpServletRequest request, HttpServletResponse response);
}
