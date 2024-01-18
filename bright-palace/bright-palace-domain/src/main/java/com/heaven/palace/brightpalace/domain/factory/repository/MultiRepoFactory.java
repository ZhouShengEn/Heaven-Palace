package com.heaven.palace.brightpalace.domain.factory.repository;

import com.heaven.palace.brightpalace.domain.factory.repository.context.RepoRegisterConst;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.repository.MultiRepoInterface;
import com.heaven.palace.jasperpalace.business.system.context.SystemOrganizationCodeConst;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhoushengen
 * @Description: 多源仓库工厂类
 * @DateTime: 2024/1/18 16:22
 **/
@Component
public class MultiRepoFactory {

    private final Map<String, MultiRepoInterface> multiRepoInterfaceMap = new ConcurrentHashMap<>(1);

    @Resource
    private List<MultiRepoInterface> multiRepoInterfaces;


    @PostConstruct
    public void initMultiCloudRemoteService(){
        for (MultiRepoInterface multiRepoInterface : multiRepoInterfaces) {
            Optional.ofNullable(multiRepoInterface.multiIdentity()).ifPresent(key -> multiRepoInterfaceMap.put(key,
                multiRepoInterface));
        }
    }

    /**
     *
     * @param repoRegisterCode {@link RepoRegisterConst}
     * @return
     */
    public MultiRepoInterface getMultiDateSource(String repoRegisterCode) {
        // 默认使用天宫作为组织
        String orgCode =
            Optional.ofNullable(CurrentBaseContext.getUserOrgCode()).orElse(SystemOrganizationCodeConst.HEAVEN_PALACE);
        return multiRepoInterfaceMap.get(repoRegisterCode.concat(orgCode));
    }


}
