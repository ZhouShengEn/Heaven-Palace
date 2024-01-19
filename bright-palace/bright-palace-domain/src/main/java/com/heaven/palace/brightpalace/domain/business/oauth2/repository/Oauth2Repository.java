package com.heaven.palace.brightpalace.domain.business.oauth2.repository;

import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.jasperpalace.base.repository.MultiRepoInterface;

import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 统一认证仓库接口
 * @DateTime: 2024/1/19 17:36
 **/
public interface Oauth2Repository extends MultiRepoInterface {

    /**
     * 按条件查询所有客户端实体
     * @param clientEntity
     * @return
     */
    List<ClientEntity> select(ClientEntity clientEntity);
}
