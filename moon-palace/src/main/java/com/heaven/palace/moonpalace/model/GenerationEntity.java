package com.heaven.palace.moonpalace.model;

import com.heaven.palace.moonpalace.model.enmus.GenerationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 主键的参数
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Data
@ApiModel(description = "生成代码基础信息")
public class GenerationEntity implements Serializable {


    private static final long serialVersionUID = -5758707294938160661L;
    /**
     * Java包名
     */
    @ApiModelProperty(notes = "Java包名")
    private String codePackage ;
    /**
     * html包名,如果为空,使用codePackage
     */
    @ApiModelProperty(notes = "html包名,如果为空,使用codePackage")
    private String htmlPackage;
    /**
     * js包名,如果为空,使用codePackage
     */
    @ApiModelProperty(notes = "js包名,如果为空,使用codePackage")
    private String jsPackage;
    /**
     * xml包名,如果为空,使用codePackage
     */
    @ApiModelProperty(notes = "xml包名,如果为空,使用codePackage")
    private String xmlPackage;
    /**
     * 功能名称
     */
    @ApiModelProperty(notes = "功能名称")
    private String name;

    /**
     * 主键名称
     */
    @ApiModelProperty(notes = "主键名称")
    private String idName = "id";
    /**
     * 主键类型
     */
    @ApiModelProperty(notes = "主键类型")
    private GenerationType idType = GenerationType.IDENTITY;
    /**
     * 类名,不填使用表名
     */
    @ApiModelProperty(notes = "类名,不填使用表名")
    private String entityName;
    /**
     * 生成时间
     **/
    @ApiModelProperty(notes = "生成时间")
    private String date;
    /**
     * 作者
     **/
    @ApiModelProperty(notes = "作者")
    private String author;

    /**
     * IP地址
     */
    @ApiModelProperty(notes = "IP地址")
    private String ipAddress;
    /**
     * sftp账户
     */
    @ApiModelProperty(notes = "sftp账户")
    private String sftpAccount;
    /**
     * sftp密码
     */
    @ApiModelProperty(notes = "sftp密码")
    private String sftpPassword;

    /**
     * 本地路径
     */
    @ApiModelProperty(notes = "本地路径")
    private String localPath;
    /**
     * 编码类型
     */
    @ApiModelProperty(notes = "编码类型")
    private String encoded;

}
