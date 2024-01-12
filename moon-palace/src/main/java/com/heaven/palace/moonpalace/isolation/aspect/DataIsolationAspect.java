package com.heaven.palace.moonpalace.isolation.aspect;


import com.heaven.palace.moonpalace.isolation.annotation.DataIsolation;
import com.heaven.palace.moonpalace.isolation.context.DataIsolationContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 * 数据接口隔离拦截面
 */
@Slf4j
@Component
@Aspect
public class DataIsolationAspect {

    @Around("@annotation(dataIsolation)")
    public Object around(ProceedingJoinPoint joinPoint, DataIsolation dataIsolation) throws Throwable {
        dataIsolation = AnnotationUtils.getAnnotation(dataIsolation, DataIsolation.class);
        if (dataIsolation.open()){
            DataIsolationContext.local.set(dataIsolation);
        } else {
            DataIsolationContext.local.remove();
        }
        Object obj;
        try {
            obj = joinPoint.proceed();
        } finally {
            // 删除上下文变量
            DataIsolationContext.local.remove();
        }
        return obj;
    }
}
