package com.heaven.palace.moonpalace.modular.custom.authority.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/24 16:32
 */
@Data
@ApiModel(description = "资源前置数据-入参")
public class ResourceDataPreVO implements Serializable {
    private static final long serialVersionUID = -2035167388915273815L;

    @ApiModelProperty(notes = "资源id")
    private Integer resourceId;

    @ApiModelProperty(notes = "角色编码列表", required = true)
    private List<String> groupTypeCodes;
}
