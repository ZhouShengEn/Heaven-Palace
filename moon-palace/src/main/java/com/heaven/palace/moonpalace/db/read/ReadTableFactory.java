package com.heaven.palace.moonpalace.db.read;

import com.heaven.palace.moonpalace.common.constant.GenCoreConstant;
import com.heaven.palace.moonpalace.db.exception.GenerationRunTimeException;
import com.heaven.palace.moonpalace.db.read.impl.ReadTableForMysqlImpl;


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
