package com.heaven.palace.purplecloudpalace.db.convert.sql;

import com.heaven.palace.purplecloudpalace.db.convert.sql.impl.SqlConvertOfMysql;
import com.heaven.palace.purplecloudpalace.db.exception.GenerationRunTimeException;
import com.heaven.palace.purplecloudpalace.common.constant.GenCoreConstant;


/**
 * @author ZhouShengEn on 2017/10/25.
 */
public class SqlConvertFactory {

    public static ISqlConvert getReadTable(String dbType) {
        if (GenCoreConstant.MYSQL.equalsIgnoreCase(dbType)) {
            return new SqlConvertOfMysql();
        }
        throw new GenerationRunTimeException("数据库不支持");
    }
}
