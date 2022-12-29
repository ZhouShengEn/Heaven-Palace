package com.heaven.palace.purplecloudpalace.business.controller;


import com.heaven.palace.purplecloudpalace.base.controller.BaseController;
import com.heaven.palace.purplecloudpalace.business.constant.BusinessCacheEnum;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :zhoushengen
 * @date : 2022/9/8
 */
@RequestMapping("/business/env")
@RestController
@Slf4j
public class EnvController extends BaseController {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 切换环境
     * @param env
     * @param cacheParam
     * @return
     */
    @RequestMapping(value = "change")
    public Object changeEnv(Integer env, String cacheParam) {
        redissonClient.getBucket(BusinessCacheEnum.getCacheKeyPrefixByParam(cacheParam).concat(ShiroKit.getUser().getAccount())).set(env);
        return SUCCESS_TIP;
    }
}
