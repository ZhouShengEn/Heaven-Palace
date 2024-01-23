package com.heaven.palace.jasperpalace.base.factory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhoushengen
 * @Description: 多源抽象工厂
 * @DateTime: 2024/1/23 17:21
 **/
public abstract class AbstractMultiFactory<T extends MultiInterface> {
    public final Map<String, T> multiInterfaceMap = new ConcurrentHashMap<>(1);

    @Resource
    private List<T> multiInterfaces;

    @PostConstruct
    public void initMultiCloudRemoteService(){
        for (T multiInterface : multiInterfaces) {
            Optional.ofNullable(multiInterface.multiIdentity()).ifPresent(key -> multiInterfaceMap.put(key,
                multiInterface));
        }
    }

    public T getMultiImplement(String multiIdentity) {
        return multiInterfaceMap.get(multiIdentity);
    }
}
