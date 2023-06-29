package com.heaven.palace.moonpalace.modular.code.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 生成参数
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@TableName("code_gen_params")
@Data
public class GenParamModel extends CodeBaseModel<GenParamModel> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 别名
     */
    @TableField(value = "alias")
    private String alias;
    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;
    /**
     * 拥有人
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * code 包
     */
    @TableField(value = "code_package")
    private String codePackage;

    /**
     * xml 路径
     */
    @TableField(value = "xml_package")
    private String xmlPackage;

    /**
     * js 目录
     */
    @TableField(value = "js_package")
    private String jsPackage;

    /**
     * html 目录
     */
    @TableField(value = "html_package")
    private String htmlPackage;

    /**
     * 本地路径
     */
    @TableField(value = "local_path")
    private String localPath;
    /**
     * 编码
     */
    @TableField(value = "encoded")
    private String encoded;
    /**
     * IP地址
     */
    @TableField(value = "ip_address")
    private String ipAddress;
    /**
     * sftp账户
     */
    @TableField(value = "sftp_account")
    private String sftpAccount;
    /**
     * sftp密码
     */
    @TableField(value = "sftp_password")
    private String sftpPassword;

}
