package com.heaven.palace.purplecloudpalace.common.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p> 角色和菜单关联表 </p>
 *
 * @author ZhouShengEn
 * @since 2022-8-25
 */
@TableName("system_relation")
public class Relation extends Model<Relation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单id
     */
    private Integer menuid;
    /**
     * 角色id
     */
    private Integer roleid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "id=" + id +
                ", menuid=" + menuid +
                ", roleid=" + roleid +
                "}";
    }
}
