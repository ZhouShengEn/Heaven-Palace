package com.heaven.palace.brightpalace.infrastructure.cache.oauth2.consts;

import com.heaven.palace.jasperpalace.base.cache.constants.BaseCacheConst;
import com.heaven.palace.jasperpalace.base.cache.param.ICacheParam;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoushengen
 * @Description: 统一认证缓存枚举
 * @DateTime: 2024/2/1 17:26
 **/
public interface Oauth2CacheConst {

    String OAUTH2_CACHE_PREFIX = BaseCacheConst.BASE_APPLICATION_CACHE_PRE_KEY + "oauth2:";
    String OAUTH2_CODE_CACHE_PREFIX = OAUTH2_CACHE_PREFIX + "code:";

    enum Oauth2CacheEnum implements ICacheParam {
        OAUTH2_CODE_CACHE(OAUTH2_CODE_CACHE_PREFIX, 5L, TimeUnit.MINUTES);

        private final String prefixKey;

        private final Long timeToLive;

        private final TimeUnit timeUnit;

        Oauth2CacheEnum(String prefixKey, Long timeToLive, TimeUnit timeUnit) {
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
