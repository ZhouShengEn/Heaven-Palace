package com.heaven.palace.moonpalace.component.cache.param;

import org.redisson.client.codec.Codec;

import java.util.concurrent.TimeUnit;

/**
 * @author :zhoushengen
 * @date : 2022/8/22
 */
public interface ICacheParam {

    /**
     * 缓存前缀
     * @return
     */
    String prefixKey();

    /**
     * 缓存存活时间
     * @return
     */
    Long timeToLive();

    /**
     * 缓存存活单位
     * @return
     */
    TimeUnit timeUnit();

    /**
     * 序列转化类
     * @return
     */
    Codec codec();
    
}
