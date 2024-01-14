package com.heaven.palace.brightpalacerpc.api.user;

import com.heaven.palace.brightpalaceapi.api.user.UserApi;
import com.heaven.palace.brightpalaceapi.api.user.dto.UserAuthDTO;
import com.heaven.palace.brightpalaceapplication.service.user.UserApplicationService;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.annotation.Resource;

/**
 * @author 10733
 * @date 2024/1/14 22:38
 * @description: 用户api实现
 */
@RestController
public class UserApiImpl implements UserApi {

    @Resource
    private UserApplicationService userApplicationService;

    @Override
    public GlobalRestResponse<UserAuthDTO> auth(HttpRequestHandlerServlet request) {
        return new GlobalRestResponse<>().data(userApplicationService.auth(request));
    }
}
