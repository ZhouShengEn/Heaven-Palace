package com.heaven.palace.moonpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 模板分享管理
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_template_share")
public class TemplateShareModel extends Model<TemplateShareModel> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 模板名称
     */
    @TableField(value = "Template_NAME")
    private String templateName;

    /**
     * 模板地址
     */
    @TableField(value = "Template_path")
    private String templatePath;

    /**
     * 模板效果
     */
    @TableField(value = "Template_effect")
    private String templateEffect;

    /**
     * TemplateDesc
     */
    @TableField(value = "Template_DESC")
    private String templateDesc;


    /**
     * 获取: Id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置: Id
     */
    public void setId(String id) {
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

    /**
     * 设置: 模板地址
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 获取: 模板效果
     */
    public String getTemplateEffect() {
        return templateEffect;
    }

    /**
     * 设置: 模板效果
     */
    public void setTemplateEffect(String templateEffect) {
        this.templateEffect = templateEffect;
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
