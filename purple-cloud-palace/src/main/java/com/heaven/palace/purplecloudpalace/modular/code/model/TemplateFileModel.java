package com.heaven.palace.purplecloudpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板内容
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_template_file")
public class TemplateFileModel extends Model<TemplateFileModel> {

    private static final long serialVersionUID = 1L;

    public TemplateFileModel() {

    }

    public TemplateFileModel(Integer templateId) {
        this.templateId = templateId;
    }


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * TemplateId
     */
    @TableField(value = "template_id")
    private Integer templateId;

    /**
     * 文件内容
     */
    @TableField(value = "file")
    private String file;

    /**
     * 文件类型
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 创建时间
     */
    @TableField(value = "CRT_TIME")
    private Date crtTime;

    /**
     * 修改时间
     */
    @TableField(value = "MDF_TIME")
    private Date mdfTime;


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
     * 获取: TemplateId
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 设置: TemplateId
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取: 文件内容
     */
    public String getFile() {
        return file;
    }

    /**
     * 设置: 文件内容
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * 获取: 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置: 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取: 创建时间
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * 设置: 创建时间
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    /**
     * 获取: 修改时间
     */
    public Date getMdfTime() {
        return mdfTime;
    }

    /**
     * 设置: 修改时间
     */
    public void setMdfTime(Date mdfTime) {
        this.mdfTime = mdfTime;
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
