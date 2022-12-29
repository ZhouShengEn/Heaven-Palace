package com.heaven.palace.purplecloudpalace.mutidatasource.annotion;

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

    String name() default "";
    
    String businessCacheKey() default "";
}
