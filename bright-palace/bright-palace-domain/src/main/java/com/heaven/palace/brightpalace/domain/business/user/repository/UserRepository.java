package com.heaven.palace.brightpalace.domain.business.user.repository;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.jasperpalace.base.factory.repository.MultiRepoInterface;

import java.util.List;

/**
 * @author 10733
 * @date 2024/1/14 23:59
 * @description: 用户仓库接口
 */
public interface UserRepository extends MultiRepoInterface {

    /**
     * 保存新用户
     * @param userAggregate
     * @return 用户id
     */
    Long save(UserAggregate userAggregate);

    /**
     * 根据实体查询用户角色信息
     * @param userRoleEntity
     * @return
     */
    List<UserRoleEntity> selectRole(UserRoleEntity userRoleEntity);

    /**
     * 根据实体仅查询用户信息
     * @param userAggregate
     * @return
     */
    List<UserAggregate> selectUser(UserAggregate userAggregate);
}
