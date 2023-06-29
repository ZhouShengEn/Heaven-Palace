package com.heaven.palace.moonpalace.intercept;

import com.heaven.palace.moonpalace.core.businesslog.context.LogCurrentDateHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 静态调用session的拦截器
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Aspect
@Component
public class BussinessLogInterceptor{
    

    @Pointcut("execution(* com.heaven.palace.moonpalace.*..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {
        
        try {
            return point.proceed();
        } finally {
            if (null != LogCurrentDateHolder.get()){
                LogCurrentDateHolder.clear();
                
            }
        }
    }
}
