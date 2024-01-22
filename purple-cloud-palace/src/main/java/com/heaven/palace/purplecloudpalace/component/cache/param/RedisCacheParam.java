package com.heaven.palace.purplecloudpalace.component.cache.param;

import com.heaven.palace.jasperpalace.base.cache.param.CacheParam;
import org.redisson.client.codec.Codec;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoushengen
 * @Description: redis缓存参数
 * @DateTime: 2024/1/22 18:16
 **/
public class RedisCacheParam extends CacheParam {

    private Codec codec;

    public RedisCacheParam(String prefixKey, String primaryKey, Long timeToLive, TimeUnit timeUnit, Codec codec) {
        super(prefixKey, primaryKey, timeToLive, timeUnit);
        this.codec = codec;
    }

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}
