package com.heaven.palace.lingxiaopalace.aspect;


import com.heaven.palace.lingxiaopalace.annotation.DataPermission;
import com.heaven.palace.lingxiaopalace.context.DataPermissionContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @author :zhoushengen
 * @date : 2023/1/30
 */
@Slf4j
@Component
@Aspect
public class DataPermissionAspect {

    @Around("@annotation(dataPermission)")
    public Object around(ProceedingJoinPoint joinPoint, DataPermission dataPermission) throws Throwable {
        dataPermission = AnnotationUtils.getAnnotation(dataPermission, DataPermission.class);
        if (dataPermission.open()){
            DataPermissionContext.local.set(dataPermission);
        } else {
            DataPermissionContext.local.remove();
        }
        Object obj;
        try {
            obj = joinPoint.proceed();
        } finally {
            // 删除上下文变量
            DataPermissionContext.local.remove();
        }
        return obj;
    }
}
