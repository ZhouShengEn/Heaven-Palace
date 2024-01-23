package com.heaven.palace.brightpalace.domain.factory.auth;

import com.heaven.palace.brightpalace.domain.business.oauth2.service.Oauth2DomainService;
import com.heaven.palace.jasperpalace.base.factory.AbstractMultiFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: zhoushengen
 * @Description: 统一认证多类型授权工厂类
 * @DateTime: 2024/1/23 17:03
 **/
@Component
public class MultiOAuth2TypeFactory extends AbstractMultiFactory<Oauth2DomainService> {

}
