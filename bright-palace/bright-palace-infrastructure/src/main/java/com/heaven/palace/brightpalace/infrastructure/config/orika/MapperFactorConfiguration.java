package com.heaven.palace.brightpalace.infrastructure.config.orika;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.infrastructure.entity.BaseUserDO;
import com.heaven.palace.purplecloudpalace.config.orika.AbstractMapperFactoryConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhoushengen
 * @Description: orika映射配置
 * @DateTime: 2024/1/17 16:26
 **/
@Configuration
public class MapperFactorConfiguration extends AbstractMapperFactoryConfiguration {
    @Override
    protected void addFluidMapper() {
        getMapperFactory().classMap(UserAggregate.class, BaseUserDO.class)
            .field("id.id", "id")
            .field("username.value", "username")
            .field("password.value", "password")
            .field("mobilePhone.value", "mobilePhone")
            .field("email.value", "email")
            .byDefault().register();
        // getMapperFactory().classMap(UserRegisterVO.class, UserAggregate.class)
        //     .field("username", "username.value")
        //     .field("password", "password.value")
        //     .field("mobilePhone", "mobilePhone.value")
        //     .field("email", "email.value")
        //     .byDefault().register();
    }

    @Override
    protected void addConverter() {

    }
}
