package com.heaven.palace.brightpalace.application.service.permission;

import com.heaven.palace.brightpalace.api.api.permission.vo.CheckPermissionVO;
import reactor.core.publisher.Mono;

/**
 * @Author: zhoushengen
 * @Description: 权限应用层服务
 * @DateTime: 2024/1/31 16:23
 **/
public interface PermissionApplicationService {

    /**
     * 判断是否拥有资源权限
     * @param resourceValue
     * @param resourceType
     * @return
     */
    Mono<CheckPermissionVO> checkPermission(String resourceValue, Integer resourceType);
}
