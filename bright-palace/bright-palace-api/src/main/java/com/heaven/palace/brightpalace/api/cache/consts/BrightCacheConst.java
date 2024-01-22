package com.heaven.palace.brightpalace.api.cache.consts;


import com.heaven.palace.jasperpalace.base.cache.param.ICacheParam;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoushengen
 * @Description: 光明宫缓存枚举
 * @DateTime: 2024/1/22 15:19
 **/
public class BrightCacheConst {

    public static final String BRIGHT_PALACE_CACHE_PREFIX = "bright-palace:";

    public enum CommonEnum implements ICacheParam {
        ;

        private final String prefixKey;

        private final Long timeToLive;

        private final TimeUnit timeUnit;


        CommonEnum(String prefixKey, Long timeToLive, TimeUnit timeUnit) {
            this.prefixKey = prefixKey;
            this.timeToLive = timeToLive;
            this.timeUnit = timeUnit;
        }

        @Override
        public String prefixKey() {
            return this.prefixKey;
        }

        @Override
        public Long timeToLive() {
            return this.timeToLive;
        }

        @Override
        public TimeUnit timeUnit() {
            return this.timeUnit;
        }

    }
}
