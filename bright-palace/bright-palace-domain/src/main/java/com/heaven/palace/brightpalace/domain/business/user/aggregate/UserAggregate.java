package com.heaven.palace.brightpalace.domain.business.user.aggregate;

import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Email;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.MobilePhone;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Password;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Username;
import com.heaven.palace.jasperpalace.base.ddd.AggregateRoot;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: zhoushengen
 * @Description: 用户聚合根
 * @DateTime: 2024/1/16 12:50
 **/
@Data
@Builder
public class UserAggregate implements AggregateRoot<PrimaryKey> {

    private PrimaryKey id;
    /**
     * 用户名称
     *
     */
    private Username username;
    /**
     * 用户密码
     *
     */
    private Password password;
    /**
     * 移动手机号
     *
     */
    private MobilePhone mobilePhone;
    /**
     * 邮箱
     *
     */
    private Email email;
    /**
     * 角色id
     *
     */
    private Long roleId;
    /**
     * 组织id
     *
     */
    private Long orgId;
    /**
     * 状态：0-使用中
     *
     */
    private Integer status;
}
