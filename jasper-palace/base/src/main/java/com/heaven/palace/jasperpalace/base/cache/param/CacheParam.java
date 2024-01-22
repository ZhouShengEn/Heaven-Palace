package com.heaven.palace.jasperpalace.base.cache.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
     * 失效时间
     */
    private Long timeToLive;

    /**
     * 失效时间单位
     */
    private TimeUnit timeUnit;

    public CacheParam(ICacheParam param, String primaryKey) {
        this.prefixKey = param.prefixKey();
        this.primaryKey = primaryKey;
        this.timeToLive = param.timeToLive();
        this.timeUnit = param.timeUnit();
    }

    public CacheParam(ICacheParam param) {
        this.prefixKey = param.prefixKey();
        this.timeToLive = param.timeToLive();
        this.timeUnit = param.timeUnit();
    }
}
