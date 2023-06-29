package com.heaven.palace.purplecloudpalace.isolation.annotation;


import com.heaven.palace.purplecloudpalace.isolation.enums.DataIsolationEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 * 数据接口隔离，是否允许查询非当前用户数据
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface DataIsolation {
    
    boolean open() default true;
    
    // 隔离级别
    DataIsolationEnum.LEVEL level() default DataIsolationEnum.LEVEL.ALL;
}
