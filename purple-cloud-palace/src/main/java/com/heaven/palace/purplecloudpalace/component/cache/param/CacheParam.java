package com.heaven.palace.purplecloudpalace.component.cache.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.redisson.client.codec.Codec;

import java.util.concurrent.TimeUnit;

/**
 * @author :zhoushengen
 * @date : 2022/8/11
 */
@Data
@Builder
@AllArgsConstructor
public class CacheParam {

    /**
     * 前缀
     */
    private String prefixKey;

    /**
     * 主键
     */
    private String primaryKey;

    /**
     * 序列化方式
     */
    private Codec codec;

    /**
     * 失效时间
     */
    private Long timeToLive;

    /**
     * 失效时间单位
     */
    private TimeUnit timeUnit;

}
