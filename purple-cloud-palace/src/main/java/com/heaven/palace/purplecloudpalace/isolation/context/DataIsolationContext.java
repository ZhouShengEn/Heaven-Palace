package com.heaven.palace.purplecloudpalace.isolation.context;


import com.heaven.palace.purplecloudpalace.isolation.annotation.DataIsolation;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 */
public class DataIsolationContext {
    public static ThreadLocal<DataIsolation> local = new ThreadLocal<>();
    
}
