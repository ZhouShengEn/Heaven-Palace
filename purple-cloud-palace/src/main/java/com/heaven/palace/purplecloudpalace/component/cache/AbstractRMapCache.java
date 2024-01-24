package com.heaven.palace.purplecloudpalace.component.cache;

import com.heaven.palace.jasperpalace.base.cache.AbstractCache;
import com.heaven.palace.purplecloudpalace.component.cache.param.RMapCacheParam;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.concurrent.locks.Lock;

/**
 * @author :zhoushengen
 * @date : 2022/8/19
 */
public abstract class AbstractRMapCache<T, K extends RMapCacheParam<T>, V> extends AbstractCache<K, V> {

    @Resource
    private RedissonClient redissonClient;

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

    @Override
    public Lock getLock(String cacheLockKey) {
        return redissonClient.getLock(cacheLockKey);
    }

    @Override
    public long getCacheRemainToLive(K cacheParam) {
        RMapCache<T, V> map = getVrMapWithCacheParam(cacheParam);
        return map.remainTimeToLive(cacheParam.getMapKey());
    }
}
