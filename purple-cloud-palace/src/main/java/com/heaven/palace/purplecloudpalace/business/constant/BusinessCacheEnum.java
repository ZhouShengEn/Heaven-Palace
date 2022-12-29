package com.heaven.palace.purplecloudpalace.business.constant;


import com.heaven.palace.purplecloudpalace.constant.RedisCacheKeyConst;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author :zhoushengen
 * @date : 2022/9/8
 * 业务对应缓存枚举
 */
public enum BusinessCacheEnum {
    BUSINESS_DICT_ITEM("businessDictItem", RedisCacheKeyConst.BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX),
    BUSINESS_DICT_TYPE("businessDictType", RedisCacheKeyConst.BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX);

    private String param;
    private String cacheKeyPrefix;

    public static String getCacheKeyPrefixByParam(String param) {
        if (null == param) {
            return null;
        }
        return Objects.requireNonNull(Arrays.stream(BusinessCacheEnum.values()).filter(businessCacheEnum 
                -> businessCacheEnum.param.equals(param)).findFirst()).orElseGet(null).getCacheKeyPrefix();
    }

    BusinessCacheEnum(String param, String cacheKey) {
        this.param = param;
        this.cacheKeyPrefix = cacheKey;
    }

    public String getParam() {
        return param;
    }

    public String getCacheKeyPrefix() {
        return cacheKeyPrefix;
    }
}

