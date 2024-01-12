package com.heaven.palace.purplecloudpalace.component.cache.param;

import org.redisson.client.codec.Codec;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoushengen
 * @DateTime: 2023/8/2 18:36
 **/
public class RMapCacheParam<K> extends CacheParam {

    private K mapKey;

    public K getMapKey() {
        return mapKey;
    }

    public void setMapKey(K mapKey) {
        this.mapKey = mapKey;
    }

    public RMapCacheParam(K mapKey, String prefixKey, String primaryKey, Codec codec, Long timeToLive,
        TimeUnit timeUnit) {
        super(prefixKey, primaryKey, codec, timeToLive, timeUnit);
        this.mapKey = mapKey;
    }

    public RMapCacheParam(String prefixKey, String primaryKey, Codec codec, Long timeToLive,
        TimeUnit timeUnit) {
        super(prefixKey, primaryKey, codec, timeToLive, timeUnit);
    }
}
