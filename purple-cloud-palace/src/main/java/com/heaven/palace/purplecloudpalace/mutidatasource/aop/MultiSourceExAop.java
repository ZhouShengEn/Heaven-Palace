package com.heaven.palace.purplecloudpalace.mutidatasource.aop;

import com.heaven.palace.purplecloudpalace.constant.RedisCacheKeyConst;
import com.heaven.palace.purplecloudpalace.core.shiro.ShiroKit;
import com.heaven.palace.purplecloudpalace.mutidatasource.DataSourceContextHolder;
import com.heaven.palace.purplecloudpalace.mutidatasource.annotion.DataSource;
import com.heaven.palace.purplecloudpalace.mutidatasource.config.OProductDevDataSourceProperties;
import com.heaven.palace.purplecloudpalace.business.constant.BusinessCacheEnum;
import com.heaven.palace.purplecloudpalace.business.constant.EnvEnum;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源切换的aop
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "true")
public class MultiSourceExAop implements Ordered {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OProductDevDataSourceProperties OProductDevDataSourceProperties;

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut(value = "@annotation(com.heaven.palace.purplecloudpalace.mutidatasource.annotion.DataSource)")
    private void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        DataSource datasource = currentMethod.getAnnotation(DataSource.class);
        if (datasource != null ) {
            if (StringUtils.isNotEmpty(datasource.name())){
                DataSourceContextHolder.setDataSourceType(datasource.name());
                log.debug("设置数据源为：" + datasource.name());

            }else {
                Object env = redissonClient.getBucket(datasource.businessCacheKey().concat(ShiroKit.getUser().getAccount())).get();
                String dBSource = null != env ? EnvEnum.getEnvById((Integer) env).getDBSource()
                        : OProductDevDataSourceProperties.getDefaultDataSourceName();
                DataSourceContextHolder.setDataSourceType(dBSource);
                log.debug("设置数据源为：" + dBSource);
            }
            
        } else {
            DataSourceContextHolder.setDataSourceType(OProductDevDataSourceProperties.getDefaultDataSourceName());
            log.debug("设置数据源为：dataSourceCurrent");
        }

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息！");
            DataSourceContextHolder.clearDataSourceType();
        }
    }


    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }

}
