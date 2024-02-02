package com.heaven.palace.brightpalace.infrastructure.cache.permission;

import com.heaven.palace.brightpalace.domain.business.permission.aggregate.entity.UserRoleResource;
import com.heaven.palace.brightpalace.infrastructure.cache.permission.param.UserPermissionCacheParam;
import com.heaven.palace.brightpalace.infrastructure.entity.UserRoleDO;
import com.heaven.palace.brightpalace.infrastructure.mapper.AuthorityResourceMenuMapper;
import com.heaven.palace.brightpalace.infrastructure.mapper.UserMemberRelateMapper;
import com.heaven.palace.brightpalace.infrastructure.mapper.UserRoleMapper;
import com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven.permission.bo.UserRoleResourceBO;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import com.heaven.palace.purplecloudpalace.component.cache.AbstractRBucketCache;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhoushengen
 * @Description: 用户权限缓存
 * @DateTime: 2024/1/30 16:13
 **/
@Service
public class UserResourcePermissionsCache extends AbstractRBucketCache<UserPermissionCacheParam, List<UserRoleResource>> {

    @Resource
    private UserMemberRelateMapper userMemberRelateMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private AuthorityResourceMenuMapper authorityResourceMenuMapper;


    @Override
    protected List<UserRoleResource> getFromOther(UserPermissionCacheParam cacheParam) {
        ArrayList<Long> roleIds = new ArrayList<>();
        Long userId = cacheParam.getUserId();
        List<UserRoleDO> userRoleDOs = userMemberRelateMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleDOs)) {
            return null;
        }
        queryUserAllRole(roleIds, userRoleDOs);
        List<UserRoleResourceBO> userRoleResourceBOs = authorityResourceMenuMapper.selectResourceByRoleIds(roleIds);
        return userRoleResourceBOs.stream().map(resource -> {
            UserRoleResource userRoleResource = MappingUtils.beanConvert(resource, UserRoleResource.class);
            userRoleResource.setUserId(new PrimaryKey(userId));
            return userRoleResource;
        }).collect(Collectors.toList());
    }

    private void queryUserAllRole(ArrayList<Long> roleIds, List<UserRoleDO> userRoleDOs) {
        if (!CollectionUtils.isEmpty(userRoleDOs)) {
            for (UserRoleDO userRoleDO : userRoleDOs) {
                roleIds.add(userRoleDO.getId());
                List<UserRoleDO> childUserRole = userRoleMapper.selectListByQuery(
                    QueryWrapper.create(new UserRoleDO().setParentId(userRoleDO.getId())));
                queryUserAllRole(roleIds, childUserRole);
            }
        }

    }
}
