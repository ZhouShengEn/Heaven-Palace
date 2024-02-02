package com.heaven.palace.purplecloudpalace.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 幂等注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RepeatSubmit {

    /**
     * 允许重复提交时间 支持nacos配置动态更新
     * @return
     */
    String allowRepeatTime() default "3";

    /**
     * 重复提交时间单位
     * @return
     */
    TimeUnit repeatUnit() default TimeUnit.SECONDS;

    /**
     * 重复提交展示文案
     * @return
     */
    String repeatMsg() default "请勿重复提交！";

    /**
     * 是否等待接口操作完成，为true则allowRepeatTime字段失效
     * @return
     */
    boolean waitForComplete() default false;

}
