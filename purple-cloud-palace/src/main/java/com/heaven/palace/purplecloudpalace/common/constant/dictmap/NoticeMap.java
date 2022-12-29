package com.heaven.palace.purplecloudpalace.common.constant.dictmap;

import com.heaven.palace.purplecloudpalace.common.constant.dictmap.base.AbstractDictMap;

/**
 * 通知的映射
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class NoticeMap extends AbstractDictMap {

    @Override
    public void init() {
        put("title", "标题");
        put("content", "内容");
    }

    @Override
    protected void initBeWrapped() {
    }
}
