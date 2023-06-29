package com.heaven.palace.moonpalace.modular.custom.authority.entity;

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
 * CustomResourceAuthority
 *
 * @author ZhouShengEn
 * @date 2023-02-03 17:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("custom_resource_authority")
@ApiModel(description = "基础资源权限实例")
public class CustomResourceAuthority extends Model<CustomResourceAuthority> {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(notes = "id")
    private Integer id;

    /**
     * 角色ID
     * 
     */
    @TableField(value = "authority_id")
    @ApiModelProperty(notes = "角色ID")
    private String authorityId;
    /**
     * 角色类型
     * 
     */
    @TableField(value = "authority_type")
    @ApiModelProperty(notes = "角色类型")
    private String authorityType;
    /**
     * 资源ID
     * 
     */
    @TableField(value = "resource_id")
    @ApiModelProperty(notes = "资源ID")
    private String resourceId;
    /**
     * 资源类型
     * 
     */
    @TableField(value = "resource_type")
    @ApiModelProperty(notes = "资源类型")
    private String resourceType;
    /**
     * ParentId
     * 
     */
    @TableField(value = "parent_id")
    private String parentId;
    /**
     * Path
     * 
     */
    @TableField(value = "path")
    private String path;
    /**
     * Description
     * 
     */
    @TableField(value = "description")
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
