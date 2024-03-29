package com.heaven.palace.brightpalace.application.service.oauth2;

import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenResVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2RefreshTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2RefreshTokenResVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.jasperpalace.base.factory.service.MultiServiceInterface;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
     * @param clientId
     * @param request
     * @param response
     */
    void login(UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO, String redirectUrl, String clientId
        , HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取token
     * @param queryTokenByCodeReqVO
     * @return
     */
    Oauth2QueryTokenResVO queryToken(Oauth2QueryTokenReqVO queryTokenByCodeReqVO);

    /**
     * 刷新token
     * @param oauth2RefreshTokenReqVO
     * @return
     */
    Oauth2RefreshTokenResVO refresh(@RequestBody @Valid Oauth2RefreshTokenReqVO oauth2RefreshTokenReqVO);
}
