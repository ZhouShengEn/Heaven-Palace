package com.heaven.palace.brightpalace.application.service.user;

import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserRegisterVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 10733
 * @date 2024/1/14 23:53
 * @description: 用户应用层服务
 */
public interface UserApplicationService {

    /**
     * 用户登录认证
     * @param userLoginPhoneAndPasswordVO
     * @param request
     * @param response
     */
    void login(UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户注册
     * @param userRegisterVO
     */
    void register(UserRegisterVO userRegisterVO);
}
