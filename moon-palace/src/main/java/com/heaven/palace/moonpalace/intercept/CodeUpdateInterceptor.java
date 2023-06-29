package com.heaven.palace.moonpalace.intercept;

import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 统一更新拦截 Created by ZhouShengEn on 2017/9/13.
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class CodeUpdateInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeInsertInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        //遍历处理所有参数，update方法有两个参数，参见Executor类中的update()方法。
        for (int i = 0; i < args.length; i++) {
            Object parameterObject = args[i];
            //第一个参数处理。根据它判断是否给“操作属性”赋值。
            if (parameterObject instanceof MappedStatement) {//如果是第一个参数 MappedStatement
                MappedStatement ms = (MappedStatement) parameterObject;
                SqlCommandType sqlCommandType = ms.getSqlCommandType();
                if (sqlCommandType == SqlCommandType.UPDATE) {//如果是“增加”或“更新”操作，则继续进行默认操作信息赋值。否则，则退出
                    continue;
                } else {
                    break;
                }
            } else if (parameterObject instanceof Map) {//如果是map，有两种情况：（1）使用@Param多参数传入，由Mybatis包装成map。（2）原始传入Map
                Map map = (Map) parameterObject;
                for (Object obj : map.values()) {
                    setProperty(obj);
                }
            } else {//原始参数传入
                setProperty(parameterObject);
            }
        }
        return invocation.proceed();
    }

    private void setProperty(Object parameterObject) {
        //设置参数值
        Method method;
        try {
            if ((method = ReflectionUtils.findMethod(parameterObject.getClass(), "setMdfUserId", Integer.class)) != null) {
                ReflectionUtils.invokeMethod(method, parameterObject, ShiroKit.getUser().getId());
            }
            if ((method = ReflectionUtils.findMethod(parameterObject.getClass(), "setMdfTime", Date.class)) != null) {
                ReflectionUtils.invokeMethod(method, parameterObject, new Date());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
