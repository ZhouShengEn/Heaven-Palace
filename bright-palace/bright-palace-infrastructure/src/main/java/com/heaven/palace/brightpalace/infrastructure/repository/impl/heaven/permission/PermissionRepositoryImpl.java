package com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven.permission;

import com.heaven.palace.brightpalace.domain.business.permission.aggregate.entity.UserRoleResource;
import com.heaven.palace.brightpalace.domain.business.permission.repository.PermissionRepository;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.brightpalace.infrastructure.cache.permission.UserResourcePermissionsCache;
import com.heaven.palace.brightpalace.infrastructure.cache.permission.consts.UserPermissionCacheConst.UserCacheEnum;
import com.heaven.palace.brightpalace.infrastructure.cache.permission.param.UserPermissionCacheParam;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 权限仓库实现类
 * @DateTime: 2024/1/31 17:46
 **/
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    @Resource
    private UserResourcePermissionsCache userResourcePermissionsCache;


    @Override
    public List<UserRoleResource> getUserAllResourcePermission(Long userId) {
        return userResourcePermissionsCache.get(new UserPermissionCacheParam(UserCacheEnum.USER_RESOURCE_PERMISSIONS, userId));
    }

    @Override
    public String multiIdentity() {
        return RepoRegisterConst.PERMISSION.concat(SystemOrganizationCodeConst.HEAVEN_PALACE);
    }
}
