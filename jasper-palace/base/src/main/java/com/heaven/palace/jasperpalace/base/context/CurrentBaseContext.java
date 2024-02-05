package com.heaven.palace.jasperpalace.base.context;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author :zhoushengen
 * @date : 2022/12/29
 * 当前线程基础上下文
 */
public class CurrentBaseContext {

    /**
     * 当前线程持有token信息
     */
    private static final String KEY_USER_TOKEN = "currentThreadUserToken";

    /**
     * 当前线程持有客户端ID
     */
    private static final String KEY_CLIENT_ID = "currentThreadClientId";

    /**
     * 当前线程持有组织编码
     */
    private static final String KEY_ORGANIZATION_CODE = "currentThreadOrgCode";

    /**
     * 当前线程持有用户信息
     */
    private static final String KEY_USER_CACHE = "currentThreadUserCache";

    private static ThreadLocal<HashMap<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    static {{
    THREAD_LOCAL.set(new HashMap<>(1));
    }
    }
    
    public static void set(String key, Object value){
        HashMap<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(1);
            THREAD_LOCAL.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        HashMap<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(1);
            THREAD_LOCAL.set(map);
        }
        return map.get(key);
    }
    
    public static Long getUserId(){
        UserCache userCache = (UserCache) get(KEY_USER_CACHE);
        return null == userCache ? null : userCache.getUserId();
    }
    
    public static String getUserName(){
        UserCache userCache = (UserCache) get(KEY_USER_CACHE);
        return null == userCache ? null : userCache.getUsername();
    }

    public static String getOrgCode(){
        return (String)get(KEY_ORGANIZATION_CODE);
    }

    public static HashMap<String, Object> getAll(){
        return THREAD_LOCAL.get();
    }

    public static void setAll(HashMap<String, Object> map){
        Object userCacheObj = map.get(KEY_USER_CACHE);
        if (null != userCacheObj) {
            map.put(KEY_USER_CACHE, JSONObject.toJavaObject((JSONObject)userCacheObj, UserCache.class));
        }
        THREAD_LOCAL.set(map);
    }

    public static String getClientId(){
        return (String)get(KEY_CLIENT_ID);
    }

    public static String getUserToken(){
        return (String)get(KEY_USER_TOKEN);
    }
    public static void setUserToken(String userToken){
        set(KEY_USER_TOKEN, userToken);
    }
    public static void setUserCache(UserCache userCache){
        set(KEY_USER_CACHE, userCache);
    }
    public static void setClientId(String clientId){
        set(KEY_CLIENT_ID, clientId);
    }
    public static void setOrgCode(String orgCode){
        set(KEY_ORGANIZATION_CODE, orgCode);
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
