package com.heaven.palace.jasperpalace.base.context;


import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import lombok.Data;
import lombok.experimental.Accessors;

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

    public static Object get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            THREAD_LOCAL.set(map);
        }
        return map.get(key);
    }
    
    public static Long getUserId(){
        UserCache userCache = (UserCache) get(CommonConst.KEY_USER_CACHE);
        return null == userCache ? null : userCache.getUserId();
    }
    
    public static String getUserName(){
        UserCache userCache = (UserCache) get(CommonConst.KEY_USER_CACHE);
        return null == userCache ? null : userCache.getUsername();
    }

    public static String getClientOrgCode(){
        UserCache userCache = (UserCache) get(CommonConst.KEY_USER_CACHE);
        return null == userCache ? null : userCache.getClientOrgCode();
    }

    public static String getClientId(){
        UserCache userCache = (UserCache) get(CommonConst.KEY_USER_CACHE);
        return null == userCache ? null : userCache.getClientId();
    }

    public static String getUserToken(){
        return (String)get(CommonConst.KEY_USER_TOKEN);
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
    @Accessors(chain = true)
    public static class UserCache implements Serializable {
        private static final long serialVersionUID = 9040610251351804329L;

        /**
         * 用户id
         */
        private Long userId;

        /**
         * 用户名称
         */
        private String username;

        /**
         * 客户端所在组织编码
         */
        private String clientOrgCode;

        /**
         * 客户端id
         */
        private String clientId;
    }

}
