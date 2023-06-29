package com.heaven.palace.moonpalace.common.constant.dictmap;

import com.heaven.palace.moonpalace.common.constant.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class DictMap extends AbstractDictMap {

    @Override
    public void init() {
        put("dictId", "字典名称");
        put("dictName", "字典名称");
        put("dictValues", "字典内容");
    }

    @Override
    protected void initBeWrapped() {

    }
}
