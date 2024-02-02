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
            .fieldAToB("id.id", "id")
            .fieldAToB("username.value", "username")
            .fieldAToB("password.value", "password")
            .fieldAToB("mobilePhone.value", "mobilePhone")
            .fieldAToB("email.value", "email")
            .fieldBToA("id", "id")
            .fieldBToA("username", "username")
            // .fieldBToA("password", "password")
            .fieldBToA("mobilePhone", "mobilePhone")
            .fieldBToA("email", "email")
            .byDefault().register();
    }

    @Override
    protected void addConverter() {

    }
}
