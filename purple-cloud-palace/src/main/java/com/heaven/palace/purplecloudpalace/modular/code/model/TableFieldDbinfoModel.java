package com.heaven.palace.purplecloudpalace.modular.code.model;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_table_field_dbinfo")
public class TableFieldDbinfoModel extends Model<TableFieldDbinfoModel> {

    private static final long serialVersionUID = 1L;

    public TableFieldDbinfoModel() {
    }

    public TableFieldDbinfoModel(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字段ID
     */
    @TableField(value = "field_id")
    private Integer fieldId;

    /**
     * 列名
     */
    @TableField(value = "field_name")
    private String fieldName;

    /**
     * 字段默认值
     */
    @TableField(value = "field_default")
    private String fieldDefault;

    /**
     * 字段注释
     */
    @TableField(value = "field_content")
    private String fieldContent;

    /**
     * 字段长度
     */
    @TableField(value = "field_length")
    private BigDecimal fieldLength;

    /**
     * 字段类型
     */
    @TableField(value = "field_type")
    private String fieldType;

    /**
     * 小数点位数
     */
    @TableField(value = "field_point_length")
    private Integer fieldPointLength;

    /**
     * 获取: Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置: Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取: 字段ID
     */
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     * 设置: 字段ID
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取: 列名
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 设置: 列名
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 获取: 字段默认值
     */
    public String getFieldDefault() {
        return fieldDefault;
    }

    /**
     * 设置: 字段默认值
     */
    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    /**
     * 获取:  字段注释
     */
    public String getFieldContent() {
        return fieldContent;
    }

    /**
     * 设置:  字段注释
     */
    public void setFieldContent(String fieldContent) {
        this.fieldContent = fieldContent;
    }

    /**
     * 获取: 字段长度
     */
    public BigDecimal getFieldLength() {
        return fieldLength;
    }

    /**
     * 设置: 字段长度
     */
    public void setFieldLength(BigDecimal fieldLength) {
        this.fieldLength = fieldLength;
    }

    /**
     * 获取: 字段类型
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * 设置: 字段类型
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * 获取: 小数点位数
     */
    public Integer getFieldPointLength() {
        return fieldPointLength;
    }

    /**
     * 设置: 小数点位数
     */
    public void setFieldPointLength(Integer fieldPointLength) {
        this.fieldPointLength = fieldPointLength;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
