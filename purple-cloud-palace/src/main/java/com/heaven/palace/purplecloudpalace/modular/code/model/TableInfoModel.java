package com.heaven.palace.purplecloudpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_table_head")
public class TableInfoModel extends CodeBaseModel<TableInfoModel> {

    private static final long serialVersionUID = 1L;

    public TableInfoModel() {

    }

    public TableInfoModel(Integer id) {
        this.id = id;
    }

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 拥有人
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 表名
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * ClassName
     */
    @TableField(value = "class_name")
    private String className;

    /**
     * 表名称
     */
    @TableField(value = "content")
    private String content;

    /**
     * 是否导入Excel
     */
    @TableField(value = "is_import")
    private Integer isImport;

    /**
     * 是否导出Excel
     */
    @TableField(value = "is_export")
    private Integer isExport;

    /**
     * 是否分页
     */
    @TableField(value = "is_pagination")
    private Integer isPagination;

    /**
     * 是否添加日志
     */
    @TableField(value = "is_log")
    private Integer isLog;

    /**
     * 是否添加协议
     */
    @TableField(value = "is_protocol")
    private Integer isProtocol;

    @TableField(exist = false)
    private List<TableServiceConfigModel> serviceConfig;

    @TableField(exist = false)
    private List<TableFieldModel> tableFields;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取: 表名
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置: 表名
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取: ClassName
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置: ClassName
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取: 表名称
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置: 表名称
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取: 是否导入Excel
     */
    public Integer getIsImport() {
        return isImport;
    }

    /**
     * 设置: 是否导入Excel
     */
    public void setIsImport(Integer isImport) {
        this.isImport = isImport;
    }

    /**
     * 获取: 是否导出Excel
     */
    public Integer getIsExport() {
        return isExport;
    }

    /**
     * 设置: 是否导出Excel
     */
    public void setIsExport(Integer isExport) {
        this.isExport = isExport;
    }

    /**
     * 获取: 是否分页
     */
    public Integer getIsPagination() {
        return isPagination;
    }

    /**
     * 设置: 是否分页
     */
    public void setIsPagination(Integer isPagination) {
        this.isPagination = isPagination;
    }

    /**
     * 获取: 是否添加日志
     */
    public Integer getIsLog() {
        return isLog;
    }

    /**
     * 设置: 是否添加日志
     */
    public void setIsLog(Integer isLog) {
        this.isLog = isLog;
    }

    /**
     * 获取: 是否添加协议
     */
    public Integer getIsProtocol() {
        return isProtocol;
    }

    /**
     * 设置: 是否添加协议
     */
    public void setIsProtocol(Integer isProtocol) {
        this.isProtocol = isProtocol;
    }

    public List<TableServiceConfigModel> getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(List<TableServiceConfigModel> serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public List<TableFieldModel> getTableFields() {
        return tableFields;
    }

    public void setTableFields(List<TableFieldModel> tableFields) {
        this.tableFields = tableFields;
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
