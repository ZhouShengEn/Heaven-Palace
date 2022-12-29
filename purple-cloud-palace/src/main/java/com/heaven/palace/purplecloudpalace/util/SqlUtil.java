package com.heaven.palace.purplecloudpalace.util;

import java.util.List;

/**
 * sql语句工具类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class SqlUtil {

    /**
     * @Description 根据集合的大小，输出相应个数"?"
     * @author ZhouShengEn
     */
    public static String parse(List<?> list) {
        String str = "";
        if (list != null && list.size() > 0) {
            str = str + "?";
            for (int i = 1; i < list.size(); i++) {
                str = str + ",?";
            }
        }
        return str;
    }

}
