package com.heaven.palace.brightpalace.domain.business.oauth2.manage;

import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.value.RefreshTokenCache;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext.UserCache;
import com.heaven.palace.purplecloudpalace.auth.cache.consts.AuthCacheConst.AuthCacheEnum;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: zhoushengen
 * @Description: TODO
 * @DateTime: 2024/2/4 19:47
 **/
@Service
public class Oauth2TokenManage {

    @Resource
    private DefaultObjectCache defaultObjectCache;

    /**
     * 保存access token
     * @param token
     * @param value
     */
    public void saveAccessToken(String token, UserCache value) {
        defaultObjectCache.setToCache(new CacheParam(AuthCacheEnum.USER_AUTH_ACCESS_TOKEN_CACHE, token), value);
    }

    /**
     * 保持refresh token
     * @param token
     * @param value
     */
    public void saveRefreshToken(String token, RefreshTokenCache value) {
        defaultObjectCache.setToCache(new CacheParam(AuthCacheEnum.USER_AUTH_REFRESH_TOKEN_CACHE, token), value);
    }

    /**
     * 删除refresh token
     * @param token
     */
    public void deleteRefreshToken(String token) {
        defaultObjectCache.removeFromCache(new CacheParam(AuthCacheEnum.USER_AUTH_REFRESH_TOKEN_CACHE, token));
    }

    /**
     * 删除access token
     * @param token
     */
    public void deleteAccessToken(String token) {
        defaultObjectCache.removeFromCache(new CacheParam(AuthCacheEnum.USER_AUTH_ACCESS_TOKEN_CACHE, token));
    }

    /**
     * 获取refresh token
     * @param token
     * @return
     */
    public RefreshTokenCache getRefreshToken(String token) {
        return defaultObjectCache.getFromCache(new CacheParam(AuthCacheEnum.USER_AUTH_REFRESH_TOKEN_CACHE, token),
            RefreshTokenCache.class);
    }

    /**
     * 获取access token过期时间
     * @param token
     * @return
     */
    public Long getAccessTokenExpireTime(String token) {
        return defaultObjectCache.getCacheRemainToLive(new CacheParam(AuthCacheEnum.USER_AUTH_ACCESS_TOKEN_CACHE, token));
    }


}
