package com.heaven.palace.jasperpalace.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: zhoushengen
 * @Description: 忽略用户认证
 * @DateTime: 2024/1/10 10:01
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface IgnoreUserAuth {
}
