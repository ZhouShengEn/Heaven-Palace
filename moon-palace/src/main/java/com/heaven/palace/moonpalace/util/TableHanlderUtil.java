package com.heaven.palace.moonpalace.util;

import com.heaven.palace.moonpalace.model.GenFieldEntity;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;

/**
 * 表处理通用类
 *
 * @author ZhouShengEn on 2017/10/25.
 */
public class TableHanlderUtil {

    private static final String GEN_TYPE_CONVERT_FILE_NAME = "generator.yml";

    private TableHanlderUtil() {

    }

    /**
     * 处理一遍字段,根据字段类型做合适的处理
     */
    public static void hanlderFields(List<GenFieldEntity> fields) {
        GenFieldEntity entity;
        for (int i = 0, le = fields.size(); i < le; i++) {
            entity = fields.get(i);
            entity.setChinaName(getFieldName(entity.getFieldName(), entity.getComment()));
            if (entity.getChinaName().equals(entity.getComment())) {
                entity.setComment(null);
            }
            entity.setName(NameUtil.getFieldHumpName(entity.getFieldName()));
            entity.setType(convertType(entity.getFieldType(), entity.getFieldPointLength()));
        }
    }

    /**
     * 处理字段名称
     */
    public static String getFieldName(String fieldName, String comment) {
        if (StringUtils.isNotEmpty(comment)) {
            String[] nameAndComment = comment.split(",");
            return nameAndComment[0];
        }
        return NameUtil.getEntityHumpName(fieldName);
    }
    
    public static Configuration getConfig(){
        try {
            return new Configurations().properties(new File(GEN_TYPE_CONVERT_FILE_NAME));
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 转换类型
     */
    public static String convertType(String dataType, Integer pointLength) {
        Configuration config = getConfig();
        return config.getString(dataType.toLowerCase(), "Object");
//        dataType = dataType.toLowerCase();
//        if (dataType.contains("char") || dataType.contains("text")) {
//            dataType = "String";
//        } else if (dataType.contains("int")) {
//            dataType = "Integer";
//        } else if (dataType.contains("float")) {
//            dataType = "Float";
//        } else if (dataType.contains("double")) {
//            dataType = "Double";
//        } else if (dataType.contains("number")) {
//            if (pointLength != null && pointLength > 0) {
//                dataType = "Double";
//            }/* else if (StringUtils.isNotEmpty(precision) && Integer.parseInt(precision) > 10) {
//                dataType = "Long";
//            } else {
//                dataType = "Integer";
//            }*/
//        } else if (dataType.contains("decimal")) {
//            dataType = "BigDecimal";
//        } else if (dataType.contains("date")) {
//            dataType = "Date";
//        } else if (dataType.contains("time")) {
//            dataType = "Date";
//        } else if (dataType.contains("blob")) {
//            dataType = "byte[]";
//        } else if (dataType.contains("clob")) {
//            dataType = "java.sql.Clob";
//        } else if (dataType.contains("numeric")) {
//            dataType = "BigDecimal";
//        } else {
//            dataType = "Object";
//        }
//        return dataType;
    }
}
