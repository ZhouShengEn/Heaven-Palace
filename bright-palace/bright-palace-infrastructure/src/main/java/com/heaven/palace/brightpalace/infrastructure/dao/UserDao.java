package com.heaven.palace.brightpalace.infrastructure.dao;

import com.heaven.palace.brightpalace.domain.repository.UserRepository;
import com.heaven.palace.brightpalace.infrastructure.mapper.BaseUserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author 10733
 * @date 2024/1/15 0:03
 * @description: 用户仓库实现类
 */
@Repository
public class UserDao implements UserRepository {

    @Resource
    private BaseUserMapper baseUserMapper;
}
