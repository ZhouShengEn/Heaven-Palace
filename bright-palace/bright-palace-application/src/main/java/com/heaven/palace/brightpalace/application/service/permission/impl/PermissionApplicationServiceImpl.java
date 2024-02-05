package com.heaven.palace.brightpalace.application.service.permission.impl;

import com.heaven.palace.brightpalace.api.api.permission.vo.CheckPermissionVO;
import com.heaven.palace.brightpalace.application.service.permission.PermissionApplicationService;
import com.heaven.palace.brightpalace.domain.business.permission.repository.PermissionRepository;
import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.brightpalace.domain.factory.repository.MultiRepoFactory;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @Author: zhoushengen
 * @Description: 权限应用层服务实现类
 * @DateTime: 2024/1/31 16:24
 **/
@Service
@Slf4j
public class PermissionApplicationServiceImpl implements PermissionApplicationService {

    @Resource
    private MultiRepoFactory multiRepoFactory;

    @Override
    public Mono<CheckPermissionVO> checkPermission(String resourceValue, Integer resourceType) {
        log.info("permission service checkPermission enter userId:{}, resourceValue:{}, resourceType:{}",
            CurrentBaseContext.getUserId(), resourceValue, resourceType);
        PermissionRepository permissionRepository =
            (PermissionRepository) multiRepoFactory.getMultiImplement(RepoRegisterConst.PERMISSION);
        CheckPermissionVO checkPermissionVO = new CheckPermissionVO();
        try {
            boolean hasPermission = permissionRepository.getUserAllResourcePermission(CurrentBaseContext.getUserId())
                .parallelStream().anyMatch(userRoleResource -> userRoleResource.getResourceType().equal(resourceType)
                    && userRoleResource.checkResourcePermission(resourceValue));
            checkPermissionVO.setHasPermission(hasPermission);
            checkPermissionVO.setBaseContext(CurrentBaseContext.getAll());
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                BusinessException exception = (BusinessException) e;
                checkPermissionVO.setErrorResult(exception);
            } else {
                log.error("permission service checkPermission handle unknown error", e);
                checkPermissionVO.setErrorResult(new BusinessException(BusinessExceptionEnum.PERMISSION_UNKNOWN_EXCEPTION_ERROR));
            }
        }
        return Mono.just(checkPermissionVO);
    }

}
