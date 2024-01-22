package com.heaven.palace.brightpalace.application.service.user.impl;

import com.heaven.palace.brightpalace.api.api.user.vo.UserLoginPhoneAndPasswordVO;
import com.heaven.palace.brightpalace.api.api.user.vo.UserRegisterVO;
import com.heaven.palace.brightpalace.api.enums.user.BaseUserConst;
import com.heaven.palace.brightpalace.application.service.user.UserApplicationService;
import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.brightpalace.domain.business.oauth2.repository.Oauth2Repository;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.entity.UserRoleEntity;
import com.heaven.palace.brightpalace.domain.business.user.repository.UserRepository;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import com.heaven.palace.jasperpalace.business.system.context.SystemRoleCodeConst;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public void login(UserLoginPhoneAndPasswordVO userLoginPhoneAndPasswordVO, HttpServletRequest request, HttpServletResponse response) {
        // 参数校验放在映射时的构造方法里
        UserAggregate userAggregate = MappingUtils.beanConvert(userLoginPhoneAndPasswordVO, UserAggregate.class);
        // todo 验证码校验
        UserRepository userRepository = (UserRepository) multiRepoFactory.getMultiDateSource(RepoRegisterConst.USER);
        List<UserAggregate> userAggregates = userRepository.selectUser(userAggregate);
        if (CollectionUtils.isEmpty(userAggregates)) {
            throw new BusinessException(BusinessExceptionEnum.LOGIN_USER_QUERY_NULL_ERROR);
        }
        String query = request.getQueryString();
        if (null != query && query.contains(CommonConst.CLIENT_ID)) {
            String clientId = query.split(CommonConst.CLIENT_ID)[1].split(CommonConst.REQUEST_QUERY_PARAM_LINK)[0];
            Oauth2Repository oauth2Repository = (Oauth2Repository) multiRepoFactory.getMultiDateSource(RepoRegisterConst.OAUTH2);
            List<ClientEntity> clientEntities = oauth2Repository.select(new ClientEntity().setCode(clientId));
            if (CollectionUtils.isEmpty(clientEntities)) {
                throw new BusinessException(BusinessExceptionEnum.AUTH_CLIENT_QUERY_NULL_ERROR);
            }
            ClientEntity clientEntity = clientEntities.get(0);
            if (StringUtils.isNotEmpty(clientEntity.getLoginUrl())) {
                try {
                    response.sendRedirect(clientEntity.getLoginUrl().concat(query));
                } catch (IOException e) {
                    throw new BusinessException(BusinessExceptionEnum.AUTH_REDIRECT_CLIENT_AUTH_URL_ERROR);
                }
            }
        }

    }

    @Override
    public void register(UserRegisterVO userRegisterVO) {
        // 参数校验放在映射时的构造方法里
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
