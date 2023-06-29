package com.heaven.palace.moonpalace.modular.code.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@ApiModel(description = "模板分页查询-出参")
public class GenTemplatePageResVO implements Serializable {
    private static final long serialVersionUID = 3301790761604744150L;
    /**
     * Id
     */
    @ApiModelProperty(notes = "id")
    private Integer id;

    /**
     * 模板名称
     */
    @ApiModelProperty(notes = "模板名称")
    private String templateName;

    /**
     * 模板地址
     */
    @ApiModelProperty(notes = "模板路径")
    private String templatePath;

    /**
     * TemplateDesc
     */
    @ApiModelProperty(notes = "模板描述")
    private String templateDesc;

    /**
     * 文件名称
     */
    @ApiModelProperty(notes = "文件名称")
    private String fileName;
    /**
     * 本地路径
     */
    @ApiModelProperty(notes = "本地路径")
    private String localPath;
    /**
     * 组ID
     */
    @ApiModelProperty(notes = "模板组ID")
    private String groupId;
    /**
     * 模板组名称
     */
    @ApiModelProperty(notes = "模板组名称")
    private String groupName;
    /**
     * 模板类型
     */
    @ApiModelProperty(notes = "模板类型")
    private String templateType;

    @ApiModelProperty(notes = "创建时间")
    private Date crtTime;

    @ApiModelProperty(notes = "创建人")
    private String crtUserName;

    @ApiModelProperty(notes = "创建人id")
    private Integer crtUserId;

    @ApiModelProperty(notes = "修改人id")
    private Integer mdfUserId;

    @ApiModelProperty(notes = "修改人")
    private String mdfUserName;

    @ApiModelProperty(notes = "修改时间")
    private Date mdfTime;
}
