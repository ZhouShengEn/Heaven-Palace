package com.heaven.palace.moonpalace.modular.system.controller;

import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.modular.system.service.UserService;
import com.heaven.palace.moonpalace.modular.system.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/6 9:58
 */
@RestController
@Api(tags = "用户中心")
@RequestMapping("/v2/user")
public class UserV2Controller extends BaseController {


    @Resource
    private UserService userService;


    @RequestMapping(value = "currentUser", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户信息")
    public ObjectRestResponse<UserVO> getCurrentUser() {

        return new ObjectRestResponse<>().data(userService.getCurrentUser());
    }

    /**
     * 上传头像
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload/avatar")
    @ApiOperation(value = "上传头像")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "avatar", value = "头像图片", dataTypeClass = MultipartFile.class, required = true)
    })
    public ObjectRestResponse<String> uploadAvatar(MultipartFile avatar) {
        return userService.uploadAvatar(avatar);
    }
}
