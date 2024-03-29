package com.heaven.palace.moonpalace.util;

import javax.servlet.http.HttpSession;

/**
 * 非Controller中获取当前session的工具类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class HttpSessionHolder {

    private static ThreadLocal<HttpSession> tl = new ThreadLocal<HttpSession>();

    public static void put(HttpSession s) {
        tl.set(s);
    }

    public static HttpSession get() {
        return tl.get();
    }

    public static void remove() {
        tl.remove();
    }
}
