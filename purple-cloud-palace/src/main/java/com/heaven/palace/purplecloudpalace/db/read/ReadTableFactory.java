package com.heaven.palace.purplecloudpalace.db.read;

import com.heaven.palace.purplecloudpalace.db.exception.GenerationRunTimeException;
import com.heaven.palace.purplecloudpalace.db.read.impl.ReadTableForMysqlImpl;
import com.heaven.palace.purplecloudpalace.common.constant.GenCoreConstant;


/**
 * 读取库的工厂
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class ReadTableFactory {

    public static IReadTable getReadTable(String dbType) {
        if (GenCoreConstant.MYSQL.equalsIgnoreCase(dbType)) {
            return new ReadTableForMysqlImpl();
        }
        throw new GenerationRunTimeException("数据库不支持");
    }

    public static String getDeiver(String dbType) {
        if (GenCoreConstant.MYSQL.equalsIgnoreCase(dbType)) {
            return "com.mysql.jdbc.Driver";
        }
        throw new GenerationRunTimeException("数据库不支持");
    }
}
