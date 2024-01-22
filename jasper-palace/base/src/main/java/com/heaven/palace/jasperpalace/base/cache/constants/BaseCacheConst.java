package com.heaven.palace.jasperpalace.base.cache.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author :zhoushengen
 * @date : 2022/8/16
 */
@Component
public class BaseCacheConst {

    
    public static String BASE_APPLICATION_CACHE_PRE_KEY;
    
    @Value("${spring.application.name}")
    public void setBaseApplicationCachePreKey(String baseApplicationCachePreKey) {
        BASE_APPLICATION_CACHE_PRE_KEY = baseApplicationCachePreKey.concat(":");
    }
}
