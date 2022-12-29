package com.heaven.palace.purplecloudpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 数据库管理
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_dbinfo")
public class DbInfoModel extends CodeBaseModel<DbInfoModel> {

    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 别名
     */
    @TableField(value = "ALIAS")
    private String alias;

    /**
     * 数据库驱动
     */
    @TableField(value = "DB_DRIVER")
    private String dbDriver;

    /**
     * 数据库地址
     */
    @TableField(value = "DB_URL")
    private String dbUrl;

    /**
     * 数据库账户
     */
    @TableField(value = "DB_USER_NAME")
    private String dbUserName;

    /**
     * 连接密码
     */
    @TableField(value = "DB_PASSWORD")
    private String dbPassword;

    /**
     * UserId
     */
    @TableField(value = "USER_ID")
    private Integer userId;

    /**
     * 数据库类型
     */
    @TableField(value = "DB_TYPE")
    private String dbType;

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
     * 获取: 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置: 别名
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 获取: 数据库驱动
     */
    public String getDbDriver() {
        return dbDriver;
    }

    /**
     * 设置: 数据库驱动
     */
    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    /**
     * 获取: 数据库地址
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * 设置: 数据库地址
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * 获取: 数据库账户
     */
    public String getDbUserName() {
        return dbUserName;
    }

    /**
     * 设置: 数据库账户
     */
    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    /**
     * 获取: 连接密码
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * 设置: 连接密码
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * 获取: UserId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置: UserId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取: 数据库类型
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * 设置: 数据库类型
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
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
