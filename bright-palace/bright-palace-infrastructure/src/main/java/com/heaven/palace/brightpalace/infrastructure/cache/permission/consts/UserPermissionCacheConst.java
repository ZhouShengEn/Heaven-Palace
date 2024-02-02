package com.heaven.palace.brightpalace.infrastructure.cache.permission.consts;

import com.heaven.palace.jasperpalace.base.cache.constants.BaseCacheConst;
import com.heaven.palace.jasperpalace.base.cache.param.ICacheParam;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoushengen
 * @Description: 用户缓存枚举
 * @DateTime: 2024/1/30 16:18
 **/
public interface UserPermissionCacheConst {

    String USER_CACHE_PREFIX = BaseCacheConst.BASE_APPLICATION_CACHE_PRE_KEY + "user:";

    String USER_PERMISSIONS_CACHE_PREFIX = USER_CACHE_PREFIX + "permissions:";
    String USER_RESOURCE_PERMISSIONS_CACHE_PREFIX = USER_PERMISSIONS_CACHE_PREFIX + "resource:";
    String USER_MENU_PERMISSIONS_CACHE_PREFIX = USER_PERMISSIONS_CACHE_PREFIX + "menu:";

    enum UserCacheEnum implements ICacheParam {
        USER_RESOURCE_PERMISSIONS(USER_RESOURCE_PERMISSIONS_CACHE_PREFIX, 1L, TimeUnit.DAYS),
        USER_MENU_PERMISSIONS(USER_MENU_PERMISSIONS_CACHE_PREFIX, 1L, TimeUnit.DAYS)
        ;

        private final String prefixKey;

        private final Long timeToLive;

        private final TimeUnit timeUnit;

        UserCacheEnum(String prefixKey, Long timeToLive, TimeUnit timeUnit) {
            this.prefixKey = prefixKey;
            this.timeToLive = timeToLive;
            this.timeUnit = timeUnit;
        }

        @Override
        public String prefixKey() {
            return prefixKey;
        }

        @Override
        public Long timeToLive() {
            return timeToLive;
        }

        @Override
        public TimeUnit timeUnit() {
            return timeUnit;
        }
    }
}
