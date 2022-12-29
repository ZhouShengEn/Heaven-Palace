package com.heaven.palace.purplecloudpalace.db.read.impl;

import com.heaven.palace.purplecloudpalace.db.exception.GenerationRunTimeException;
import com.heaven.palace.purplecloudpalace.db.read.BaseReadTable;
import com.heaven.palace.purplecloudpalace.db.read.IReadTable;
import com.heaven.palace.purplecloudpalace.model.GenBeanEntity;
import com.heaven.palace.purplecloudpalace.util.NameUtil;
import com.heaven.palace.purplecloudpalace.util.TableHanlderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * MySql数据库的实现类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class ReadTableForMysqlImpl extends BaseReadTable implements IReadTable {

    private static String TABLE_SQL = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_NAME = '%s' AND TABLE_SCHEMA = '%s'";

    private static String ALL_TABLE_SQL = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = '%s'";

    private static String FIELDS_SQL = "SELECT column_name as fieldName, data_type as fieldType, column_comment, numeric_precision, numeric_scale as scale, character_maximum_length as charmaxLength,is_nullable as nullable from information_schema.columns where table_name = '%s' and table_schema = '%s'";

    private static String SCHEMA_SQL = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME != 'information_schema' AND SCHEMA_NAME !=  'mysql'";

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadTableForMysqlImpl.class);

    @Override
    public GenBeanEntity read(String dbName, String tableName) {
        try {
            GenBeanEntity entity = getTableEntiy(dbName, tableName, TABLE_SQL);
            entity.setName(NameUtil.getEntityHumpName(entity.getTableName()));
            entity.setFields(getTableFields(dbName, tableName, FIELDS_SQL));
            TableHanlderUtil.hanlderFields(entity.getFields());
            return entity;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerationRunTimeException("获取表格数据发生异常");
        }
    }

    @Override
    public List<String> getAllDB() {
        try {
            return getAllDB(SCHEMA_SQL);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerationRunTimeException("获取表格数据发生异常");
        }
    }

    @Override
    public List<GenBeanEntity> getAllTable(String dbName) {
        try {
            return getAllTableEntiy(dbName, ALL_TABLE_SQL);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerationRunTimeException("获取表格数据发生异常");
        }
    }

    @Override
    protected String handlerTableComment(String comment) {
        if (comment.contains(";")) {
            return comment.split(";")[0];
        }
        if (comment.startsWith("InnoDB free")) {
            return null;
        }
        return comment;
    }

}
