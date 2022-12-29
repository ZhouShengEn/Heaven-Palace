package com.heaven.palace.purplecloudpalace.core.intercept;//package com.heaven.palace.purplecloudpalace.core.intercept;
//
//
//import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.util.Map;
//import java.util.Properties;
//
///**
// * 数据库类型支持 Created by ZhouShengEn on 2017/9/24. int update(MappedStatement ms, Object parameter) throws
// * SQLException;
// *
// * <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler
// * resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;
// *
// * <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler
// * resultHandler) throws SQLException;
// */
//@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
//public class DbTypeInterceptor implements Interceptor {
//
//    private static String TYPE = "";
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        //遍历处理所有参数，update方法有两个参数，参见Executor类中的update()方法。
//        for (int i = 0; i < args.length; i++) {
//            Object parameterObject = args[i];
//            //第一个参数处理。根据它判断是否给“操作属性”赋值。
//            if (StringUtils.isEmpty(TYPE) && parameterObject instanceof MappedStatement) {//如果是第一个参数 MappedStatement
//                MappedStatement ms = (MappedStatement) parameterObject;
//                TYPE = GlobalConfigUtils.getGlobalConfig(ms.getConfiguration()).getDbConfig().getIdType().name().toUpperCase();
//            } else if (parameterObject instanceof Map) {//如果是map，有两种情况：（1）使用@Param多参数传入，由Mybatis包装成map。（2）原始传入Map
//                Map map = (Map) parameterObject;
//                map.put("_databaseType",TYPE);
//            }
//        }
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//}
//
