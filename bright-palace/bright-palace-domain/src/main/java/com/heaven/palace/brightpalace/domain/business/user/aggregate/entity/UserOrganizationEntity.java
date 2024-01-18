package com.heaven.palace.brightpalace.domain.business.user.aggregate.entity;

import com.heaven.palace.jasperpalace.base.ddd.Entity;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhoushengen
 * @Description: 实体-用户所在组织
 * @DateTime: 2024/1/18 14:45
 **/
@Data
@Accessors(chain = true)
public class UserOrganizationEntity implements Entity<PrimaryKey> {

    private PrimaryKey id;
    /**
     * 组织名称
     *
     */
    private String name;
    /**
     * 组织编码
     *
     */
    private String code;
    /**
     * 描述
     *
     */
    private String description;
    /**
     * 父级组织id
     *
     */
    private Long parentId;
}
