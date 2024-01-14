package com.heaven.palace.brightpalaceapplication.service.user;

import com.heaven.palace.brightpalaceapi.api.user.dto.UserAuthDTO;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

/**
 * @author 10733
 * @date 2024/1/14 23:53
 * @description: 用户应用层服务
 */
public interface UserApplicationService {

    /**
     * 用户认证
     * @param request
     * @return
     */
    UserAuthDTO auth(HttpRequestHandlerServlet request);
}
