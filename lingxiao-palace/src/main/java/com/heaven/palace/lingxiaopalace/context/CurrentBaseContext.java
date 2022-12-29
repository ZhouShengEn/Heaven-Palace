package com.heaven.palace.lingxiaopalace.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :zhoushengen
 * @date : 2022/12/29
 * 当前线程基础上下文
 */
public class CurrentBaseContext {
    private static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();
    static {{
    THREAD_LOCAL.set(new HashMap<String, Object>(1));
    }
    }
    
    public static void set(String key, Object value){
        THREAD_LOCAL.get().put(key, value);
    }
    
    public static Object get(String key){
        return THREAD_LOCAL.get().get(key);
    }
    
    
}
