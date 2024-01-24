package com.heaven.palace.brightpalace.domain.business.oauth2.service.impl;

import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.AuthTokenAggregate;
import com.heaven.palace.brightpalace.domain.business.oauth2.service.Oauth2DomainService;
import com.heaven.palace.brightpalace.domain.business.user.aggregate.UserAggregate;
import com.heaven.palace.brightpalace.domain.business.user.repository.UserRepository;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 统一认证领域服务接口实现
 * @DateTime: 2024/1/23 16:41
 **/
@Service
@Slf4j
public class Oauth2DomainServiceImpl implements Oauth2DomainService {

    @Resource
    private MultiRepoFactory multiRepoFactory;

    @Resource
    private DefaultObjectCache defaultObjectCache;

    @Override
    public AuthTokenAggregate loginByPasswordAndPhone(UserAggregate userAggregate) {
        // todo 验证码校验
        UserRepository userRepository = (UserRepository) multiRepoFactory.getMultiImplement(RepoRegisterConst.USER);
        List<UserAggregate> userAggregates = userRepository.selectUser(userAggregate);
        if (CollectionUtils.isEmpty(userAggregates)) {
            throw new BusinessException(BusinessExceptionEnum.LOGIN_USER_QUERY_NULL_ERROR);
        }
        UserAggregate userAggregateRes = userAggregates.get(0);
        return new AuthTokenAggregate().setUserAggregate(userAggregateRes);
    }
}
