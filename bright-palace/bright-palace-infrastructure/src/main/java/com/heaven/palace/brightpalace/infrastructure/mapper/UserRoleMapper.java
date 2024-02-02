package com.heaven.palace.brightpalace.infrastructure.mapper;

import com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven.user.bo.UserRoleBO;
import com.heaven.palace.brightpalace.infrastructure.entity.UserRoleDO;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * UserRoleMapper
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:45
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    List<UserRoleBO> select(UserRoleBO userRoleBO);


}
