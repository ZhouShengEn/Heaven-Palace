package com.heaven.palace.brightpalace.application.service.oauth2.impl;

import com.alibaba.fastjson.JSON;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenResVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2RefreshTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2RefreshTokenResVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.brightpalace.application.factory.auth.context.Oauth2TypeRegisterConst.ResponseTypeEnum;
import com.heaven.palace.brightpalace.application.service.oauth2.Oauth2ApplicationService;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.AuthTokenAggregate;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.value.Oauth2ClientParam;
import com.heaven.palace.brightpalace.domain.business.oauth2.manage.Oauth2TokenManage;
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
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import com.heaven.palace.purplecloudpalace.auth.cache.consts.AuthCacheConst.AuthCacheEnum;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import com.heaven.palace.purplecloudpalace.util.AuthUtil;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import com.heaven.palace.purplecloudpalace.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${oauth2.code.size:8}")
    private Integer codeSize;

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

    @Resource
    private Oauth2TokenManage oauth2TokenManage;


    @Override
    public void auth(HttpServletRequest request, HttpServletResponse response, String clientId) {

        ClientEntity clientEntity = getClientEntityByClientId(clientId);
        String token = AuthUtil.obtainAuthorization(request);
        // 确认token是否为空
        if (StringUtils.isEmpty(token) || null ==
            defaultObjectCache.getFromCache(new CacheParam(AuthCacheEnum.USER_AUTH_ACCESS_TOKEN_CACHE, token))) {
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
        String code = generateCode();
        // 客户端授权码token录入缓存
        setCodeCache(clientId, authTokenAggregate, code);
        // 用户access_token录入缓存
        oauth2TokenManage.saveAccessToken(authTokenAggregate.getAccessToken().getToken(), userAggregate.convert2UserCache());
        // 用户refresh_token录入缓存
        oauth2TokenManage.saveRefreshToken(authTokenAggregate.getFreshToken().getToken(), authTokenAggregate.getAccessToken().getToken());
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
        // 初始化校验客户端密钥
        Oauth2ClientParam oauth2Code = new Oauth2ClientParam(queryTokenByCodeReqVO.getEncryptCode(), clientEntity.getSecret());
        Oauth2CacheParam oauth2CacheParam = new Oauth2CacheParam(Oauth2CacheEnum.OAUTH2_CODE_CACHE, clientId,
            oauth2Code.getValue());
        AuthTokenAggregate authTokenAggregate = oauth2Cache.getFromCache(oauth2CacheParam);
        BusinessException.throwWith(null == authTokenAggregate, BusinessExceptionEnum.AUTH_OAUTH2_TOKEN_CACHE_NOT_HIT_ERROR);
        // 内部客户端和第三方客户端返回不同token，第三方客户端token应走openapi网关，目前暂未设计
        String accessToken = SystemOrganizationCodeConst.HEAVEN_PALACE.equals(clientEntity.getOrgCode())
            ? authTokenAggregate.getAccessToken().getToken()
            : authTokenAggregate.getClientToken().getToken();
        // 授权码只能使用一次，用完即销毁
        oauth2Cache.remove(oauth2CacheParam);
        return new Oauth2QueryTokenResVO()
                .setAccessToken(accessToken)
                .setRefreshToken(authTokenAggregate.getFreshToken().getToken())
                .setExpireTime(oauth2Cache.getCacheRemainToLive(oauth2CacheParam));
    }

    @Override
    public Oauth2RefreshTokenResVO refresh(Oauth2RefreshTokenReqVO oauth2RefreshTokenReqVO) {
        String clientId = oauth2RefreshTokenReqVO.getClientId();
        ClientEntity clientEntity = getClientEntityByClientId(clientId);
        // 初始化校验客户端密钥
        Oauth2ClientParam clientRefreshToken = new Oauth2ClientParam(oauth2RefreshTokenReqVO.getEncryptRefreshToken(),
            clientEntity.getSecret());
        // 校验refresh token是否已过期
        String refreshToken = clientRefreshToken.getValue();
        String accessToken = oauth2TokenManage.getRefreshToken(refreshToken, String.class);
        BusinessException.throwWith(null == accessToken, BusinessExceptionEnum.AUTH_OAUTH2_TOKEN_CACHE_NOT_HIT_ERROR);
        Oauth2RefreshTokenResVO oauth2RefreshTokenResVO = new Oauth2RefreshTokenResVO();
        // 生成新的access token
        String newAccessToken = AuthTokenAggregate.generateToken();
        oauth2RefreshTokenResVO.setAccessToken(newAccessToken);
        oauth2RefreshTokenResVO.setExpireTime(oauth2TokenManage.getAccessTokenExpireTime(newAccessToken));
        if (clientEntity.getRefreshNew()) {
            // 删除旧的refresh token
            String newRefreshToken = AuthTokenAggregate.generateToken();
            oauth2RefreshTokenResVO.setRefreshToken(newRefreshToken);
            oauth2TokenManage.saveRefreshToken(newRefreshToken, newAccessToken);
            oauth2TokenManage.deleteRefreshToken(refreshToken);
        } else {
            // 更新refresh token关联的access token
            oauth2TokenManage.saveRefreshToken(refreshToken, newAccessToken);
        }
        // 删除旧的access token
        oauth2TokenManage.deleteAccessToken(accessToken);
        return oauth2RefreshTokenResVO;
    }


    private ClientEntity getClientEntityByClientId(String clientId) {
        Oauth2Repository oauth2Repository = (Oauth2Repository) multiRepoFactory.getMultiImplement(RepoRegisterConst.OAUTH2);
        List<ClientEntity> clientEntities = oauth2Repository.select(new ClientEntity().setCode(clientId));
        if (CollectionUtils.isEmpty(clientEntities)) {
            throw new BusinessException(BusinessExceptionEnum.AUTH_CLIENT_QUERY_NULL_ERROR);
        }
        return clientEntities.get(0);
    }

    /**
     * 客户端授权码token录入缓存
     * @param clientId
     * @param authTokenAggregate
     * @param code
     */
    private void setCodeCache(String clientId, AuthTokenAggregate authTokenAggregate, String code) {
        oauth2Cache.setToCache(new Oauth2CacheParam(Oauth2CacheEnum.OAUTH2_CODE_CACHE, clientId, code),
            authTokenAggregate);
    }

    /**
     * 授权码生成
     * @return
     */
    private String generateCode() {
        return UUIDUtils.generateUuid(codeSize);
    }

    @Override
    public String multiIdentity() {
        return ResponseTypeEnum.CODE.getMarkIdentify();
    }
}
