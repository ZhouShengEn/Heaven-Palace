package com.heaven.palace.brightpalaceapplication.service.user.impl;

import com.heaven.palace.brightpalaceapi.api.user.dto.UserAuthDTO;
import com.heaven.palace.brightpalaceapi.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalaceapplication.service.user.UserApplicationService;
import com.heaven.palace.brightpalaceinfrastructure.mapper.BaseUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.annotation.Resource;

/**
 * @author 10733
 * @date 2024/1/14 23:54
 * @description: 用户应用层服务实现
 */
@Service
@Slf4j
public class UserApplicationServiceImpl implements UserApplicationService {

    @Resource
    private BaseUserMapper baseUserMapper;


    @Override
    public UserAuthDTO auth(HttpRequestHandlerServlet request) {
        return null;
    }

    @Override
    public void register(UserRegisterVO userRegisterVO) {

    }
}
