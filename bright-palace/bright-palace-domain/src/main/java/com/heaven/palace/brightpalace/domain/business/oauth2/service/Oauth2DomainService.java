package com.heaven.palace.brightpalace.domain.business.oauth2.service;

import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.AuthAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.jasperpalace.base.factory.service.MultiServiceInterface;

/**
 * @Author: zhoushengen
 * @Description: 统一认证领域服务接口
 * @DateTime: 2024/1/23 16:41
 **/
public interface Oauth2DomainService extends MultiServiceInterface {

    /**
     * 用户登录-密码&手机号
     * @param userAggregate
     */
    AuthAggregate loginByPasswordAndPhone(UserAggregate userAggregate);
}
