package com.heaven.palace.moonpalace.db.read;

import com.heaven.palace.moonpalace.db.exception.GenerationRunTimeException;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.model.GenFieldEntity;
import com.heaven.palace.moonpalace.util.ConnectionUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 读表基础类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public abstract class BaseReadTable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseReadTable.class);

    protected List<GenFieldEntity> getTableFields(String dbName, String tableName, String sql) throws Exception {
        List<GenFieldEntity> list = new ArrayList<GenFieldEntity>();
        Statement statement = null;
        try {
            ResultSet rs = ConnectionUtil.createStatement()
                    .executeQuery(String.format(sql, tableName, dbName));
            GenFieldEntity field;
            while (rs.next()) {
                field = new GenFieldEntity();
                if (StringUtils.isNotEmpty(rs.getString("charmaxLength"))) {
                    field.setFieldLength(rs.getBigDecimal("charmaxLength"));
                } else {
                    field.setFieldLength(rs.getBigDecimal("numeric_precision"));
                }
                field.setComment(rs.getString("column_comment"));
                field.setFieldName(rs.getString("fieldName"));
                field.setNotNull("Y".equalsIgnoreCase(rs.getString("nullable")) ? 2 : 1);
                field.setFieldType(rs.getString("fieldType"));
                //field.setPrecision(rs.getString("numeric_precision"));
                field.setFieldPointLength(rs.getInt("scale"));
                list.add(field);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerationRunTimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
        return list;
    }

    protected GenBeanEntity getTableEntiy(String dbName, String tableName, String sql) throws Exception {
        Statement statement = null;
        try {
            ResultSet rs = ConnectionUtil.createStatement().executeQuery(String.format(sql, tableName, dbName));
            String dbTableName = null;
            String comment = null;
            while (rs.next()) {
                dbTableName = rs.getString(1);
                comment = rs.getString(2);
            }
            if (StringUtils.isEmpty(dbTableName)) {
                throw new GenerationRunTimeException("表不存在");
            } else {
                GenBeanEntity entity = new GenBeanEntity();
                if (StringUtils.isNotEmpty(comment)) {
                    entity.setChinaName(handlerTableComment(comment));
                }
                entity.setTableName(dbTableName);
                return entity;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerationRunTimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
    }

    public List<String> getAllDB(String sql) throws SQLException {
        Statement statement = null;
        List<String> list = new ArrayList<String>();
        try {
            ResultSet rs = ConnectionUtil.createStatement().executeQuery(String.format(sql));
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerationRunTimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
        return list;
    }

    protected abstract String handlerTableComment(String comment);

    protected List<GenBeanEntity> getAllTableEntiy(String dbName, String sql) throws Exception {
        Statement statement = null;
        List<GenBeanEntity> list = new ArrayList<GenBeanEntity>();
        try {
            ResultSet rs = ConnectionUtil.createStatement().executeQuery(String.format(sql, dbName));
            String dbTableName = null;
            String comment = null;
            while (rs.next()) {
                dbTableName = rs.getString(1);
                comment = rs.getString(2);
                if (StringUtils.isEmpty(dbTableName)) {
                    throw new GenerationRunTimeException("表不存在");
                } else {
                    GenBeanEntity entity = new GenBeanEntity();
                    if (StringUtils.isNotEmpty(comment)) {
                        entity.setChinaName(handlerTableComment(comment));
                    }
                    entity.setTableName(dbTableName);
                    list.add(entity);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new GenerationRunTimeException("查询表是否存在发生异常", e);
        } finally {
            if (statement != null) {
                statement.close();
                statement = null;
            }
        }
        return list;
    }

}
