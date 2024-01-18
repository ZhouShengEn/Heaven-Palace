package com.heaven.palace.brightpalace.infrastructure.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 10733
 * @date 2024/1/18 23:21
 * @description: 用户组织关联BO
 */
@Data
public class UserRoleBO implements Serializable {
    private static final long serialVersionUID = 2421136906637567812L;

    private Long id;
    /**
     * 角色名称
     *
     */
    private String roleName;
    /**
     * 角色编码
     *
     */
    private String code;
    /**
     * 组织id
     *
     */
    private Long orgId;
    /**
     * 父级角色id
     *
     */
    private Long parentId;
    /**
     * 描述
     *
     */
    private String description;
    /**
     * 组织名称
     *
     */
    private String orgName;
    /**
     * 组织编码
     *
     */
    private String orgCode;
    /**
     * 父级组织id
     *
     */
    private Long orgParentId;

}
