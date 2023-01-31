package com.heaven.palace.lingxiaopalace.context;


import com.heaven.palace.lingxiaopalace.annotation.DataPermission;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 */
public class DataPermissionContext {
    public static ThreadLocal<DataPermission> local = new ThreadLocal<>();
    
}
