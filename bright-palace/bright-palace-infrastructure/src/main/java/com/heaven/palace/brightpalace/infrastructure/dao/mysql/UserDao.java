package com.heaven.palace.brightpalace.infrastructure.dao.mysql;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.repository.UserRepository;
import com.heaven.palace.brightpalace.infrastructure.entity.BaseUserDO;
import com.heaven.palace.brightpalace.infrastructure.mapper.BaseUserMapper;
import com.heaven.palace.brightpalace.infrastructure.mapper.UserRoleMapper;
import com.heaven.palace.jasperpalace.business.system.context.DateSourceDriveConst;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author 10733
 * @date 2024/1/15 0:03
 * @description: 用户仓库实现类
 */
@Repository(DateSourceDriveConst.MYSQL+"UserDao")
public class UserDao implements UserRepository {

    @Resource
    private BaseUserMapper baseUserMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void register(UserAggregate userAggregate) {
        BaseUserDO baseUserDO = MappingUtils.beanConvert(userAggregate, BaseUserDO.class);
    }
}
