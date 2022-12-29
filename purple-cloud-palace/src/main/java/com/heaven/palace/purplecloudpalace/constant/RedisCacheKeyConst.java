package com.heaven.palace.purplecloudpalace.constant;

/**
 * @author :zhoushengen
 * @date : 2022/9/8
 */
public interface RedisCacheKeyConst {
    
    public static final String BASE_CACHE_KEY_PREFIX = "purple-cloud-palace:"; 
    public static final String BUSINESS_CACHE_KEY_PREFIX = BASE_CACHE_KEY_PREFIX + "business:"; 
    public static final String BUSINESS_ENV_CACHE_KEY_PREFIX = BUSINESS_CACHE_KEY_PREFIX + "env:";
    public static final String BUSINESS_DICT_ITEM_ENV_CACHE_KEY_PREFIX = BUSINESS_ENV_CACHE_KEY_PREFIX + "dict-item:";
    public static final String BUSINESS_DICT_TYPE_ENV_CACHE_KEY_PREFIX = BUSINESS_ENV_CACHE_KEY_PREFIX + "dict-type:";
    
}
