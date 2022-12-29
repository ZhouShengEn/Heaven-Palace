package com.heaven.palace.purplecloudpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 模板管理
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_template")
public class TemplateModel extends CodeBaseModel<TemplateModel> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    @TableField(value = "TEMPLATE_NAME")
    private String templateName;

    /**
     * 模板地址
     */
    @TableField(value = "TEMPLATE_path")
    private String templatePath;

    /**
     * UserId
     */
    @TableField(value = "USER_ID")
    private Integer userId;

    /**
     * TemplateDesc
     */
    @TableField(value = "TEMPLATE_DESC")
    private String templateDesc;

    /**
     * 文件名称
     */
    @TableField(value = "FILE_NAME")
    private String fileName;
    /**
     * 本地路径
     */
    @TableField(value = "local_path")
    private String localPath;
    /**
     *  组ID
     */
    @TableField(value = "GROUP_ID")
    private String groupId;
    /**
     * 模板类型
     */
    @TableField(value = "TEMPLATE_TYPE")
    private String templateType;

    @TableField(exist = false)
    private TemplateFileModel fileModel;

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
     * 获取: 模板名称
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * 设置: 模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 获取: 模板地址
     */
    public String getTemplatePath() {
        return templatePath;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 设置: 模板地址
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 获取: UserId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置: UserId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取: TemplateDesc
     */
    public String getTemplateDesc() {
        return templateDesc;
    }

    /**
     * 设置: TemplateDesc
     */
    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    /**
     * 获取: 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置: 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取: 模板类型
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * 设置: 模板类型
     */
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public TemplateFileModel getFileModel() {
        return fileModel;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public void setFileModel(TemplateFileModel fileModel) {
        this.fileModel = fileModel;
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
