package com.heaven.palace.brightpalace.application.service.oauth2.impl;

import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.brightpalace.domain.business.oauth2.repository.Oauth2Repository;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.constant.CommonConst.Header;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 统一认证应用服务接口实现
 * @DateTime: 2024/1/19 17:53
 **/
@Service
@Slf4j
public class Oauth2ApplicationServiceImpl implements Oauth2ApplicationService {

    public static final String LOGIN_REDIRECT_TEMPLATE = "?code=%s&state=%s";

    @Resource
    private MultiRepoFactory multiRepoFactory;

    @Resource
    private DefaultObjectCache defaultObjectCache;


    @Override
    public void auth(ServerHttpRequest serverHttpRequest, HttpServletResponse response, String loginFor,
        String... args) {


        List<String> clientCodes = serverHttpRequest.getHeaders().get(Header.CLIENT_HEADER);
        Oauth2Repository oauth2Repository = (Oauth2Repository) multiRepoFactory.getMultiDateSource(RepoRegisterConst.OAUTH2);
        String clientCode = clientCodes.get(0);
        List<ClientEntity> clientEntities = oauth2Repository.select(new ClientEntity().setCode(clientCode));

        if (CollectionUtils.isEmpty(clientEntities)) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_CLIENT_QUERY_NULL_ERROR);
        }
        ClientEntity clientEntity = clientEntities.get(0);
        if (CollectionUtils.isEmpty(clientCodes)) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_REQUEST_CLIENT_NULL_ERROR);
        }

    }
}
