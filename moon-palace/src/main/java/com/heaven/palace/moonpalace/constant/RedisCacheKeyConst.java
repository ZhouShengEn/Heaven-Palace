package com.heaven.palace.moonpalace.constant;

/**
 * @author :zhoushengen
 * @date : 2022/9/8
 */
public interface RedisCacheKeyConst {
    
    String BASE_CACHE_KEY_PREFIX = "generator:";

    /**      custom       **/
    String CUSTOM_CACHE_KEY_PREFIX = BASE_CACHE_KEY_PREFIX + "custom:";
    String CUSTOM_ENV_CACHE_KEY_PREFIX = CUSTOM_CACHE_KEY_PREFIX + "env:";

    /**      custom_resource_authority      **/
    String CUSTOM_RESOURCE_AUTHORITY_ENV_CACHE_KEY_PREFIX = CUSTOM_ENV_CACHE_KEY_PREFIX + "custom-resource-authority:";

    /**      custom_element       **/
    String CUSTOM_ELEMENT_ENV_CACHE_KEY_PREFIX = CUSTOM_ENV_CACHE_KEY_PREFIX + "custom-element:";

    /**      gen       **/
    String GEN_DYNAMIC_DATASOURCE_KEY_PREFIX = BASE_CACHE_KEY_PREFIX + "datasource:";
}
