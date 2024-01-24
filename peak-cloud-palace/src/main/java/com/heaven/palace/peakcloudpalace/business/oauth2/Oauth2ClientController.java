package com.heaven.palace.peakcloudpalace.business.oauth2;

import cn.hutool.core.util.IdUtil;
import com.heaven.palace.brightpalace.api.api.oauth2.Oauth2Api;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenReqVO;
import com.heaven.palace.brightpalace.api.api.oauth2.vo.Oauth2QueryTokenResVO;
import com.heaven.palace.jasperpalace.base.cache.constants.CommonCacheConst.CommonCacheEnum;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import com.heaven.palace.jasperpalace.base.exception.EncryptException;
import com.heaven.palace.jasperpalace.base.response.GlobalRestResponse;
import com.heaven.palace.jasperpalace.base.annotation.IgnoreUserAuth;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import com.heaven.palace.purplecloudpalace.util.RandomAESEncryptUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author: zhoushengen
 * @Description: 统一认证客户端控制层，以内部客户端授权码登录为例，第三方客户端对接统一认证时可参考
 * @DateTime: 2024/1/22 17:30
 **/
@RestController
@RequestMapping(value = "/oauth2")
@Slf4j
@RefreshScope
public class Oauth2ClientController {

    /**
     * 服务端重定向认证模板
     */
    public static final String SERVER_AUTH_REDIRECT_TEMPLATE = "?clientId=%s&responseType=%s&redirectUrl=%s";

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String clientSecret;

    @Value("${oauth2.server.authUrl}")
    private String authUrl;

    @Value("${oauth2.client.loginUrl}")
    private String loginUrl;

    @Value("${oauth2.client.defaultHomePage}")
    private String clientDefaultHomePage;

    @Resource
    private DefaultObjectCache defaultObjectCache;

    @Resource
    private Oauth2Api oauth2Api;

    @GetMapping(value = "/login")
    @ApiOperation(value = "基于授权码的登录接口")
    @IgnoreUserAuth
    public void login(HttpServletRequest request, HttpServletResponse response
        , @RequestParam(required = false) String loginFor, @RequestParam(required = false) String code,
        @RequestParam(required = false) String stateUuid) {

        if (!ObjectUtils.anyNotNull(loginFor, code)) {
            throw new BusinessException(CommonExceptionEnum.AUTH_REQUEST_PARAM_NULL_ERROR);
        }

        if (StringUtils.isEmpty(code)) {
            // 登录拦截，跳转至客户端对应的登录页面
            stateUuid = IdUtil.fastSimpleUUID();
            // 缓存用户访问的资源页面
            defaultObjectCache.setToCache(new CacheParam(CommonCacheEnum.LOGIN_STATE_CACHE, stateUuid), loginFor);
            try {
                String redirectUrl = URLEncoder.encode(loginUrl.concat("?stateUuid=").concat(stateUuid),
                    StandardCharsets.UTF_8.name());
                response.sendRedirect(authUrl.concat(String.format(SERVER_AUTH_REDIRECT_TEMPLATE, clientId
                        , CommonConst.Oauth2ResponseType.CODE, redirectUrl)));
            } catch (Exception e) {
                throw new BusinessException(CommonExceptionEnum.AUTH_REDIRECT_CLIENT_URL_ERROR);
            }
        } else {
            String loginForUrl;
            // 授权码换取token
            if (null == stateUuid || null == (loginForUrl = defaultObjectCache.getFromCache(
                new CacheParam(CommonCacheEnum.LOGIN_STATE_CACHE, stateUuid), String.class))) {
                loginForUrl = clientDefaultHomePage;
            }
            String encryptCode = null;
            try {
                encryptCode = RandomAESEncryptUtils.encryptForString(code, clientSecret);
            } catch (EncryptException e) {
                throw new RuntimeException(e);
            }

            GlobalRestResponse<Oauth2QueryTokenResVO> queryTokenRes = oauth2Api.queryToken(new Oauth2QueryTokenReqVO()
                    .setClientId(clientId)
                    .setEncryptCode(encryptCode)
                    .setResponseType(CommonConst.Oauth2ResponseType.CODE));
            if (!GlobalRestResponse.success(queryTokenRes)) {
                throw new BusinessException(queryTokenRes);
            }
            String accessToken = queryTokenRes.getData().getAccessToken();
            try {
                response.addHeader(CommonConst.Header.AUTH_HEADER, CommonConst.Header.AUTH_HEADER_BEARER.concat(accessToken));
                response.sendRedirect(loginForUrl);
            } catch (Exception e) {
                throw new BusinessException(CommonExceptionEnum.AUTH_REDIRECT_CLIENT_LOGIN_FOR_URL_ERROR);
            }

        }
    }
}
