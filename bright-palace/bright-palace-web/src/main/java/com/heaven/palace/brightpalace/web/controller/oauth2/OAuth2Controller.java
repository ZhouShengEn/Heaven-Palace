package com.heaven.palace.brightpalace.web.controller.oauth2;

import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2RefreshTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2RefreshTokenResVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.brightpalace.application.factory.auth.MultiOAuth2TypeFactory;
import com.heaven.palace.jasperpalace.base.annotation.IgnoreUserAuth;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import com.heaven.palace.purplecloudpalace.aop.annotation.RepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: zhoushengen
 * @Description: 统一认证
 * @DateTime: 2024/1/19 17:14
 **/
@RestController
@Api(tags = "统一认证")
@RequestMapping("oauth2")
public class OAuth2Controller {

    @Resource
    private MultiOAuth2TypeFactory multiOAuth2TypeFactory;

    @GetMapping(value = "/auth")
    @ApiOperation(value = "OAuth2基于授权码认证")
    @IgnoreUserAuth
    public GlobalRestResponse<Void> auth(HttpServletRequest request, HttpServletResponse response,
        @RequestParam String clientId, @RequestParam String responseType) {
        multiOAuth2TypeFactory.getMultiImplement(responseType).auth(request, response, clientId);
        return GlobalRestResponse.success("认证成功！");
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户账户密码手机号双因子登录")
    @IgnoreUserAuth
    @RepeatSubmit
    public GlobalRestResponse<Void> login(@RequestBody @Valid UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO
        , @RequestParam String responseType, @RequestParam String redirectUrl, @RequestParam String clientId,
        HttpServletRequest request, HttpServletResponse response) {
        multiOAuth2TypeFactory.getMultiImplement(responseType)
                .login(userLoginPhoneAndPasswordVO, redirectUrl, clientId, request, response);
        return GlobalRestResponse.success("登录成功！");
    }

    @PostMapping(value = "/refresh")
    @ApiOperation(value = "token刷新")
    @IgnoreUserAuth
    public GlobalRestResponse<Oauth2RefreshTokenResVO> refresh(@RequestBody @Valid Oauth2RefreshTokenReqVO oauth2RefreshTokenReqVO) {
        return GlobalRestResponse.success(multiOAuth2TypeFactory.getMultiImplement(oauth2RefreshTokenReqVO.getResponseType())
            .refresh(oauth2RefreshTokenReqVO), "刷新成功！");
    }

}
