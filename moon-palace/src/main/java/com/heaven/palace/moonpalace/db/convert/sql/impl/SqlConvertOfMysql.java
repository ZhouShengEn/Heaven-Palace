package com.heaven.palace.moonpalace.db.convert.sql.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import com.heaven.palace.moonpalace.db.convert.sql.ISqlConvert;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.model.GenFieldEntity;
import com.heaven.palace.moonpalace.util.NameUtil;
import com.heaven.palace.moonpalace.util.TableHanlderUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Mysql 语句解析
 *
 * @author ZhouShengEn on 2017/10/25.
 */
public class SqlConvertOfMysql implements ISqlConvert {

    @Override
    public GenBeanEntity parseSql(String sql) {
        sql = sql.replace("`", "");
        sql = sql.replace("'", "");
        sql = SQLUtils.format(sql, JdbcConstants.MYSQL);
        GenBeanEntity bean = new GenBeanEntity();
        String tableName = sql.substring(sql.indexOf("TABLE") + 5, sql.indexOf("(")).trim();
        bean.setTableName(tableName);
        bean.setName(NameUtil.getEntityHumpName(bean.getTableName()));
        String[] columns = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf(")")).split("\n");
        List<GenFieldEntity> fields = new ArrayList<GenFieldEntity>(columns.length);
        GenFieldEntity field;
        for (int i = 0; i < columns.length; i++) {
            if (StringUtils.isBlank(columns[i])) {
                continue;
            }
            if (columns[i].contains("PRIMARY")) {
                //设置主键
                String[] keys = columns[i].substring(columns[i].indexOf("(") + 1, columns[i].indexOf(")")).split(",");
                for (int j = 0; j < keys.length; j++) {
                    for (int k = 0; k < fields.size(); k++) {
                        if (fields.get(k).getFieldName().equalsIgnoreCase(keys[j])) {
                            fields.get(k).setKey(2);
                            break;
                        }
                    }
                }
            } else {
                field = new GenFieldEntity();
                String[] keys = columns[i].trim().split(" ");
                field.setFieldName(keys[0]);
                if (keys[1].indexOf("(") > 0) {
                    String type = keys[1];
                    field.setFieldType(type.substring(0, type.indexOf("(")));
                    if (type.indexOf(")") < 0) {
                        for (int j = 2; j < keys.length; j++) {
                            if (keys[j].indexOf(")") > -1) {
                                type += keys[j];
                                break;
                            }
                            type += keys[j];
                        }
                    }
                    String[] points = type.substring(type.indexOf("(") + 1, type.indexOf(")")).split(",");
                    field.setFieldLength(new BigDecimal(points[0]));
                    if (points.length == 2) {
                        field.setFieldPointLength(Integer.parseInt(points[1]));
                    }
                } else {
                    field.setFieldType(keys[1]);
                }
                field.setNotNull(columns[i].contains("NOT NULL") ? 2 : 1);
                for (int j = 2; j < keys.length; j++) {
                    if ("COMMENT".equalsIgnoreCase(keys[j]) && keys.length > j + 1) {
                        field.setComment(keys[j + 1]);
                    } else if ("NOT".equalsIgnoreCase(keys[j])) {

                    }
                }
                fields.add(field);
            }
        }
        TableHanlderUtil.hanlderFields(fields);
        bean.setFields(fields);
        String tableInfo = sql.substring(sql.lastIndexOf(")") + 1);
        if (tableInfo.contains("COMMENT")) {
            tableInfo = tableInfo.substring(tableInfo.lastIndexOf("COMMENT") + 7).trim();
            tableInfo = tableInfo.substring(tableInfo.lastIndexOf("=") + 1).trim();
            tableInfo = tableInfo.substring(0, tableInfo.lastIndexOf(" ") == -1 ? tableInfo.length() : tableInfo.lastIndexOf(" ")).trim();
            bean.setComment(tableInfo);
            if (bean.getComment().endsWith(";")) {
                bean.setComment(bean.getComment().substring(0, bean.getComment().length() - 1));
            }
            bean.setChinaName(bean.getComment());
        }
        return bean;
    }

}
