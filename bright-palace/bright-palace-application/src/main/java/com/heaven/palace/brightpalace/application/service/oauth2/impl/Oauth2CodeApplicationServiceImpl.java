package com.heaven.palace.brightpalace.application.service.oauth2.impl;

import com.alibaba.fastjson.JSON;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenResVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.brightpalace.application.factory.auth.context.Oauth2TypeRegisterConst.ResponseTypeEnum;
import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.AuthTokenAggregate;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.value.Oauth2Code;
import com.heaven.palace.brightpalace.domain.business.oauth2.repository.Oauth2Repository;
import com.heaven.palace.brightpalace.domain.business.oauth2.service.Oauth2DomainService;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.brightpalace.infrastructure.cache.oauth2.Oauth2Cache;
import com.heaven.palace.brightpalace.infrastructure.cache.oauth2.consts.Oauth2CacheConst.Oauth2CacheEnum;
import com.heaven.palace.brightpalace.infrastructure.cache.oauth2.param.Oauth2CacheParam;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext.UserCache;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import com.heaven.palace.purplecloudpalace.auth.cache.consts.AuthCacheConst.AuthCacheEnum;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import com.heaven.palace.purplecloudpalace.util.AuthUtil;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import com.heaven.palace.purplecloudpalace.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 统一认证授权码应用服务实现
 * @DateTime: 2024/1/19 17:53
 **/
@Service
@Slf4j
public class Oauth2CodeApplicationServiceImpl implements Oauth2ApplicationService {

    public static final String LOGIN_REDIRECT_TEMPLATE = "%s?%s";

    @Resource
    private MultiRepoFactory multiRepoFactory;

    @Resource
    private DefaultObjectCache defaultObjectCache;

    /**
     * 这里由于并未设计token持久化，所以可以直接使用缓存防腐层获取数据，如果存在持久化这里需要从领域层调取接口
     */
    @Resource
    private Oauth2Cache oauth2Cache;

    @Resource
    private Oauth2DomainService oauth2DomainService;


    @Override
    public void auth(HttpServletRequest request, HttpServletResponse response, String clientId) {

        ClientEntity clientEntity = getClientEntityByClientId(clientId);
        String token = AuthUtil.obtainAuthorization(request);
        // 确认token是否为空
        if (StringUtils.isEmpty(token) || null ==
            defaultObjectCache.getFromCache(new CacheParam(AuthCacheEnum.USER_AUTH_TOKEN_CACHE, token))) {
            try {
                response.sendRedirect(String.format(LOGIN_REDIRECT_TEMPLATE, clientEntity.getLoginUrl()
                        , request.getQueryString()));
            } catch (Exception e) {
                throw new BusinessException(BusinessExceptionEnum.AUTH_REDIRECT_LOGIN_URL_ERROR);
            }
        }

    }

    @Override
    public void login(UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO, String redirectUrl, String clientId,
        HttpServletRequest request, HttpServletResponse response) {
        log.info("oauth2 service login start username:{}, clientId:{}, redirectUrl:{}, userLoginPhoneAndPasswordVO:{}",
            userLoginPhoneAndPasswordVO.getUsername(), clientId, redirectUrl, JSON.toJSONString(userLoginPhoneAndPasswordVO));
        AuthTokenAggregate authTokenAggregate = oauth2DomainService
            .loginByPasswordAndPhone(MappingUtils.beanConvert(userLoginPhoneAndPasswordVO, UserAggregate.class));
        UserAggregate userAggregate = authTokenAggregate.getUserAggregate();
        // todo 重复登录校验！
        String code = UUIDUtils.generateUuid(8);
        // 客户端授权码token录入缓存
        oauth2Cache.setToCache(new Oauth2CacheParam(Oauth2CacheEnum.OAUTH2_CODE_CACHE, clientId, code), authTokenAggregate);
        // 用户token录入缓存
        defaultObjectCache.setToCache(new CacheParam(AuthCacheEnum.USER_AUTH_TOKEN_CACHE
                , authTokenAggregate.getAccessToken().getToken()),
            new UserCache().setUserId(userAggregate.getId().getId()).setUsername(userAggregate.getUsername().getValue()));
        try {
            String decodeUrl = URLDecoder.decode(redirectUrl, StandardCharsets.UTF_8.name());
            response.sendRedirect(decodeUrl.concat("&code=").concat(code));
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_REDIRECT_CLIENT_AUTH_URL_ERROR);
        }
    }

    @Override
    public Oauth2QueryTokenResVO queryToken(Oauth2QueryTokenReqVO queryTokenByCodeReqVO) {
        String clientId = queryTokenByCodeReqVO.getClientId();
        ClientEntity clientEntity = getClientEntityByClientId(clientId);
        Oauth2Code oauth2Code = new Oauth2Code(queryTokenByCodeReqVO.getEncryptCode(), clientEntity.getSecret());
        Oauth2CacheParam oauth2CacheParam = new Oauth2CacheParam(Oauth2CacheEnum.OAUTH2_CODE_CACHE, clientId,
            oauth2Code.getValue());
        AuthTokenAggregate authTokenAggregate = oauth2Cache.getFromCache(oauth2CacheParam);
        if (null == authTokenAggregate) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_OAUTH2_TOKEN_CACHE_NOT_HIT_ERROR);
        }
        // 内部客户端和第三方客户端返回不同token，第三方客户端token应走openapi网关，目前暂未设计
        String accessToken = SystemOrganizationCodeConst.HEAVEN_PALACE.equals(clientEntity.getOrgCode())
            ? authTokenAggregate.getAccessToken().getToken()
            : authTokenAggregate.getClientToken().getToken();
        return new Oauth2QueryTokenResVO()
                .setAccessToken(accessToken)
                .setRefreshToken(authTokenAggregate.getFreshToken().getToken())
                .setExpireTime(oauth2Cache.getCacheRemainToLive(oauth2CacheParam));
    }


    private ClientEntity getClientEntityByClientId(String clientId) {
        Oauth2Repository oauth2Repository = (Oauth2Repository) multiRepoFactory.getMultiImplement(RepoRegisterConst.OAUTH2);
        List<ClientEntity> clientEntities = oauth2Repository.select(new ClientEntity().setCode(clientId));
        if (CollectionUtils.isEmpty(clientEntities)) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_CLIENT_QUERY_NULL_ERROR);
        }
        return clientEntities.get(0);
    }
    @Override
    public String multiIdentity() {
        return ResponseTypeEnum.CODE.getMarkIdentify();
    }
}
