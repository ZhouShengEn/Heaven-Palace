package com.heaven.palace.brightpalace.application.service.oauth2.impl;

import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.brightpalace.domain.business.oauth2.repository.Oauth2Repository;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.constant.CommonConst.Header;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author: zhoushengen
 * @Description: 统一认证应用服务接口实现
 * @DateTime: 2024/1/19 17:53
 **/
@Service
@Slf4j
public class Oauth2ApplicationServiceImpl implements Oauth2ApplicationService {

    @Resource
    private MultiRepoFactory multiRepoFactory;


    @Override
    public void authLogin(ServerHttpRequest serverHttpRequest, HttpServletResponse response, String loginFor,
        String code) {

        if (!ObjectUtils.anyNotNull(loginFor, code)) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_REQUEST_PARAM_NULL_ERROR);
        }

        List<String> clientCodes = serverHttpRequest.getHeaders().get(Header.CLIENT_HEADER);
        if (CollectionUtils.isEmpty(clientCodes)) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_REQUEST_CLIENT_NULL_ERROR);
        }

        Oauth2Repository oauth2Repository = (Oauth2Repository) multiRepoFactory.getMultiDateSource(RepoRegisterConst.OAUTH2);
        String clientCode = clientCodes.get(0);
        List<ClientEntity> clientEntities = oauth2Repository.select(new ClientEntity().setCode(clientCode));

        if (CollectionUtils.isEmpty(clientEntities)) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_CLIENT_QUERY_NULL_ERROR);
        }

        ClientEntity clientEntity = clientEntities.get(0);


        if (StringUtils.isEmpty(code)) {
            // 登录拦截，跳转至客户端对应的登录页面
            log.info("authLogin start redirect clientCode:{}, loginFor:{}, loginUrl:{}", clientCode, loginFor,
                clientEntity.getLoginUrl());

            String stateUuid = UUID.randomUUID().toString().replace("-", "");
            // todo 缓存loginFor，授权完成后即可跳转

            try {
                response.sendRedirect(clientEntity.getLoginUrl());
            } catch (IOException e) {
                throw new BusinessException(BusinessExceptionEnum.AUTH_REDIRECT_CLIENT_URL_ERROR);
            }
        } else {
            // 授权码登录拦截

        }





    }
}
