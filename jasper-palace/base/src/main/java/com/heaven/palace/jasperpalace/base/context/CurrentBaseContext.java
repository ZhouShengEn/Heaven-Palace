package com.heaven.palace.jasperpalace.base.context;


import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import lombok.Data;

import java.io.Serializable;
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
    THREAD_LOCAL.set(new HashMap<>(1));
    }
    }
    
    public static void set(String key, Object value){
        THREAD_LOCAL.get().put(key, value);
    }
    
    public static Object get(String key){
        return THREAD_LOCAL.get().get(key);
    }
    
    public static long getUserId(){
        return ((UserCache) THREAD_LOCAL.get().get(CommonConst.KEY_USER_CACHE)).getUserId();
    }
    
    public static String getUserName(){
        return ((UserCache) THREAD_LOCAL.get().get(CommonConst.KEY_USER_CACHE)).getUsername();
    }
    
    public static String getUserToken(){
        return (String) THREAD_LOCAL.get().get(CommonConst.KEY_USER_TOKEN);
    }
    public static void setUserToken(String userToken){
        THREAD_LOCAL.get().put(CommonConst.KEY_USER_TOKEN, userToken);
    }
    public static void setUserCache(UserCache userCache){
        THREAD_LOCAL.get().put(CommonConst.KEY_USER_CACHE, userCache);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

    @Data
    public static class UserCache implements Serializable {
        private static final long serialVersionUID = 9040610251351804329L;

        /**
         * 用户id
         */
        private long userId;

        /**
         * 用户名称
         */
        private String username;
    }

}
