package com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.brightpalace.domain.business.user.repository.UserRepository;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.brightpalace.infrastructure.bo.UserRoleBO;
import com.heaven.palace.brightpalace.infrastructure.entity.BaseUserDO;
import com.heaven.palace.brightpalace.infrastructure.entity.UserMemberRelateDO;
import com.heaven.palace.brightpalace.infrastructure.mapper.BaseUserMapper;
import com.heaven.palace.brightpalace.infrastructure.mapper.UserMemberRelateMapper;
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


    @Override
    public Long save(UserAggregate userAggregate) {
        userAggregate.checkRegister();
        BaseUserDO baseUserDO = MappingUtils.beanConvert(userAggregate, BaseUserDO.class);
        baseUserMapper.insert(baseUserDO);
        userAggregate.getUserRoleEntities().forEach(role -> {
            UserMemberRelateDO userMemberRelateDO = new UserMemberRelateDO().setUserId(baseUserDO.getId())
                    .setRoleId(role.getId().getId());
            userMemberRelateMapper.insert(userMemberRelateDO);
        });
        return baseUserDO.getId();
    }

    @Override
    public List<UserRoleEntity> selectRole(UserRoleEntity userRoleEntity) {
        UserRoleBO userRoleBo = MappingUtils.beanConvert(userRoleEntity, UserRoleBO.class);
        return MappingUtils.beanListConvert(userRoleMapper.select(userRoleBo),
            UserRoleEntity.class);
    }


    @Override
    public List<UserAggregate> selectUser(UserAggregate userAggregate) {
        BaseUserDO baseUserDO = MappingUtils.beanConvert(userAggregate, BaseUserDO.class);
        return MappingUtils.beanListConvert(baseUserMapper.selectListByQuery(QueryWrapper.create(baseUserDO)), UserAggregate.class);
    }

    @Override
    public String multiIdentity() {
        return RepoRegisterConst.USER.concat(SystemOrganizationCodeConst.HEAVEN_PALACE);
    }
}
