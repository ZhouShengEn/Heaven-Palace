package com.heaven.palace.moonpalace.common.persistence.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhouShengEn
 * @Date 2022-09-06 15:42
 */
@TableName("business_operation_log")
@Data
@ApiModel(value = "业务日志数据结构")
public class BusinessOperationLog implements Serializable {

    private static final long serialVersionUID = -4433287197320026054L;
    /**
     * Id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    @ApiModelProperty(notes = "id")
    private Integer id;

    /**
     * 操作标题（新增、修改、删除）
     */
    @TableField(value = "TITLE")
    @ApiModelProperty(notes = "操作标题（新增、修改、删除）")
    private String title;

    /**
     * 操作内容
     */
    @TableField(value = "CONTENT")
    @ApiModelProperty(notes = "操作内容")
    private String content;
    
    /**
     * 业务类型（字典列表：customDictItem；字典类型：customDictType；菜单：customBaseMenu；权限：customCustomResourceAuthority）
     */
    @TableField(value = "TYPE")
    @ApiModelProperty(notes = "业务类型（字典列表：customDictItem；字典类型：customDictType；菜单：customBaseMenu；权限：customCustomResourceAuthority）")
    private String type;

    /**
     * 环境数据源id
     */
    @TableField(value = "DB_ID")
    @ApiModelProperty(notes = "环境数据源id")
    private Integer dbId;

    /**
     * 系统用户id
     */
    @TableField(value = "USER_ID")
    @ApiModelProperty(notes = "系统用户id")
    private Integer userId;

    /**
     * sql数据
     */
    @TableField(value = "SQL_TEXT")
    @ApiModelProperty(notes = "sql数据")
    private String sqlText;

    @TableField(value = "SYNC_STATUS")
    @ApiModelProperty(notes = "同步状态：0-不需要同步；1-需要同步；2-已同步")
    private Integer syncStatus;

    @TableField(value = "PARENT_ID")
    @ApiModelProperty(notes = "父级id")
    private Integer parentId;
    
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
