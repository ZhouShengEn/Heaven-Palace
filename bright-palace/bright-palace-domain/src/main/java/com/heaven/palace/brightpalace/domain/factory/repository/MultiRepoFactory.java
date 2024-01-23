package com.heaven.palace.brightpalace.domain.factory.repository;

import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.factory.AbstractMultiFactory;
import com.heaven.palace.jasperpalace.base.factory.repository.MultiRepoInterface;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: zhoushengen
 * @Description: 多源仓库工厂类
 * @DateTime: 2024/1/18 16:22
 **/
@Component
public class MultiRepoFactory extends AbstractMultiFactory<MultiRepoInterface> {

    /**
     *
     * @param repoRegisterCode {@link RepoRegisterConst}
     * @return
     */
    @Override
    public MultiRepoInterface getMultiImplement(String repoRegisterCode) {
        // 默认使用天宫作为组织
        String orgCode =
            Optional.ofNullable(CurrentBaseContext.getClientOrgCode()).orElse(SystemOrganizationCodeConst.HEAVEN_PALACE);
        return multiInterfaceMap.get(repoRegisterCode.concat(orgCode));
    }


}
