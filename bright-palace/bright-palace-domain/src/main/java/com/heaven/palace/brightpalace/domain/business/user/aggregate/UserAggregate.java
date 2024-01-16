package com.heaven.palace.brightpalace.domain.business.user.aggregate;

import com.heaven.palace.jasperpalace.base.ddd.AggregateRoot;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;

/**
 * @Author: zhoushengen
 * @Description: 用户聚合根
 * @DateTime: 2024/1/16 12:50
 **/
public class UserAggregate implements AggregateRoot<PrimaryKey> {

    private PrimaryKey id;
    private String username;
}
