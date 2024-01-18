package com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserOrganizationEntity;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.brightpalace.domain.business.user.repository.UserRepository;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.brightpalace.infrastructure.entity.BaseUserDO;
import com.heaven.palace.brightpalace.infrastructure.entity.UserMemberRelateDO;
import com.heaven.palace.brightpalace.infrastructure.entity.UserOrganizationDO;
import com.heaven.palace.brightpalace.infrastructure.entity.UserRoleDO;
import com.heaven.palace.brightpalace.infrastructure.mapper.BaseUserMapper;
import com.heaven.palace.brightpalace.infrastructure.mapper.UserMemberRelateMapper;
import com.heaven.palace.brightpalace.infrastructure.mapper.UserOrganizationMapper;
import com.heaven.palace.brightpalace.infrastructure.mapper.UserRoleMapper;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 10733
 * @date 2024/1/15 0:03
 * @description: 用户仓库实现类
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private BaseUserMapper baseUserMapper;

    @Resource
    private UserMemberRelateMapper userMemberRelateMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserOrganizationMapper userOrganizationMapper;

    @Override
    public Long save(UserAggregate userAggregate) {
        userAggregate.checkRegister();
        BaseUserDO baseUserDO = MappingUtils.beanConvert(userAggregate, BaseUserDO.class);
        baseUserMapper.insert(baseUserDO);
        UserMemberRelateDO userMemberRelateDO = new UserMemberRelateDO().setUserId(baseUserDO.getId())
            .setRoleId(userAggregate.getRole().getId().getId());
        userMemberRelateMapper.insert(userMemberRelateDO);
        return baseUserDO.getId();
    }

    @Override
    public List<UserRoleEntity> selectRole(UserRoleEntity userRoleEntity) {
        UserRoleDO userRoleDO = MappingUtils.beanConvert(userRoleEntity, UserRoleDO.class);
        return MappingUtils.beanListConvert(userRoleMapper.selectListByQuery(QueryWrapper.create(userRoleDO)),
            UserRoleEntity.class);
    }

    @Override
    public List<UserOrganizationEntity> selectOrg(UserOrganizationEntity userOrganizationEntity) {
        UserOrganizationDO userOrganizationDO = MappingUtils.beanConvert(userOrganizationEntity, UserOrganizationDO.class);
        return MappingUtils.beanListConvert(userOrganizationMapper.selectListByQuery(QueryWrapper.create(userOrganizationDO)),
            UserOrganizationEntity.class);
    }

    @Override
    public String multiIdentity() {
        return RepoRegisterConst.USER.concat(SystemOrganizationCodeConst.HEAVEN_PALACE);
    }
}
