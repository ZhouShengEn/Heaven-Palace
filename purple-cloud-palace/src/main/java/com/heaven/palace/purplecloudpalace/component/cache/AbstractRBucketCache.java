package com.heaven.palace.purplecloudpalace.component.cache;

import com.heaven.palace.jasperpalace.base.cache.AbstractCache;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.purplecloudpalace.component.cache.param.RedisCacheParam;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

/**
 * @author :zhoushengen
 * @date : 2022/8/19
 */
public abstract class AbstractRBucketCache<K extends CacheParam, V> extends AbstractCache<K, V> {

    @Resource
    private RedissonClient redissonClient;


    @Override
    public V getFromCache(K cacheParam) {
        RBucket<V> bucket = getVrBucketWithCacheParam(cacheParam);
        return bucket.get();
        
    }


    @Override
    public void setToCache(K cacheParam, V value) {
        RBucket<V> bucket = getVrBucketWithCacheParam(cacheParam);
        bucket.delete();
        if (ObjectUtils.allNotNull(cacheParam.getTimeToLive(), cacheParam.getTimeUnit())){
            bucket.set(value, cacheParam.getTimeToLive(), cacheParam.getTimeUnit());
        }else {
            bucket.set(value);
        }

    }

    @Override
    public void removeFromCache(K cacheParam) {
        RBucket<V> bucket = getVrBucketWithCacheParam(cacheParam);
        bucket.delete();
    }

    private RBucket<V> getVrBucketWithCacheParam(K cacheParam) {
        String primaryCacheKey = this.getPrimaryCacheKey(cacheParam);
        Codec codec = null;
        if (cacheParam instanceof RedisCacheParam) {
            codec = ((RedisCacheParam) cacheParam).getCodec();
        }
        RBucket<V> bucket = null != codec
                ?redissonClient.getBucket(primaryCacheKey, codec):redissonClient.getBucket(primaryCacheKey);
        return bucket;
    }

    @Override
    public Lock getLock(String cacheLockKey) {
        return redissonClient.getLock(cacheLockKey);
    }

    @Override
    public long getCacheRemainToLive(K cacheParam) {
        RBucket<V> bucket = getVrBucketWithCacheParam(cacheParam);
        return bucket.remainTimeToLive();
    }
}
