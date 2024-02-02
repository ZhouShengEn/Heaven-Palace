package com.heaven.palace.brightpalace.infrastructure.cache.permission.param;

import com.heaven.palace.brightpalace.infrastructure.cache.permission.consts.UserPermissionCacheConst.UserCacheEnum;
import com.heaven.palace.purplecloudpalace.component.cache.param.RedisCacheParam;

/**
 * @Author: zhoushengen
 * @Description: 用户权限缓存参数
 * @DateTime: 2024/1/30 16:29
 **/
public class UserPermissionCacheParam extends RedisCacheParam {

    private Long userId;


    public UserPermissionCacheParam(UserCacheEnum userCacheEnum, Long userId) {
        super(userCacheEnum.prefixKey(), String.valueOf(userId), userCacheEnum.timeToLive(), userCacheEnum.timeUnit(), null);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
