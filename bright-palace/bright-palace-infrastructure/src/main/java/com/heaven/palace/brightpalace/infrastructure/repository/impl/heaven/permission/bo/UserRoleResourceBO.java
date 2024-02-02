package com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven.permission.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 用户角色资源
 * @DateTime: 2024/1/31 18:36
 **/
@Data
public class UserRoleResourceBO implements Serializable {
    private static final long serialVersionUID = -3186032967600740910L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源id
     */
    private Long resourceId;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源值
     */
    private String resourceValue;

    /**
     * 资源类型
     */
    private Integer resourceType;

    /**
     * 资源状态
     */
    private Integer resourceStatus;
}
