package com.heaven.palace.brightpalace.domain.business.user.repository;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;

/**
 * @author 10733
 * @date 2024/1/14 23:59
 * @description: 用户仓库接口
 */
public interface UserRepository {

    void register(UserAggregate userAggregate);
}
