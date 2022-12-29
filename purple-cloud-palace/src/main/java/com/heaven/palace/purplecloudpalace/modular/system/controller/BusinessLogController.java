package com.heaven.palace.purplecloudpalace.modular.system.controller;

import com.heaven.palace.purplecloudpalace.common.annotion.Permission;
import com.heaven.palace.purplecloudpalace.common.constant.BusinessOperationLogConstant;
import com.heaven.palace.purplecloudpalace.common.constant.Const;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.modular.system.service.BusinessOperationLogService;
import com.heaven.palace.purplecloudpalace.business.constant.BusinessCacheEnum;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :zhoushengen
 * @date : 2022/9/7
 */
@RestController
@RequestMapping("/Business/log")
public class BusinessLogController {
    private final static String PREFIX = "/common/";
    @Autowired
    BusinessOperationLogService businessOperationLogService;
    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("/list")
    public Object list(String type, String cacheParam){
        
        return businessOperationLogService
                .queryListByType(type, "CREATE_TIME"
                        , (Integer) redissonClient.getBucket(BusinessCacheEnum.getCacheKeyPrefixByParam(cacheParam).concat(ShiroKit.getUser().getAccount())).get());
    }
}
