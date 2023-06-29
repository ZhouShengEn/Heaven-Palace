package com.heaven.palace.moonpalace.modular.custom.ugroup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * BaseGroup
 *
 * @author ZhouShengEn
 * @date 2023-02-22 17:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("custom_user_group")
@ApiModel(description = "菜单基础实例")
public class CustomUserGroup extends Model<CustomUserGroup> {

    private static final long serialVersionUID = 7706768737085651564L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 角色编码
     * 
     */
    @TableField(value = "code")
    @ApiModelProperty(notes = "角色编码")
    private String code;
    /**
     * 角色名称
     * 
     */
    @TableField(value = "name")
    @ApiModelProperty(notes = "角色名称")
    private String name;
    /**
     * 上级节点
     * 
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(notes = "上级节点")
    private Integer parentId;
    /**
     * 树状关系
     * 
     */
    @TableField(value = "path")
    @ApiModelProperty(notes = "树状关系")
    private String path;
    /**
     * 类型
     * 
     */
    @TableField(value = "type")
    @ApiModelProperty(notes = "类型")
    private String type;
    /**
     * 角色组类型
     * 
     */
    @TableField(value = "group_type")
    @ApiModelProperty(notes = "角色组类型")
    private Integer groupType;
    /**
     * Description
     * 
     */
    @TableField(value = "description")
    @ApiModelProperty(notes = "Description")
    private String description;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY")
    @ApiModelProperty(notes = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(value = "UPDATE_BY")
    @ApiModelProperty(notes = "修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME")
    @ApiModelProperty(notes = "修改时间")
    private Date updateTime;

}
