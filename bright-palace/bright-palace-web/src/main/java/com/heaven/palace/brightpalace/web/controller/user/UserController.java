package com.heaven.palace.brightpalace.web.controller.user;

import com.heaven.palace.brightpalace.api.api.permission.vo.CheckPermissionVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalace.application.service.permission.PermissionApplicationService;
import com.heaven.palace.brightpalace.application.service.user.UserApplicationService;
import com.heaven.palace.jasperpalace.base.annotation.IgnoreUserAuth;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

    @Resource
    private PermissionApplicationService permissionApplicationService;

    @GetMapping(value = "/checkPermission")
    @ApiOperation(value = "用户权限查询")
    public Mono<CheckPermissionVO> checkPermission(@RequestParam String resourceValue, @RequestParam Integer resourceType) {
        return permissionApplicationService.checkPermission(resourceValue, resourceType);
    }


    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册")
    @IgnoreUserAuth
    public GlobalRestResponse<Void> register(@RequestBody UserRegisterVO userRegisterVO) {
        userApplicationService.register(userRegisterVO);
        return GlobalRestResponse.success("注册成功！");
    }
}
