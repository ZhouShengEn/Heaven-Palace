package com.heaven.palace.moonpalace.db.convert.sql;

import com.heaven.palace.moonpalace.model.GenBeanEntity;

/**
 * @author ZhouShengEn on 2017/10/25.
 */
public interface ISqlConvert {

    /**
     * 解析SQL
     */
    public GenBeanEntity parseSql(String sql);
}
