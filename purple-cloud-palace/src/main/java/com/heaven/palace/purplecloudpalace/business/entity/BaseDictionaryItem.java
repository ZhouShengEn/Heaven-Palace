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
 * @Date 2022-09-02 16:29
 */
@TableName("base_dictionary_item")
public class BaseDictionaryItem extends Model<BaseDictionaryItem> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 序号
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 类型id
     */
    @TableField(value = "type_id")
    private Integer typeId;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 展示名
     */
    @TableField(value = "text")
    private String text;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @DiffIgnore
    private String createBy;

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
     * 创建时间
     */
    @TableField(value = "create_time")
    @DiffIgnore
    private Date createTime;

    /**
     * 父节点id
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 层级关系
     */
    @TableField(value = "level")
    private String level;


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
     * 获取: 序号
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置: 序号
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取: 类型id
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置: 类型id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取: 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置: 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取: 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置: 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取: 展示名
     */
    public String getText() {
        return text;
    }

    /**
     * 设置: 展示名
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取: 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置: 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    /**
     * 获取: 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置: 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取: 父节点id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置: 父节点id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取: 层级关系
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置: 层级关系
     */
    public void setLevel(String level) {
        this.level = level;
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
