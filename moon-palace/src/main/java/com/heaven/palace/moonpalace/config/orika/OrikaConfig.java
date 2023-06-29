package com.heaven.palace.moonpalace.config.orika;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.common.persistence.vo.BusinessOperationLogVo;
import com.heaven.palace.moonpalace.config.orika.convert.Date2StringForDateConvert;
import com.heaven.palace.moonpalace.config.orika.convert.Date2StringForDateTimeConvert;
import com.heaven.palace.moonpalace.config.orika.convert.String2IntConvert;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :zhoushengen
 * @date : 2022/9/5
 */
@Configuration
public class OrikaConfig {
    private static MapperFactory mapperFactory;

    @Bean
    protected static MapperFactory getMapperFactory() {
        if (null == mapperFactory) {
            mapperFactory = new DefaultMapperFactory.Builder().build();
        }
        return mapperFactory;

    }

    public OrikaConfig() {
        addConverter();
        addFluidMapper();
    }

    /**
     * 添加classMap，业务字段映射
     */
    protected void addFluidMapper(){
        getMapperFactory().classMap(BusinessOperationLog.class, BusinessOperationLogVo.class)
                .fieldMap("createTime", "createTime").converter("date2StringForDateTimeConvert").add()
                .fieldMap("createTime", "createTime").converter("date2StringForDateTimeConvert").add()
                .byDefault().register();
    }

    /**
     * 添加业务转换器
     */
    protected void addConverter(){
        getMapperFactory().getConverterFactory().registerConverter("string2IntConvert", new String2IntConvert());
        getMapperFactory().getConverterFactory().registerConverter("date2StringForDateTimeConvert", new Date2StringForDateTimeConvert());
        getMapperFactory().getConverterFactory().registerConverter("date2StringForDateConvert", new Date2StringForDateConvert());

    }
}
