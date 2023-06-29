package com.heaven.palace.moonpalace.util;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * 唯一id生成器
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class IdGenerator {

    public static String getId() {
        return String.valueOf(IdWorker.getId());
    }

    public static long getIdLong() {
        return IdWorker.getId();
    }
}
