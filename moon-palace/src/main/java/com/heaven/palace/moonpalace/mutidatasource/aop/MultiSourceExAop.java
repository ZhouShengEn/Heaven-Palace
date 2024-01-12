package com.heaven.palace.moonpalace.mutidatasource.aop;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.constant.BusinessLogConst;
import com.heaven.palace.moonpalace.core.businesslog.BusinessLogContextHolder;
import com.heaven.palace.moonpalace.core.businesslog.operation.BusinessLogOperationFactory;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.modular.code.model.CustomModuleModel;
import com.heaven.palace.moonpalace.modular.custom.global.constant.BusinessCacheEnum;
import com.heaven.palace.moonpalace.modular.system.dao.CustomModuleDao;
import com.heaven.palace.moonpalace.mutidatasource.DataSourceContextHolder;
import com.heaven.palace.moonpalace.mutidatasource.DataSourceDetermineContextHolder;
import com.heaven.palace.moonpalace.mutidatasource.annotion.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import javax.annotation.Resource;
import org.springframework.core.annotation.Order;
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
@Slf4j
@Order(value = 1)
public class MultiSourceExAop{

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private CustomModuleDao customModuleDao;

    @Pointcut(value = "@annotation(com.heaven.palace.moonpalace.mutidatasource.annotion.DataSource)")
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
        BusinessCacheEnum businessCacheEnum = datasource.businessCacheKey();
        // 查看是否有指定数据源
        Integer currentDataSource = DataSourceDetermineContextHolder.getCurrentDataSource();
        if (null == currentDataSource){
            // 未指定数据源时从redis中获取用户当前模块下数据源
            RBucket<Object> bucket = redissonClient.getBucket(businessCacheEnum.getCacheKeyPrefix().concat(ShiroKit.getUser().getAccount()));
            Object dbId = bucket.get();
            if (null == dbId) {
                // redis中没有信息时，取当前模块所属默认数据源
                LambdaQueryWrapper<CustomModuleModel> moduleQuery = new LambdaQueryWrapper<>();
                moduleQuery.eq(CustomModuleModel::getModuleParam, businessCacheEnum.getModuleParam());
                CustomModuleModel customModuleModel = customModuleDao.selectOne(moduleQuery);
                dbId = null != customModuleModel ?  customModuleModel.getDefaultDbId() : null;
                bucket.setAsync(dbId);
                log.info("寻找模块默认数据源：cacheParam:{}, dbId:{}", businessCacheEnum.getModuleParam(), dbId);
            }
            currentDataSource = (Integer) dbId;
        }

        DataSourceContextHolder.setDataSourceType(currentDataSource);
        log.info("设置数据源为：id:{}", currentDataSource);

        // sql日志记录
        if (StringUtils.isNotEmpty(datasource.logSqlType().getOperation())){
            BusinessOperationLog businessOperationLog = new BusinessOperationLog();
            businessOperationLog.setDbId(currentDataSource);
            businessOperationLog.setTitle(datasource.logSqlType().getOperation());
            businessOperationLog.setType(businessCacheEnum.getModuleParam());
            businessOperationLog.setSyncStatus(BusinessLogConst.SyncStatusEnum.Need_Synchronize.getStatus());
            BusinessLogContextHolder.set(businessOperationLog);
        }
        try {
            Object proceed = point.proceed();
            BusinessLogOperationFactory.route(BusinessLogConst.SqlTypeEnum.getOperationBeanNameByOperate(datasource.logSqlType().getOperation())).writeBusinessLog();
            return proceed;
        } finally {
            log.info("清空数据源和临时日志对象信息！");
            DataSourceContextHolder.clearDataSourceType();
            BusinessLogOperationFactory.BUSINESS_LOG_TEMP_OBJECT.remove();
        }
    }

}
