package com.heaven.palace.brightpalace.infrastructure.cache.oauth2;

import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.AuthTokenAggregate;
import com.heaven.palace.brightpalace.infrastructure.cache.oauth2.param.Oauth2CacheParam;
import com.heaven.palace.purplecloudpalace.component.cache.AbstractRMapCache;
import org.springframework.stereotype.Service;

/**
 * @Author: zhoushengen
 * @Description: 统一认证缓存
 * @DateTime: 2024/2/1 17:31
 **/
@Service
public class Oauth2Cache extends AbstractRMapCache<String, Oauth2CacheParam, AuthTokenAggregate> {

    /**
     * 这里有需要可以将token持久化，在懒加载缓存时参考 {@link com.heaven.palace.jasperpalace.base.cache.AbstractCache#get}
     * @param cacheParam
     * @return
     */
    @Override
    protected AuthTokenAggregate getFromOther(Oauth2CacheParam cacheParam) {
        return null;
    }
}
