package com.heaven.palace.brightpalace.web.controller.user;

import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalace.application.service.user.UserApplicationService;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import com.heaven.palace.purplecloudpalace.aop.annotation.IgnoreUserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: zhoushengen
 * @Description: 用户控制类
 * @DateTime: 2024/1/15 12:38
 **/
@RestController
@Api(tags = "用户控制类")
@RequestMapping("user")
public class UserController {

    @Resource
    private UserApplicationService userApplicationService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户账户密码手机号双因子登录")
    public GlobalRestResponse<Void> login(@RequestBody @Valid UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO
            , HttpServletRequest request, HttpServletResponse response) {
        userApplicationService.login(userLoginPhoneAndPasswordVO, request, response);
        return new GlobalRestResponse<>().message("登录成功！");
    }


    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册")
    @IgnoreUserAuth
    public GlobalRestResponse<Void> register(@RequestBody UserRegisterVO userRegisterVO) {
        userApplicationService.register(userRegisterVO);
        return new GlobalRestResponse<>().message("注册成功！");
    }
}
