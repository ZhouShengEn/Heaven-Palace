package com.heaven.palace.purplecloudpalace.core.log;

import com.heaven.palace.purplecloudpalace.util.SpringContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;


/**
 * 被修改的bean临时存放的地方
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
public class LogObjectHolder implements Serializable {

    private Object object = null;

    public void set(Object obj) {
        this.object = obj;
    }

    public Object get() {
        return object;
    }

    public static LogObjectHolder me() {
        LogObjectHolder bean = SpringContextHolder.getBean(LogObjectHolder.class);
        return bean;
    }
}
