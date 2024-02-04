package com.heaven.palace.brightpalace.domain.business.user.aggregate;

import com.heaven.palace.brightpalace.api.enums.user.BaseUserConst;
import com.heaven.palace.brightpalace.api.enums.user.BaseUserConst.Status;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Email;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.MobilePhone;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Password;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.value.Username;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext.UserCache;
import com.heaven.palace.jasperpalace.base.ddd.AggregateRoot;
import com.heaven.palace.jasperpalace.base.ddd.PrimaryKey;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 用户聚合根
 * @DateTime: 2024/1/16 12:50
 **/
@Data
@Accessors(chain = true)
public class UserAggregate implements AggregateRoot<PrimaryKey> {

    private static final long serialVersionUID = -4470594073172746008L;
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
     * 用户角色组织
     *
     */
    private List<UserRoleEntity> userRoleEntities;
    /**
     * @see Status#getCode()
     *
     */
    private Integer status;

    /**
     * 用户注册校验，不允许存在空无意义的组织和角色
     */
    public void checkRegister() {
        if (CollectionUtils.isEmpty(this.userRoleEntities) || userRoleEntities.stream().anyMatch(role ->
                null == role.getId().getId() || null == role.getOrgId())) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_ROLE_OR_ORG_QUERY_NULL_ERROR);
        }
    }

    /**
     * 变更状态
     * @param status
     */
    public void changeStatus(BaseUserConst.Status status) {
        this.status = status.getCode();
    }

    /**
     * 转换用户缓存
     * @return
     */
    public UserCache convert2UserCache() {
        return new UserCache().setUserId(id.getId()).setUsername(username.getValue());
    }
}
