package com.heaven.palace.moonpalace.common.constant.dictmap.base;

import java.util.HashMap;

/**
 * 字典映射抽象类
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public abstract class AbstractDictMap {

    protected HashMap<String, String> dictory = new HashMap<String, String>();
    protected HashMap<String, String> fieldWarpperDictory = new HashMap<String, String>();

    public AbstractDictMap() {
        put("id", "主键id");
        init();
        initBeWrapped();
    }

    /**
     * 初始化字段英文名称和中文名称对应的字典
     *
     * @author ZhouShengEn
     * @Date 2017/5/9 19:39
     */
    public abstract void init();

    /**
     * 初始化需要被包装的字段(例如:性别为1:男,2:女,需要被包装为汉字)
     *
     * @author ZhouShengEn
     * @Date 2017/5/9 19:35
     */
    protected abstract void initBeWrapped();

    public String get(String key) {
        return this.dictory.get(key);
    }

    public void put(String key, String value) {
        this.dictory.put(key, value);
    }

    public String getFieldWarpperMethodName(String key) {
        return this.fieldWarpperDictory.get(key);
    }

    public void putFieldWrapperMethodName(String key, String methodName) {
        this.fieldWarpperDictory.put(key, methodName);
    }
}
