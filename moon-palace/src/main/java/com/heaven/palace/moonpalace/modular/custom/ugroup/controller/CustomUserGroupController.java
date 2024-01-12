package com.heaven.palace.moonpalace.modular.custom.ugroup.controller;


import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.modular.custom.ugroup.service.CustomUserGroupService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/22 17:20
 */
@Slf4j
@RequestMapping(value = "/custom/group")
@RestController
@Api(tags = "基础角色管理")
public class CustomUserGroupController extends BaseController {

    @Resource
    private CustomUserGroupService customUserGroupService;

}
