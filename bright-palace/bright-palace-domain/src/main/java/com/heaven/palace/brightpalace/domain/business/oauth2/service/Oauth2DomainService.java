package com.heaven.palace.brightpalace.domain.business.oauth2.service;

import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.AuthTokenAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;

/**
 * @Author: zhoushengen
 * @Description: 统一认证领域服务接口
 * @DateTime: 2024/1/23 16:41
 **/
public interface Oauth2DomainService {

    /**
     * 用户登录-密码&手机号
     * @param userAggregate
     */
    AuthTokenAggregate loginByPasswordAndPhone(UserAggregate userAggregate);
}
