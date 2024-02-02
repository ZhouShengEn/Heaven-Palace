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
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(1);
            THREAD_LOCAL.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(1);
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

    public static String getOrgCode(){
        return (String)get(CommonConst.KEY_ORGANIZATION_CODE);
    }

    public static String getClientId(){
        return (String)get(CommonConst.KEY_CLIENT_ID);
    }

    public static String getUserToken(){
        return (String)get(CommonConst.KEY_USER_TOKEN);
    }
    public static void setUserToken(String userToken){
        set(CommonConst.KEY_USER_TOKEN, userToken);
    }
    public static void setUserCache(UserCache userCache){
        set(CommonConst.KEY_USER_CACHE, userCache);
    }
    public static void setClientId(String clientId){
        set(CommonConst.KEY_CLIENT_ID, clientId);
    }
    public static void setOrgCode(String orgCode){
        set(CommonConst.KEY_ORGANIZATION_CODE, orgCode);
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
    }

}
