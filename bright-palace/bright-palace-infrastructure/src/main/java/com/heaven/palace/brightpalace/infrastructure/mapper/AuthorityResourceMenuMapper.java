package com.heaven.palace.brightpalace.infrastructure.mapper;

import com.heaven.palace.brightpalace.infrastructure.entity.AuthorityResourceMenuDO;
import com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven.permission.bo.UserRoleResourceBO;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AuthorityResourceMenuMapper
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:44
 */
@Mapper
public interface AuthorityResourceMenuMapper extends BaseMapper<AuthorityResourceMenuDO> {

    List<UserRoleResourceBO> selectResourceByRoleIds(List<Long> roleIds);

}
