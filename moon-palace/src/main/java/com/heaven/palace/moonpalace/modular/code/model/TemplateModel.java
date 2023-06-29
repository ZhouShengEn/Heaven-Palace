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
 * 模板管理
 *
 * @author ZhouShengEn
 * @Date 2022-8-25
 */
@EqualsAndHashCode(callSuper = true)
@TableName("code_template")
@ApiModel(description = "模板基础类")
@Data
public class TemplateModel extends CodeBaseModel<TemplateModel> {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    @TableField(value = "TEMPLATE_NAME")
    @ApiModelProperty(notes = "模板名称")
    private String templateName;

    /**
     * 模板地址
     */
    @TableField(value = "TEMPLATE_path")
    @ApiModelProperty(notes = "模板地址")
    private String templatePath;

    /**
     * UserId
     */
    @TableField(value = "USER_ID")
    @ApiModelProperty(notes = "UserId")
    private Integer userId;

    /**
     * TemplateDesc
     */
    @TableField(value = "TEMPLATE_DESC")
    @ApiModelProperty(notes = "TemplateDesc")
    private String templateDesc;

    /**
     * 文件名称
     */
    @TableField(value = "FILE_NAME")
    @ApiModelProperty(notes = "文件名称")
    private String fileName;
    /**
     * 本地路径
     */
    @TableField(value = "local_path")
    @ApiModelProperty(notes = "本地路径")
    private String localPath;
    /**
     *  组ID
     */
    @TableField(value = "GROUP_ID")
    @ApiModelProperty(notes = "组ID")
    private String groupId;
    /**
     * 模板类型
     */
    @TableField(value = "TEMPLATE_TYPE")
    @ApiModelProperty(notes = "模板类型")
    private String templateType;

    @TableField(exist = false)
    private TemplateFileModel fileModel;

}
