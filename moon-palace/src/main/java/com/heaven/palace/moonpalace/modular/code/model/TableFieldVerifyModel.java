package com.heaven.palace.moonpalace.modular.code.model;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 字段的校验信息
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_table_field_verification")
public class TableFieldVerifyModel extends Model<TableFieldVerifyModel> {

    private static final long serialVersionUID = 1L;

    public TableFieldVerifyModel() {

    }

    public TableFieldVerifyModel(Integer fieldId) {
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
     * 前端校验
     */
    @TableField(value = "view_verification")
    private Integer viewVerification;

    /**
     * 后台校验
     */
    @TableField(value = "server_verification")
    private Integer serverVerification;

    /**
     * 允许空
     */
    @TableField(value = "not_null")
    private Integer notNull;

    /**
     * 最小
     */
    @TableField(value = "min_num")
    private String minNum;

    /**
     * 最大
     */
    @TableField(value = "max_num")
    private String maxNum;

    /**
     * 正则
     */
    @TableField(value = "regex")
    private String regex;

    /**
     * 0 自定义 1 -邮箱 2 手机 3- 电话 4-身份证
     */
    @TableField(value = "regex_type")
    private Integer regexType;

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
     * 获取: 前端校验
     */
    public Integer getViewVerification() {
        return viewVerification;
    }

    /**
     * 设置: 前端校验
     */
    public void setViewVerification(Integer viewVerification) {
        this.viewVerification = viewVerification;
    }

    /**
     * 获取: 后台校验
     */
    public Integer getServerVerification() {
        return serverVerification;
    }

    /**
     * 设置: 后台校验
     */
    public void setServerVerification(Integer serverVerification) {
        this.serverVerification = serverVerification;
    }

    /**
     * 获取: 允许空
     */
    public Integer getNotNull() {
        return notNull;
    }

    /**
     * 设置: 允许空
     */
    public void setNotNull(Integer notNull) {
        this.notNull = notNull;
    }

    /**
     * 获取: 最小
     */
    public String getMinNum() {
        return minNum;
    }

    /**
     * 设置: 最小
     */
    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    /**
     * 获取: 最大
     */
    public String getMaxNum() {
        return maxNum;
    }

    /**
     * 设置: 最大
     */
    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * 获取: 正则
     */
    public String getRegex() {
        return regex;
    }

    /**
     * 设置: 正则
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Integer getRegexType() {
        return regexType;
    }

    public void setRegexType(Integer regexType) {
        this.regexType = regexType;
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
