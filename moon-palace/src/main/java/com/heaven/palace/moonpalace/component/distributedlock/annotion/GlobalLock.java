package com.heaven.palace.moonpalace.component.distributedlock.annotion;


import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/3 11:02
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface GlobalLock {

    /**
     * 业务缓存枚举
     * @return
     */
    BusinessCacheEnum businessCacheKey() default BusinessCacheEnum.GENERATOR;

    /**
     * 锁key
     * @return
     */
    String lockKey() default "";
}
