package com.heaven.palace.brightpalace.domain.business.user.repository;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserOrganizationEntity;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.jasperpalace.base.repository.MultiRepoInterface;

import java.util.List;

/**
 * @author 10733
 * @date 2024/1/14 23:59
 * @description: 用户仓库接口
 */
public interface UserRepository extends MultiRepoInterface {

    /**
     *
     * @param userAggregate
     * @return 用户id
     */
    Long save(UserAggregate userAggregate);

    List<UserRoleEntity> selectRole(UserRoleEntity userRoleEntity);

    List<UserOrganizationEntity> selectOrg(UserOrganizationEntity userOrganizationEntity);
}
