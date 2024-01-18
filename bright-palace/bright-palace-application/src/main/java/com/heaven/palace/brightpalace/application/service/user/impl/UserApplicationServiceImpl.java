package com.heaven.palace.brightpalace.application.service.user.impl;

import com.heaven.palace.brightpalace.api.api.user.dto.UserAuthDTO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalace.api.enums.user.BaseUserConst;
import com.heaven.palace.brightpalace.application.service.user.UserApplicationService;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.brightpalace.domain.business.user.repository.UserRepository;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import com.heaven.palace.jasperpalace.business.system.context.SystemRoleCodeConst;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 10733
 * @date 2024/1/14 23:54
 * @description: 用户应用层服务实现
 */
@Service
@Slf4j
public class UserApplicationServiceImpl implements UserApplicationService {


    @Resource
    private MultiRepoFactory multiRepoFactory;

    @Override
    public UserAuthDTO auth(HttpRequestHandlerServlet request) {
        return null;
    }

    @Override
    public void register(UserRegisterVO userRegisterVO) {
        UserAggregate userAggregate = MappingUtils.beanConvert(userRegisterVO, UserAggregate.class);
        UserRepository userRepository = (UserRepository) multiRepoFactory.getMultiDateSource(RepoRegisterConst.USER);
        List<UserAggregate> queryPhoneAggregates = userRepository.selectUser(
                new UserAggregate()
                        .setMobilePhone(userAggregate.getMobilePhone())
                        .setStatus(BaseUserConst.Status.ACTIVE.getCode()));
        if (!CollectionUtils.isEmpty(queryPhoneAggregates)) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_USER_PHONE_REPEAT_ERROR);
        }
        List<UserAggregate> queryNameAggregates = userRepository.selectUser(
                new UserAggregate()
                        .setUsername(userAggregate.getUsername())
                        .setStatus(BaseUserConst.Status.ACTIVE.getCode()));
        if (!CollectionUtils.isEmpty(queryNameAggregates)) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_USER_NAME_REPEAT_ERROR);
        }
        UserRoleEntity userRoleEntity = new UserRoleEntity().setCode(SystemRoleCodeConst.BASE_USER)
                .setOrgCode(SystemOrganizationCodeConst.HEAVEN_PALACE);
        List<UserRoleEntity> userRoleEntities = userRepository.selectRole(userRoleEntity);
        if (CollectionUtils.isEmpty(userRoleEntities)) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_ROLE_OR_ORG_QUERY_NULL_ERROR);
        }
        userAggregate.setUserRoleEntities(userRoleEntities);
        userAggregate.changeStatus(BaseUserConst.Status.ACTIVE);
        userRepository.save(userAggregate);
    }
}
