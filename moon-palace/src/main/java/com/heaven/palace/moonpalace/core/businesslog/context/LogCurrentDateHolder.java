package com.heaven.palace.moonpalace.core.businesslog.context;


import java.io.Serializable;

/**
 * @author :zhoushengen
 * @date : 2023/3/3
 * 当前线程的日志相关变量，用于确认子线程的主线程是否是同一个
 */
public class LogCurrentDateHolder implements Serializable {

    private static final long serialVersionUID = -5129308228828155531L;
    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void set(String currentDate) {
        context.set(currentDate);
    }

    public static String get() {
        return context.get();
    }
    
    public static void clear() {
        context.remove();
    }

}
