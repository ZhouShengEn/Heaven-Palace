package com.heaven.palace.purplecloudpalace.business.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.javers.core.metamodel.annotation.DiffIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhouShengEn
 * @Date 2022-09-02 17:02
 */
@TableName("base_dictionary_type")
public class BaseDictionaryType extends Model<BaseDictionaryType> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 字典类型名称
     */
    @TableField(value = "text")
    private String text;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    @DiffIgnore
    private String updateBy;

    /**
     * 修改日期
     */
    @TableField(value = "update_date")
    @DiffIgnore
    private Date updateDate;


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
     * 获取: 类型编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置: 类型编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取: 字典类型名称
     */
    public String getText() {
        return text;
    }

    /**
     * 设置: 字典类型名称
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取: 修改人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置: 修改人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取: 修改日期
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置: 修改日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
