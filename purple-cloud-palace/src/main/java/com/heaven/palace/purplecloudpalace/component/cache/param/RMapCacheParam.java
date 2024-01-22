package com.heaven.palace.purplecloudpalace.component.cache.param;

import org.redisson.client.codec.Codec;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoushengen
 * @DateTime: 2023/8/2 18:36
 **/
public class RMapCacheParam<K> extends RedisCacheParam {

    private K mapKey;

    public K getMapKey() {
        return mapKey;
    }

    public void setMapKey(K mapKey) {
        this.mapKey = mapKey;
    }

    public RMapCacheParam(K mapKey, String prefixKey, String primaryKey, Long timeToLive,
        TimeUnit timeUnit, Codec codec) {
        super(prefixKey, primaryKey, timeToLive, timeUnit, codec);
        this.mapKey = mapKey;
    }

    public RMapCacheParam(String prefixKey, String primaryKey, Long timeToLive,
        TimeUnit timeUnit, Codec codec) {
        super(prefixKey, primaryKey, timeToLive, timeUnit, codec);
    }
}
