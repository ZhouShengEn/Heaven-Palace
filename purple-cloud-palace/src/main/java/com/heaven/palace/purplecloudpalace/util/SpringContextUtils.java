package com.heaven.palace.purplecloudpalace.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author :zhoushengen
 * @date : 2022/9/7
 * Spring上下文工具
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    protected static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == SpringContextUtils.applicationContext){
            SpringContextUtils.applicationContext = applicationContext;
        }

    }


    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }
}
