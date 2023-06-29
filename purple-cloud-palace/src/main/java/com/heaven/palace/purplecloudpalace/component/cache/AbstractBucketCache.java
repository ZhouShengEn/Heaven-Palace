package com.heaven.palace.purplecloudpalace.component.cache;

import com.heaven.palace.purplecloudpalace.component.cache.param.CacheParam;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RBucket;

/**
 * @author :zhoushengen
 * @date : 2022/8/19
 */
public abstract class AbstractBucketCache<K extends CacheParam, V> extends AbstractCache<K, V> {
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
        RBucket<V> bucket = null != cacheParam.getCodec()
                ?redissonClient.getBucket(primaryCacheKey, cacheParam.getCodec()):redissonClient.getBucket(primaryCacheKey);
        return bucket;
    }
}
