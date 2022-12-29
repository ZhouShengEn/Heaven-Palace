package com.heaven.palace.purplecloudpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_template_group")
public class TemplateGroupModel extends CodeBaseModel<TemplateGroupModel> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 组名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

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
     * 获取: 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置: 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取: 组名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置: 组名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取: 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置: 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
