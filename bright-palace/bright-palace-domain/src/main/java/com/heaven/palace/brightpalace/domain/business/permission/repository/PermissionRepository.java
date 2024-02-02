package com.heaven.palace.brightpalace.domain.business.permission.repository;

import com.heaven.palace.brightpalace.domain.business.permission.aggregate.entity.UserRoleResource;
import com.heaven.palace.jasperpalace.base.factory.repository.MultiRepoInterface;

import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 权限仓库
 * @DateTime: 2024/1/31 17:44
 **/
public interface PermissionRepository extends MultiRepoInterface {


    List<UserRoleResource> getUserAllResourcePermission(Long userId);
}
