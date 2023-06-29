package com.heaven.palace.moonpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据库管理
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@EqualsAndHashCode(callSuper = true)
@TableName("code_dbinfo")
@ApiModel(description = "数据库实例")
@Data
public class DbInfoModel extends CodeBaseModel<DbInfoModel> {

    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(notes = "主键id")
    private Integer id;

    /**
     * 别名
     */
    @TableField(value = "ALIAS")
    @ApiModelProperty(notes = "别名")
    private String alias;

    /**
     * 数据库驱动
     */
    @TableField(value = "DB_DRIVER")
    @ApiModelProperty(notes = "数据库驱动")
    private String dbDriver;

    /**
     * 数据库地址
     */
    @TableField(value = "DB_URL")
    @ApiModelProperty(notes = "数据库地址")
    private String dbUrl;

    /**
     * 数据库账户
     */
    @TableField(value = "DB_USER_NAME")
    @ApiModelProperty(notes = "数据库账户")
    private String dbUserName;

    /**
     * 连接密码
     */
    @TableField(value = "DB_PASSWORD")
    @ApiModelProperty(notes = "连接密码")
    private String dbPassword;

    /**
     * UserId
     */
    @TableField(value = "USER_ID")
    @ApiModelProperty(notes = "用户id")
    private Integer userId;

    /**
     * 数据库类型
     */
    @TableField(value = "DB_TYPE")
    @ApiModelProperty(notes = "数据库类型")
    private String dbType;

    @ApiModelProperty(notes = "标签")
    @TableField(value = "TAG")
    private String tag;

    @ApiModelProperty(notes = "数据源来源分类：0-业务数据源； 1-代码生成数据源")
    @TableField(value = "CATEGORY")
    private Integer category;


}
