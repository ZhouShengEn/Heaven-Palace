package com.heaven.palace.lingxiaopalace.annotation;


import com.heaven.palace.lingxiaopalace.enums.DataPermissionEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface DataPermission {
    
    boolean open() default true;
    
    // 隔离级别
    DataPermissionEnum.LEVEL level() default DataPermissionEnum.LEVEL.ALL;
}
