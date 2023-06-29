package com.heaven.palace.moonpalace.modular.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DbinfoPageResVO implements Serializable {
    private static final long serialVersionUID = 7229668155863223273L;

    /**
     * Id
     */
    @ApiModelProperty(notes = "主键id")
    private Integer id;

    /**
     * 别名
     */
    @ApiModelProperty(notes = "别名")
    private String alias;

    /**
     * 数据库驱动
     */
    @ApiModelProperty(notes = "数据库驱动")
    private String dbDriver;

    /**
     * 数据库地址
     */
    @ApiModelProperty(notes = "数据库地址")
    private String dbUrl;

    /**
     * 数据库账户
     */
    @ApiModelProperty(notes = "数据库账户")
    private String dbUserName;

    /**
     * 连接密码
     */
    @ApiModelProperty(notes = "连接密码")
    private String dbPassword;

    /**
     * UserId
     */
    @ApiModelProperty(notes = "用户id")
    private Integer userId;

    /**
     * 数据库类型
     */
    @ApiModelProperty(notes = "数据库类型")
    private String dbType;

    @ApiModelProperty(notes = "是否属于默认数据源")
    private Boolean isDefault = Boolean.FALSE;

    @ApiModelProperty(notes = "标签")
    private String tag;

    /**
     * 创建人
     */
    @ApiModelProperty(notes = "创建人id")
    private Integer crtUserId;

    @ApiModelProperty(notes = "创建人名称")
    private String crtUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    private Date crtTime;

    /**
     * 修改人
     */
    @ApiModelProperty(notes = "修改人id")
    private Integer mdfUserId;

    @ApiModelProperty(notes = "修改人名称")
    private String mdfUserName;

    /**
     * 修改时间
     */
    @ApiModelProperty(notes = "修改时间")
    private Date mdfTime;
}
