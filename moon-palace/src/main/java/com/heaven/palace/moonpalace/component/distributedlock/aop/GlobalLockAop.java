package com.heaven.palace.moonpalace.component.distributedlock.aop;

import com.heaven.palace.moonpalace.component.cache.constants.CacheLockConst;
import com.heaven.palace.moonpalace.component.distributedlock.annotion.GlobalLock;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/3 11:06
 */
@Aspect
@Component
@Slf4j
@Order(value = 2)
public class GlobalLockAop {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut(value = "@annotation(com.heaven.palace.moonpalace.component.distributedlock.annotion.GlobalLock)")
    private void cut() {
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        GlobalLock globalLock = currentMethod.getAnnotation(GlobalLock.class);
        BusinessCacheEnum businessCacheEnum = globalLock.businessCacheKey();
        String cacheKeyPrefix = businessCacheEnum.getCacheKeyPrefix();
        String lockKey = CacheLockConst.GLOBAL_LOCK_KEY_PREFIX.concat(businessCacheEnum.getModuleParam()).concat(":").concat(globalLock.lockKey());
        // 业务模块的锁需要具体到数据源
        if (StringUtils.isNotEmpty(cacheKeyPrefix)) {
            Object dbId = redissonClient.getBucket(cacheKeyPrefix.concat(ShiroKit.getUser().getAccount())).get();
            lockKey = lockKey.concat(String.valueOf(dbId));
        }

        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(10L, TimeUnit.SECONDS);
            log.info(DateTime.now().toString() + "lock redis lock success:{}", lockKey);
            return point.proceed();
        } finally {
            if (null != lock && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info(DateTime.now().toString() + "unlock redis lock success:{}", lockKey);
            }
        }
    }
}
