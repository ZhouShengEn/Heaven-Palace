package com.heaven.palace.moonpalace.config.orika;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :zhoushengen
 * @date : 2022/9/7
 * orika对象映射业务抽象类
 */
@Configuration
public abstract class AbstractMapperFactoryConfiguration {

    private static MapperFactory mapperFactory;

    @Bean
    protected static MapperFactory getMapperFactory() {
        if (null == mapperFactory) {
            mapperFactory = new DefaultMapperFactory.Builder().build();
        }
        return mapperFactory;

    }

    public AbstractMapperFactoryConfiguration() {
        addConverter();
        addFluidMapper();
    }

    /**
     * 添加classMap，业务字段映射
     */
    abstract protected void addFluidMapper();

    /**
     * 添加业务转换器
     */
    abstract protected void addConverter();


}
