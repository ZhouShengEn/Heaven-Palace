package com.heaven.palace.brightpalace.application.service.user.impl;

import com.heaven.palace.brightpalace.api.api.user.dto.UserAuthDTO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalace.application.service.user.UserApplicationService;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserOrganizationEntity;
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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
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
        UserRepository userRepository = (UserRepository) multiRepoFactory.getMultiDateSource(RepoRegisterConst.USER);
        UserAggregate userAggregate = MappingUtils.beanConvert(userRegisterVO, UserAggregate.class);
        UserRoleEntity userRoleEntity = new UserRoleEntity().setCode(SystemRoleCodeConst.BASE_USER);
        UserOrganizationEntity userOrganizationEntity = new UserOrganizationEntity().setCode(
            SystemOrganizationCodeConst.HEAVEN_PALACE);

        List<UserRoleEntity> userRoleEntities = userRepository.selectRole(userRoleEntity);
        List<UserOrganizationEntity> userOrganizationEntities = userRepository.selectOrg(userOrganizationEntity);
        if (!ObjectUtils.allNotNull(userOrganizationEntities, userRoleEntities)) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_ROLE_OR_ORG_QUERY_NULL_ERROR);
        }
        userAggregate.setRole(userRoleEntities.get(0));
        userAggregate.setOrganization(userOrganizationEntities.get(0));
        userRepository.save(userAggregate);
    }
}
