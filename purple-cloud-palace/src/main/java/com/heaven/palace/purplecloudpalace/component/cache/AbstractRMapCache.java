package com.heaven.palace.purplecloudpalace.component.cache;

import com.heaven.palace.purplecloudpalace.component.cache.param.RMapCacheParam;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RMapCache;

import java.util.Collection;

/**
 * @author :zhoushengen
 * @date : 2022/8/19
 */
public abstract class AbstractRMapCache<T, K extends RMapCacheParam<T>, V> extends AbstractCache<K, V> {
    @Override
    public V getFromCache(K cacheParam) {
        RMapCache<T, V> map = getVrMapWithCacheParam(cacheParam);
        return map.get(cacheParam.getMapKey());
        
    }


    @Override
    public void setToCache(K cacheParam, V value) {
        RMapCache<T, V> map = getVrMapWithCacheParam(cacheParam);
        map.remove(cacheParam.getMapKey());
        if (ObjectUtils.allNotNull(cacheParam.getTimeToLive(), cacheParam.getTimeUnit())){
            map.put(cacheParam.getMapKey(), value, cacheParam.getTimeToLive(), cacheParam.getTimeUnit());
        }else {
            map.put(cacheParam.getMapKey(), value);
        }

    }

    public Collection<V> getAll(K cacheParam) {
        RMapCache<T, V> map = getVrMapWithCacheParam(cacheParam);
        return map.values();
    }

    @Override
    public void removeFromCache(K cacheParam) {
        RMapCache<T, V> map = getVrMapWithCacheParam(cacheParam);
        if (null != cacheParam.getMapKey()) {
            map.remove(cacheParam.getMapKey());
        } else {
            map.delete();
        }

    }

    private RMapCache<T, V> getVrMapWithCacheParam(K cacheParam) {
        String primaryCacheKey = this.getPrimaryCacheKey(cacheParam);
        return null != cacheParam.getCodec()
                ?redissonClient.getMapCache(primaryCacheKey, cacheParam.getCodec()):redissonClient.getMapCache(primaryCacheKey);
    }

}
