package com.heaven.palace.moonpalace.modular.custom.element.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.heaven.palace.moonpalace.base.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;



/**
 * CustomElement
 *
 * @author ZhouShengEn
 * @date 2023-02-27 10:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("custom_element")
@ApiModel(description = "基础资源实体")
public class CustomElement extends BaseModel<CustomElement> {

    private static final long serialVersionUID = -4582707912100470910L;
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(notes = "Id")
    private Integer id;
    /**
     * 资源编码
     * 
     */
    @TableField(value = "code")
    @ApiModelProperty(notes = "资源编码")
    private String code;
    /**
     * 资源类型
     * 
     */
    @TableField(value = "type")
    @ApiModelProperty(notes = "资源类型")
    private String type;
    /**
     * 资源名称
     * 
     */
    @TableField(value = "name")
    @ApiModelProperty(notes = "资源名称")
    private String name;
    /**
     * 资源路径
     * 
     */
    @TableField(value = "uri")
    @ApiModelProperty(notes = "资源路径")
    private String uri;
    /**
     * 资源关联菜单
     * 
     */
    @TableField(value = "menu_id")
    @ApiModelProperty(notes = "资源关联菜单")
    private String menuId;
    /**
     * ParentId
     * 
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(notes = "ParentId")
    private String parentId;
    /**
     * 资源树状检索路径
     * 
     */
    @TableField(value = "path")
    @ApiModelProperty(notes = "资源树状检索路径")
    private String path;
    /**
     * 资源请求类型
     * 
     */
    @TableField(value = "method")
    @ApiModelProperty(notes = "资源请求类型")
    private String method;
    /**
     * 描述
     * 
     */
    @TableField(value = "description")
    @ApiModelProperty(notes = "描述")
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
