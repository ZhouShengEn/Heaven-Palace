package com.heaven.palace.jasperpalace.base.cache;


import com.heaven.palace.jasperpalace.base.cache.constants.CacheLockConst;
import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author :zhoushengen
 * @date : 2022/8/19
 */
@Slf4j
public abstract class AbstractCache<K extends CacheParam, V> implements ICache<K, V> {
    
    private final ThreadLocal<Integer> retryThreadLocal = ThreadLocal.withInitial(() -> 1);
    
    // 最大重试次数
    private static final Integer MAX_RETRY_TIMES = 2;

    /**
     * 缓存操作线程池
     */
    protected static final ThreadPoolExecutor setDataToRedisCachePool;

    static {
        // 异步设置缓存线程池
        setDataToRedisCachePool = new ThreadPoolExecutor(10, 30, 10L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
        // 核心线程回收
        setDataToRedisCachePool.allowCoreThreadTimeOut(Boolean.TRUE);
    }

    public abstract Lock getLock(String cacheLockKey);

    @Override
    public final V get(K cacheParam) {
        // 简易布隆过滤器，防止缓存穿透
        if (this.bloomFilter(cacheParam)) {
            return null;
        }
        try {
            V v;
            if (null == (v = getFromCache(cacheParam))) {
                String cacheLockKey = getCacheLockKey(cacheParam);
                Lock lock = getLock(cacheLockKey);
                try {
                    boolean lockResult = lock.tryLock(180, TimeUnit.SECONDS);
                    if (!lockResult) {
                        throw new RuntimeException(cacheLockKey + "lock expire");
                    }
                    if (null == (v = this.getFromCache(cacheParam))) {
                        v = getAndSet(cacheParam);
                    }
                } finally {
                    lock.unlock();
                }
            }
            return v;

        }catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException)e;
            }
            Integer retryTimes = retryThreadLocal.get();
            log.error("get cache error! primaryKey:{}, retryTimes:{}", cacheParam.getPrimaryKey(), retryTimes);
            if (retryTimes > MAX_RETRY_TIMES){
                return null;
            }
            retryThreadLocal.set(++retryTimes);
            // 异常再次重试时不考虑缓存击穿
            return this.getAndSet(cacheParam);
        }
        
    }

    @Override
    public void set(K cacheParam, V value) {
        // 过滤器, 防非法穿透
        if (this.bloomFilter(cacheParam)) {
            // 异步设置缓存
            this.setAsync(cacheParam, value);
        }

    }

    @Override
    public void remove(K cacheParam) {
        this.removeFromCache(cacheParam);
    }

    /**
     * 空键过滤
     * @param cacheParam
     * @return
     */
    protected boolean bloomFilter(K cacheParam) {
        return StringUtils.isAnyBlank(cacheParam.getPrefixKey(), cacheParam.getPrimaryKey());
    }

    /**
     * 用于获取缓存时锁的key
     * @param cacheParam
     * @return
     */
    private String getCacheLockKey(K cacheParam) {
        return new StringBuilder(CacheLockConst.CACHE_GET_LOCK_KEY).append(cacheParam.getPrefixKey())
                .append(cacheParam.getPrimaryKey()).toString();
    }

    /**
     * 用于设置缓存时锁的key
     * @param cacheParam
     * @return
     */
    private String setCacheLockKey(K cacheParam) {
        return new StringBuilder(CacheLockConst.CACHE_SET_LOCK_KEY).append(cacheParam.getPrefixKey())
                .append(cacheParam.getPrimaryKey()).toString();
    }

    
    protected V getAndSet(K cacheParam) {
        try {
            if (bloomFilter(cacheParam)) {
                return null;
            }
            V value = this.getData(cacheParam);
            // 异步设置缓存
            this.setAsync(cacheParam, value);
            // 由于异步设置缓存，为防止出现外部修改缓存造成数据错误
            return (V) SerializationUtils.clone((Serializable) value);
        } catch (Exception e) {
            log.info("cache get and set error！cachePrimaryKey:{}", getPrimaryCacheKey(cacheParam), e);
            if (e instanceof BusinessException) {
                throw (BusinessException)e;
            }
            return null;
        }
    }

    /**
     * 获取数据
     *
     * @param cacheParam
     * @return
     */
    private V getData(K cacheParam) {
        try {
            return this.getFromOther(cacheParam);
        } catch (Exception e) {
            log.info("cache get from DB error！cachePrimaryKey:{}", getPrimaryCacheKey(cacheParam), e);
            if (e instanceof BusinessException) {
                throw (BusinessException)e;
            }
            return null;
        }

    }

    private void setAsync(K cacheParam, V value) {
        setDataToRedisCachePool.submit(new SetCacheTask(cacheParam, value, this));
    }

    /**
     * 获取全局缓存唯一key
     * @param cacheParam
     * @return
     */
    protected String getPrimaryCacheKey(K cacheParam) {
        return new StringBuilder(cacheParam.getPrefixKey()).append(cacheParam.getPrimaryKey()).toString();
    }
    

    /**
     * 从缓存中获取数据
     *
     * @param cacheParam
     * @return
     */
    protected abstract V getFromCache(K cacheParam);

    /**
     * 将数据设置在缓存中
     *
     * @param cacheParam
     * @param value
     */
    protected abstract void setToCache(K cacheParam, V value);

    /**
     * 移除缓存
     *
     * @param cacheParam
     */
    protected abstract void removeFromCache(K cacheParam);

    /**
     * 通过非缓存的方式获取数据
     *
     * @param cacheParam
     * @return
     */
    protected abstract V getFromOther(K cacheParam);

    /**
     * 获取缓存剩余存活时间
     *
     * @param cacheParam
     * @return
     */
    public abstract long getCacheRemainToLive(K cacheParam);


    private class SetCacheTask implements Runnable {

        private final K cacheParam;
        private V value;
        private final AbstractCache<K, V> abstractCache;

        public SetCacheTask(K cacheParam, V value, AbstractCache<K, V> abstractCache) {
            this.cacheParam = cacheParam;
            this.value = value;
            this.abstractCache = abstractCache;
        }

        @Override
        public void run() {
            String setCacheLockKey = this.abstractCache.setCacheLockKey(cacheParam);

            Lock lock = getLock(setCacheLockKey);
            try {
                boolean tryLock = lock.tryLock(180, TimeUnit.SECONDS);
                if (tryLock) {
                    if (null != value || (null != (value = this.abstractCache.getData(cacheParam)))) {
                        this.abstractCache.setToCache(cacheParam, value);
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }


}
