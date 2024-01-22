package com.heaven.palace.purplecloudpalace.oauth2;

import com.heaven.palace.jasperpalace.base.cache.constants.CommonCacheConst.CommonEnum;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import com.heaven.palace.purplecloudpalace.aop.annotation.IgnoreUserAuth;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: zhoushengen
 * @Description: 统一认证客户端控制层
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
    public static final String SERVER_AUTH_REDIRECT_TEMPLATE = "?clientId=%s&state=%s";

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String clientSecret;

    @Value("${oauth2.server.authRule}")
    private String authRule;

    @Resource
    private DefaultObjectCache defaultObjectCache;

    @GetMapping(value = "/login")
    @ApiOperation(value = "基于授权码的登录接口")
    @IgnoreUserAuth
    public void login(ServerHttpRequest serverHttpRequest, HttpServletResponse response
        , @RequestParam(required = false) String loginFor, @RequestParam(required = false) String encryptCode) {

        if (!ObjectUtils.anyNotNull(loginFor, encryptCode)) {
            throw new BusinessException(CommonExceptionEnum.AUTH_REQUEST_PARAM_NULL_ERROR);
        }

        if (StringUtils.isEmpty(encryptCode)) {
            // 登录拦截，跳转至客户端对应的登录页面

            String stateUuid = UUID.randomUUID().toString().replace("-", "");
            defaultObjectCache.setToCache(new CacheParam(CommonEnum.LOGIN_STATE_CACHE, stateUuid), loginFor);

            try {
                response.sendRedirect(authRule.concat(String.format(SERVER_AUTH_REDIRECT_TEMPLATE, clientId, stateUuid)));
            } catch (IOException e) {
                throw new BusinessException(CommonExceptionEnum.AUTH_REDIRECT_CLIENT_URL_ERROR);
            }
        } else {
            // todo 授权码换取token

        }
    }
}
