package com.heaven.palace.moonpalace.mutidatasource.annotion;

import com.heaven.palace.moonpalace.constant.BusinessLogConst;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多数据源标识
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataSource {
    /**
     * 业务缓存key
     * @return
     */
    BusinessCacheEnum businessCacheKey();

    /**
     * sql 操作类型 需要记录日志时配置
     * @return
     */
    BusinessLogConst.SqlTypeEnum logSqlType() default BusinessLogConst.SqlTypeEnum.NONE;
}
