package com.heaven.palace.brightpalace.domain.business.user.aggregate.entity;

import com.heaven.palace.jasperpalace.base.ddd.Entity;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhoushengen
 * @Description: 实体-用户角色
 * @DateTime: 2024/1/17 15:58
 **/
@Data
@Accessors(chain = true)
public class UserRoleEntity implements Entity<PrimaryKey> {

    private static final long serialVersionUID = -1261869988830348296L;
    private PrimaryKey id;

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
