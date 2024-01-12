package com.heaven.palace.moonpalace.isolation.context;


import com.heaven.palace.moonpalace.isolation.annotation.DataIsolation;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 */
public class DataIsolationContext {
    public static ThreadLocal<DataIsolation> local = new ThreadLocal<>();
    
}
