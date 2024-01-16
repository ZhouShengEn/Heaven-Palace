package com.heaven.palace.brightpalace.application.service.user.impl;

import com.heaven.palace.brightpalace.api.api.user.dto.UserAuthDTO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalace.application.service.user.UserApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

/**
 * @author 10733
 * @date 2024/1/14 23:54
 * @description: 用户应用层服务实现
 */
@Service
@Slf4j
public class UserApplicationServiceImpl implements UserApplicationService {


    @Override
    public UserAuthDTO auth(HttpRequestHandlerServlet request) {
        return null;
    }

    @Override
    public void register(UserRegisterVO userRegisterVO) {

    }
}
