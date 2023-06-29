package com.heaven.palace.moonpalace.modular.custom.global.constant;


import com.heaven.palace.moonpalace.constant.RedisCacheKeyConst;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author :zhoushengen
 * @date : 2022/9/8
 * 业务对应缓存枚举
 */
public enum BusinessCacheEnum {
    // 代码生成平台
    GENERATOR("generator", null),

    // 权限
    CUSTOM_RESOURCE_AUTHORITY("customResourceAuthority", RedisCacheKeyConst.CUSTOM_RESOURCE_AUTHORITY_ENV_CACHE_KEY_PREFIX),

    // 接口资源
    CUSTOM_ELEMENT("customElement", RedisCacheKeyConst.CUSTOM_ELEMENT_ENV_CACHE_KEY_PREFIX),

    ;

    /**
     * 业务标识
     */
    private final String moduleParam;

    /**
     * redis缓存key
     */
    private final String cacheKeyPrefix;

    public static String getCacheKeyPrefixByParam(String param) {
        if (null == param) {
            return null;
        }
        return Objects.requireNonNull(Arrays.stream(BusinessCacheEnum.values()).filter(businessCacheEnum 
                -> businessCacheEnum.moduleParam.equals(param)).findFirst()).orElseGet(null).getCacheKeyPrefix();
    }

    BusinessCacheEnum(String moduleParam, String cacheKey) {
        this.moduleParam = moduleParam;
        this.cacheKeyPrefix = cacheKey;
    }

    public String getModuleParam() {
        return moduleParam;
    }

    public String getCacheKeyPrefix() {
        return cacheKeyPrefix;
    }
}

