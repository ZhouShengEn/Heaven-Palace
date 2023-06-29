package com.heaven.palace.moonpalace.modular.custom.element.vo;

import com.heaven.palace.moonpalace.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/27 15:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "基础接口分页查询-入参")
public class CustomElementPageReqVO extends PageVO {
    private static final long serialVersionUID = 7041644729798350280L;
    /**
     * Id
     */
    @ApiModelProperty(notes = "Id")
    private Integer id;
    /**
     * 资源类型
     *
     */
    @ApiModelProperty(notes = "资源类型")
    private String type;
    /**
     * 模糊查询：name + uri
     *
     */
    @ApiModelProperty(notes = "模糊查询：name + uri")
    private String fuzzyQuery;
    /**
     * 资源请求类型
     *
     */
    @ApiModelProperty(notes = "资源请求类型,枚举：POST、GET、PUT、DELETE、PATCH")
    private String method;
}
