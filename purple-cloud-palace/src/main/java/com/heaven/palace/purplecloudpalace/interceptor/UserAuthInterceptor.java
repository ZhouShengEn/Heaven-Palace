package com.heaven.palace.purplecloudpalace.interceptor;


import com.heaven.palace.jasperpalace.base.annotation.IgnoreUserAuth;
import com.heaven.palace.purplecloudpalace.auth.cache.consts.AuthCacheConst.AuthCacheEnum;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext.UserCache;
import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import com.heaven.palace.jasperpalace.base.exception.ErrorRequestException;
import com.heaven.palace.jasperpalace.base.exception.auth.AuthenticationException;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import com.heaven.palace.purplecloudpalace.util.AuthUtil;
import com.heaven.palace.purplecloudpalace.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhoushengen
 * @Description: 用户认证拦截器，要求所有业务系统必须注入此拦截器
 * @DateTime: 2024/1/10 9:39
 **/
public class UserAuthInterceptor implements AsyncHandlerInterceptor {

    @Value("${error.path:/error}")
    private String errorPath = "/error";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (errorPath.equals(request.getServletPath())) {
            throw new ErrorRequestException(CommonExceptionEnum.REQUEST_NULL);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 忽略用户认证
        IgnoreUserAuth annotation = handlerMethod.getBeanType().getAnnotation(IgnoreUserAuth.class);
        if (annotation != null || handlerMethod.hasMethodAnnotation(IgnoreUserAuth.class)) {
            return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
        }

        String token = AuthUtil.obtainAuthorization(request);
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException(CommonExceptionEnum.AUTH_TOKEN_EMPTY_ERROR);
        }
        DefaultObjectCache defaultObjectCache = SpringContextUtils.getBean(DefaultObjectCache.class);
        UserCache userCache = defaultObjectCache.getFromCache(new CacheParam(AuthCacheEnum.USER_AUTH_TOKEN_CACHE, token),
            UserCache.class);
        if (null == userCache) {
            throw new AuthenticationException(CommonExceptionEnum.AUTH_TOKEN_EXPIRE_ERROR);
        }
        CurrentBaseContext.setUserCache(userCache);
        CurrentBaseContext.setUserToken(token);

        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        CurrentBaseContext.clear();
        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
