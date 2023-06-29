package com.heaven.palace.moonpalace.modular.custom.authority.vo;

import com.heaven.palace.moonpalace.base.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "权限分页查询-入参")
public class AuthorityPageReqVO extends PageVO {

    private static final long serialVersionUID = 6120984229974088546L;

    @ApiModelProperty(notes = "id")
    private Integer id;

    /**
     * 角色ID
     *
     */
    @ApiModelProperty(notes = "角色ID")
    private String authorityId;
    /**
     * 角色类型
     *
     */
    @ApiModelProperty(notes = "角色类型")
    private String authorityType;
    /**
     * 资源ID
     *
     */
    @ApiModelProperty(notes = "资源ID")
    private String resourceId;
    /**
     * 资源类型
     *
     */
    @ApiModelProperty(notes = "资源类型")
    private String resourceType;
    /**
     * ParentId
     *
     */
    @ApiModelProperty(notes = "父级id")
    private String parentId;
    /**
     * Path
     *
     */
    @ApiModelProperty(notes = "路径")
    private String path;
}
