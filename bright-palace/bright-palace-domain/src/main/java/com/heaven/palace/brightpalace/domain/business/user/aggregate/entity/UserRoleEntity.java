package com.heaven.palace.brightpalace.domain.business.user.aggregate.entity;

import com.heaven.palace.jasperpalace.base.ddd.Entity;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;

/**
 * @Author: zhoushengen
 * @Description: 实体-用户角色
 * @DateTime: 2024/1/17 15:58
 **/
public class UserRoleEntity implements Entity<PrimaryKey> {

    private PrimaryKey id;

    /**
     * 角色名称
     *
     */
    private String roleName;
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
}
