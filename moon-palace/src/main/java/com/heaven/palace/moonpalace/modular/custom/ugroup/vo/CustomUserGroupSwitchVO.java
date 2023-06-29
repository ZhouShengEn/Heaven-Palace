package com.heaven.palace.moonpalace.modular.custom.ugroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/22 17:40
 */
@ApiModel(description = "角色开关数据结构")
@Data
public class CustomUserGroupSwitchVO implements Serializable {
    private static final long serialVersionUID = 4104174232453210166L;

    @ApiModelProperty(notes = "id")
    private Integer id;

    @ApiModelProperty(notes = "角色名称")
    private String name;

    @ApiModelProperty(notes = "权限id")
    private String authorityId;

    @ApiModelProperty(notes = "角色类型编码")
    private String typeCode;

    @ApiModelProperty(notes = "角色类型名称")
    private String typeName;


}
