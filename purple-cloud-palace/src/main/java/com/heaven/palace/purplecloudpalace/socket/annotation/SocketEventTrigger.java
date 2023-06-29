package com.heaven.palace.purplecloudpalace.socket.annotation;


import com.heaven.palace.purplecloudpalace.socket.context.SocketMessageSendConst;
import com.heaven.palace.purplecloudpalace.socket.context.SocketNamespaceEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author :zhoushengen
 * @date : 2023/3/21
 * socket事件触发器
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SocketEventTrigger {

    SocketNamespaceEnum[] namespaces();

    SocketMessageSendConst.EventMethodMappingEnum event();
    
    String roomSpEL() default "";
}
