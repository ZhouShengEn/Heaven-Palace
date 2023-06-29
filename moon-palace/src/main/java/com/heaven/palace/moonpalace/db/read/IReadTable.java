package com.heaven.palace.moonpalace.db.read;

import com.heaven.palace.moonpalace.model.GenBeanEntity;

import java.util.List;

/**
 * 读取数据库表,获取数据库表的属性
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public interface IReadTable {
    /**
     * 读取数据库表格属性
     */
    public GenBeanEntity read(String dbName, String tableName);

    /**
     * 查询所有的库
     */
    public List<String> getAllDB();

    /**
     * 查询所有的表
     */
    public List<GenBeanEntity> getAllTable(String dbName);

}
