package com.heaven.palace.brightpalace.infrastructure.cache.oauth2.param;

import com.heaven.palace.brightpalace.infrastructure.cache.oauth2.consts.Oauth2CacheConst.Oauth2CacheEnum;
import com.heaven.palace.purplecloudpalace.component.cache.param.RMapCacheParam;

/**
 * @Author: zhoushengen
 * @Description: 统一认证缓存参数
 * @DateTime: 2024/2/1 17:25
 **/
public class Oauth2CacheParam extends RMapCacheParam<String> {
    public Oauth2CacheParam(Oauth2CacheEnum oauth2CacheEnum, String primaryKey, String mapKey) {
        super(mapKey, oauth2CacheEnum.prefixKey(), primaryKey, oauth2CacheEnum.timeToLive(),
            oauth2CacheEnum.timeUnit(), null);
    }
}
