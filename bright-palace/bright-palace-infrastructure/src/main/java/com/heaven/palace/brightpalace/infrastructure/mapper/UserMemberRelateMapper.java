package com.heaven.palace.brightpalace.infrastructure.mapper;

import com.heaven.palace.brightpalace.infrastructure.entity.UserMemberRelateDO;
import com.heaven.palace.brightpalace.infrastructure.entity.UserRoleDO;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * UserMemberRelateMapper
 *
 * @author ZhouShengEn
 * @date 2024-01-12 12:45
 */
@Mapper
public interface UserMemberRelateMapper extends BaseMapper<UserMemberRelateDO> {

    List<UserRoleDO> selectByUserId(Long userId);
}
