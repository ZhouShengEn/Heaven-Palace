package com.heaven.palace.purplecloudpalace.util;

import java.util.regex.Pattern;

/**
 * 正则工具类
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class PatternUtil {
    /**
     * 只允许中文
     */
    private static final Pattern chineseUTF8 = Pattern.compile("[\u4E00-\u9FA5]*");
    /**
     * 验证手机号
     */
    private static final Pattern phone       = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$");
    /**
     * 验证座机号
     */
    private static final Pattern mobile      = Pattern.compile("([0][1-9]{2,3}-)?[0-9]{5,10}$");
    /**
     * 验证邮箱
     */
    private static final Pattern email       = Pattern
        .compile("^w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$");
    /**
     * 验证URL
     */
    private static final Pattern url         = Pattern.compile("^[a-zA-z]+://[^s]*$");

    /**
     * 是否只有中文
     * @param text
     * @return
     */
    public static boolean isChineseUTF8(String text) {
        return chineseUTF8.matcher(text).matches();
    }

    /**
     * 是不是手机号
     * @param text
     * @return
     */
    public static boolean isPhone(String text) {
        return phone.matcher(text).matches();
    }

    /**
     * 是不是电话
     * @param text
     * @return
     */
    public static boolean isMobile(String text) {
        return mobile.matcher(text).matches();
    }

}
