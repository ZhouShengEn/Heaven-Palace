package com.heaven.palace.brightpalacerpc.controller.user;

import com.heaven.palace.brightpalaceapi.api.user.dto.UserAuthDTO;
import com.heaven.palace.brightpalaceapi.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalaceapplication.service.user.UserApplicationService;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.annotation.Resource;

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

    @PostMapping(value = "/auth")
    @ApiOperation(value = "用户认证")
    public GlobalRestResponse<UserAuthDTO> auth(HttpRequestHandlerServlet request) {
        return new GlobalRestResponse<>().data(userApplicationService.auth(request));
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册")
    public GlobalRestResponse<Void> register(@RequestBody UserRegisterVO userRegisterVO) {
        userApplicationService.register(userRegisterVO);
        return new GlobalRestResponse<>().message("注册成功！");
    }
}
