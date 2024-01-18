package com.heaven.palace.brightpalace.domain.business.user.aggregate;

import com.heaven.palace.brightpalace.api.enums.user.BaseUserConst.Status;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserOrganizationEntity;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Email;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.MobilePhone;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Password;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Username;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.AggregateRoot;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Data;

/**
 * @Author: zhoushengen
 * @Description: 用户聚合根
 * @DateTime: 2024/1/16 12:50
 **/
@Data
// @Builder
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
     * 角色
     *
     */
    private UserRoleEntity role;
    /**
     * 组织
     *
     */
    private UserOrganizationEntity organization;
    /**
     * @see Status#getCode()
     *
     */
    private Integer status;

    public void checkRegister() {
        if (null == this.role || null == this.organization
            || null == this.role.getId() || null == this.organization.getId()) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_ROLE_OR_ORG_QUERY_NULL_ERROR);
        }
    }
}
