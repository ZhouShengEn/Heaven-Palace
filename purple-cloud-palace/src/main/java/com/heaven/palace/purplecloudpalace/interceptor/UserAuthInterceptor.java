package com.heaven.palace.purplecloudpalace.interceptor;


import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext.UserCache;
import com.heaven.palace.jasperpalace.base.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.exception.auth.AuthenticationException;
import com.heaven.palace.purplecloudpalace.aop.annotation.IgnoreUserAuth;
import com.heaven.palace.purplecloudpalace.component.cache.constants.CommonCacheConst;
import com.heaven.palace.purplecloudpalace.util.AuthUtil;
import com.heaven.palace.purplecloudpalace.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhoushengen
 * @Description: 用户认证拦截器
 * @DateTime: 2024/1/10 9:39
 **/
public class UserAuthInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 忽略用户认证
        IgnoreUserAuth annotation = handlerMethod.getBeanType().getAnnotation(IgnoreUserAuth.class);
        if (annotation != null || handlerMethod.hasMethodAnnotation(IgnoreUserAuth.class)) {
            return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
        }

        String token = AuthUtil.obtainAuthorization(request);
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException(BusinessExceptionEnum.TOKEN_EMPTY_ERROR);
        }
        RedissonClient redissonClient = SpringContextUtils.getBean(RedissonClient.class);
        RBucket<UserCache> userInfoBucketByToken
            = redissonClient.getBucket(CommonCacheConst.AUTH_TOKEN_KEY_PREFIX.concat(token));
        UserCache userCache;
        if (null == (userCache = userInfoBucketByToken.get())) {
            throw new AuthenticationException(BusinessExceptionEnum.TOKEN_EXPIRE_ERROR);
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
