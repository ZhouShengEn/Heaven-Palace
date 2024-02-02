package com.heaven.palace.purplecloudpalace.auth.cache.consts;


import com.heaven.palace.jasperpalace.base.cache.constants.BaseCacheConst;
import com.heaven.palace.jasperpalace.base.cache.param.ICacheParam;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoushengen
 * @Description: 认证缓存枚举
 * @DateTime: 2024/1/10 15:34
 **/
public class AuthCacheConst {


    /**
     * Token缓存
     */
    public static final String AUTH_CACHE_KEY_PREFIX = "AUTH:";

    public static final String AUTH_TOKEN_KEY_PREFIX = AUTH_CACHE_KEY_PREFIX + "Token:";

    /**
     * 客户端统一认证缓存
     */
    public static final String CLIENT_OAUTH2_CACHE_PREFIX = BaseCacheConst.BASE_APPLICATION_CACHE_PRE_KEY + "oauth2:";
    public static final String CLIENT_LOGIN_STATE_PREFIX = CLIENT_OAUTH2_CACHE_PREFIX + "state:";


    public enum AuthCacheEnum implements ICacheParam {
        /**
         * 未授权目标url缓存
         */
        LOGIN_STATE_CACHE(CLIENT_LOGIN_STATE_PREFIX, 5L, TimeUnit.MINUTES),
        /**
         * token缓存
         */
        USER_AUTH_TOKEN_CACHE(AUTH_TOKEN_KEY_PREFIX, 5L, TimeUnit.HOURS),

        ;

        private final String prefixKey;

        private final Long timeToLive;

        private final TimeUnit timeUnit;

        AuthCacheEnum(String prefixKey, Long timeToLive, TimeUnit timeUnit) {
            this.prefixKey = prefixKey;
            this.timeToLive = timeToLive;
            this.timeUnit = timeUnit;
        }

        @Override
        public String prefixKey() {
            return this.prefixKey;
        }

        @Override
        public Long timeToLive() {
            return this.timeToLive;
        }

        @Override
        public TimeUnit timeUnit() {
            return this.timeUnit;
        }

    }
}
