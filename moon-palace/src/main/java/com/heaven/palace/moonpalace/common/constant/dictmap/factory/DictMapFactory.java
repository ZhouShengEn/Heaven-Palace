package com.heaven.palace.moonpalace.common.constant.dictmap.factory;

import com.heaven.palace.moonpalace.common.constant.dictmap.base.AbstractDictMap;
import com.heaven.palace.moonpalace.common.constant.dictmap.base.SystemDict;

/**
 * 字典的创建工厂
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class DictMapFactory {

    private static final String BASE_PATH = "com.heaven.palace.moonpalace.common.constant.dictmap.";

    /**
     * 通过类名创建具体的字典类
     */
    public static AbstractDictMap createDictMap(String className) {
        if ("SystemDict".equals(className)) {
            return new SystemDict();
        } else {
            try {
                Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(BASE_PATH + className);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }
}
