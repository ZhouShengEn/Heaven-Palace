package com.heaven.palace.moonpalace.common.constant.dictmap;

import com.heaven.palace.moonpalace.common.constant.dictmap.base.AbstractDictMap;

/**
 * 日志的字典
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class LogDict extends AbstractDictMap {

    @Override
    public void init() {
        put("tips", "备注");
    }

    @Override
    protected void initBeWrapped() {

    }
}
