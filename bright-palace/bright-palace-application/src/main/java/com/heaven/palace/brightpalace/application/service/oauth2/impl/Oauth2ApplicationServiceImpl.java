package com.heaven.palace.brightpalace.application.service.oauth2.impl;

import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhoushengen
 * @Description: 统一认证应用服务接口实现
 * @DateTime: 2024/1/19 17:53
 **/
@Service
@Slf4j
public class Oauth2ApplicationServiceImpl implements Oauth2ApplicationService {

    public static final String LOGIN_REDIRECT_TEMPLATE = "?code=%s&state=%s";

    @Value("${oauth2.login.url}")
    private String loginUrl;

    @Resource
    private MultiRepoFactory multiRepoFactory;

    @Resource
    private DefaultObjectCache defaultObjectCache;


    @Override
    public void auth(HttpServletRequest request, HttpServletResponse response, String clientId,
                     String... args) {

//
//        Oauth2Repository oauth2Repository = (Oauth2Repository) multiRepoFactory.getMultiDateSource(RepoRegisterConst.OAUTH2);
//        List<ClientEntity> clientEntities = oauth2Repository.select(new ClientEntity().setCode(clientId));
//        if (CollectionUtils.isEmpty(clientEntities)) {
//            throw new BusinessException(BusinessExceptionEnum.AUTH_CLIENT_QUERY_NULL_ERROR);
//        }
//        ClientEntity clientEntity = clientEntities.get(0);

        try {
            response.sendRedirect(loginUrl.concat(request.getQueryString()));
        } catch (IOException e) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_REDIRECT_LOGIN_URL_ERROR);
        }



    }
}
