package com.heaven.palace.purplecloudpalace.aop.aspect;

import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import com.heaven.palace.purplecloudpalace.aop.annotation.RepeatSubmit;
import com.heaven.palace.purplecloudpalace.component.cache.DefaultObjectCache;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: zhoushengen
 * @Description: 幂等性校验
 * @DateTime: 2023/8/4 17:40
 **/
@Aspect
@Component
public class RepeatSubmitAop implements BeanFactoryAware {

    @Autowired
    private DefaultObjectCache defaultObjectCache;

    private BeanFactory beanFactory;

    @SneakyThrows
    @Around("@within(repeatSubmit) || @annotation(repeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) {
        String allowRepeatTime = repeatSubmit.allowRepeatTime();
        if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
            allowRepeatTime = ((ConfigurableBeanFactory) this.beanFactory).resolveEmbeddedValue(allowRepeatTime);
        }
        long releaseTime = Long.parseLong(allowRepeatTime);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest();
        String path = URLUtil.getPath(request.getRequestURI());
        // String clientIP = ServletUtil.getClientIP(request);
        Object[] args = joinPoint.getArgs();
        String params = "";
        if (null != args && args.length > 0) {
            List<Object> filterParams = Arrays.stream(args)
                .filter(arg -> !(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse)).collect(
                    Collectors.toList());
            params = JSON.toJSONString(filterParams);
        }
        String userId = null != CurrentBaseContext.getUserId() ? String.valueOf(CurrentBaseContext.getUserId()) : "null";
        // redis 锁
        String redisKey = "prefix"
            .concat(userId).concat(":")
            .concat(path)
            // .concat(String.valueOf(Math.abs(clientIP.hashCode())))
            .concat(String.valueOf(Math.abs(SecureUtil.md5(params).hashCode())));
        RLock lock = (RLock) defaultObjectCache.getLock(redisKey);
        if (lock.isLocked()) {
            throw new BusinessException(CommonExceptionEnum.REPEAT_SUBMIT_ERROR.getStatusCode(), repeatSubmit.repeatMsg());
        } else if (repeatSubmit.waitForComplete()){
            try {
                lock.lock();
                return joinPoint.proceed();
            } finally {
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } else {
            lock.lock(releaseTime, repeatSubmit.repeatUnit());
            return joinPoint.proceed();
        }

    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
