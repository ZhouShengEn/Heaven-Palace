package com.heaven.palace.purplecloudpalace.component.cache;

import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import org.springframework.stereotype.Component;

/**
 * @Author: zhoushengen
 * @Description: 默认对象缓存
 * @DateTime: 2024/1/22 15:51
 **/
@Component
public class DefaultObjectCache extends AbstractRBucketCache<CacheParam, Object> {

    @Override
    protected Object getFromOther(CacheParam cacheParam) {
        return null;
    }
    public <V> V getFromCache(CacheParam cacheParam, Class<V> clazz) {
        Object fromCache = getFromCache(cacheParam);
        return null != fromCache ? (V)fromCache : null;
    }
    public <V> void setToCache(CacheParam cacheParam, V value, Class<V> clazz) {
        setToCache(cacheParam, value);
    }
}
