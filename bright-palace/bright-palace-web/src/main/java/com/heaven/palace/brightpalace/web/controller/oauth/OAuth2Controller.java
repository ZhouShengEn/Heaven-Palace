package com.heaven.palace.brightpalace.web.controller.oauth;

import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.purplecloudpalace.aop.annotation.IgnoreUserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
    private Oauth2ApplicationService oauth2ApplicationService;

    @GetMapping(value = "code/auth")
    @ApiOperation(value = "基于授权码的登录接口")
    @IgnoreUserAuth
    public void login(ServerHttpRequest serverHttpRequest, HttpServletResponse response
        , @RequestParam String clientId, @RequestParam(required = false) String... args) {
        oauth2ApplicationService.auth(serverHttpRequest, response, clientId, args);
    }
}
