package com.heaven.palace.purplecloudpalace.db.convert.sql;

import com.heaven.palace.purplecloudpalace.model.GenBeanEntity;

/**
 * @author ZhouShengEn on 2017/10/25.
 */
public interface ISqlConvert {

    /**
     * 解析SQL
     */
    public GenBeanEntity parseSql(String sql);
}
