package com.heaven.palace.brightpalace.application.service.oauth2.impl;

import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.AuthAggregate;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.brightpalace.domain.business.oauth2.repository.Oauth2Repository;
import com.heaven.palace.brightpalace.domain.business.oauth2.service.Oauth2DomainService;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.auth.context.Oauth2TypeRegisterConst.ResponseTypeEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.cache.constants.CommonCacheConst.CommonCacheEnum;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import com.heaven.palace.purplecloudpalace.util.AuthUtil;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 统一认证应用服务接口实现
 * @DateTime: 2024/1/19 17:53
 **/
@Service
@Slf4j
public class Oauth2ApplicationServiceImpl implements Oauth2ApplicationService {

    public static final String LOGIN_REDIRECT_TEMPLATE = "%s?responseType=%s&%s";

    @Resource
    private MultiRepoFactory multiRepoFactory;

    @Resource
    private DefaultObjectCache defaultObjectCache;

    @Resource
    private Oauth2DomainService oauth2DomainService;


    @Override
    public void auth(HttpServletRequest request, HttpServletResponse response, String clientId) {

       Oauth2Repository oauth2Repository = (Oauth2Repository) multiRepoFactory.getMultiImplement(RepoRegisterConst.OAUTH2);
       List<ClientEntity> clientEntities = oauth2Repository.select(new ClientEntity().setCode(clientId));
       if (CollectionUtils.isEmpty(clientEntities)) {
           throw new BusinessException(BusinessExceptionEnum.AUTH_CLIENT_QUERY_NULL_ERROR);
       }
       ClientEntity clientEntity = clientEntities.get(0);
        String token = AuthUtil.obtainAuthorization(request);
        // 确认token是否为空
        if (StringUtils.isEmpty(token) || null ==
            defaultObjectCache.getFromCache(new CacheParam(CommonCacheEnum.USER_AUTH_TOKEN_CACHE, token))) {
            try {
                response.sendRedirect(String.format(LOGIN_REDIRECT_TEMPLATE, clientEntity.getLoginUrl(),
                    ResponseTypeEnum.CODE.getType(), request.getQueryString()));
            } catch (IOException e) {
                throw new BusinessException(BusinessExceptionEnum.AUTH_REDIRECT_LOGIN_URL_ERROR);
            }
        }

    }

    @Override
    public void login(UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO, String responseType, String redirectUrl,
        HttpServletRequest request, HttpServletResponse response) {

        AuthAggregate authAggregate = oauth2DomainService.loginByPasswordAndPhone(
            MappingUtils.beanConvert(userLoginPhoneAndPasswordVO, UserAggregate.class));


    }
}
