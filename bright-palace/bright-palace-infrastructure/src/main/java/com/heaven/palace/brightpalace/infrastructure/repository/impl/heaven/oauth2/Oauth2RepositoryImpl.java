package com.heaven.palace.brightpalace.infrastructure.repository.impl.heaven.oauth2;

import com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.entity.ClientEntity;
import com.heaven.palace.brightpalace.domain.business.oauth2.repository.Oauth2Repository;
import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.brightpalace.infrastructure.entity.AuthClientDO;
import com.heaven.palace.brightpalace.infrastructure.mapper.AuthClientMapper;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import com.heaven.palace.purplecloudpalace.util.MappingUtils;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhoushengen
 * @Description: 统一认证仓库实现类
 * @DateTime: 2024/1/19 17:45
 **/
@Repository
public class Oauth2RepositoryImpl implements Oauth2Repository {

    @Resource
    private AuthClientMapper authClientMapper;


    @Override
    public List<ClientEntity> select(ClientEntity clientEntity) {
        AuthClientDO authClientDO = MappingUtils.beanConvert(clientEntity, AuthClientDO.class);
        return MappingUtils.beanListConvert(authClientMapper.selectListByQuery(QueryWrapper.create(authClientDO)),
            ClientEntity.class);
    }



    @Override
    public String multiIdentity() {
        return RepoRegisterConst.OAUTH2.concat(SystemOrganizationCodeConst.HEAVEN_PALACE);
    }
}
