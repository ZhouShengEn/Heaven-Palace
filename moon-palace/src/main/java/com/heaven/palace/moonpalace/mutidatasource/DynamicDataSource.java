package com.heaven.palace.moonpalace.mutidatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.heaven.palace.moonpalace.constant.RedisCacheKeyConst;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource implements InitializingBean {
    
    @Autowired
    private RedissonClient redissonClient;

    private Map<Object, Object> resolvedDataSources = new ConcurrentHashMap<>(1);
    
    private DataSource defaultDateSource;

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
        Object lookupKey = determineCurrentLookupKey();
        return getDataSource(lookupKey);
    }

    public DataSource getDataSource(Object lookupKey) {
        if (lookupKey == null) {
            return defaultDateSource;
        }
        DataSource dataSource = (DataSource) this.resolvedDataSources.get(lookupKey);
        if (dataSource == null) {
            dataSource = initDateSource(lookupKey);
        }
        return dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }
    
    private DataSource initDateSource(Object lookupKey) {
        DataSource dataSource = null;
        String datasourceKey = RedisCacheKeyConst.GEN_DYNAMIC_DATASOURCE_KEY_PREFIX.concat(String.valueOf(lookupKey));
        Object res = redissonClient.getBucket(datasourceKey).get();
        if (null != res){
            try {
                DbInfoModel dbInfoModel= JSON.parseObject(res.toString(), DbInfoModel.class);
                DruidDataSource druidDataSource = (DruidDataSource) this.defaultDateSource;
                DruidDataSource dynamicDateSource = druidDataSource.cloneDruidDataSource();
                dynamicDateSource.setUseUnfairLock(druidDataSource.isUseUnfairLock());
                dynamicDateSource.setUsername(dbInfoModel.getDbUserName());
                dynamicDateSource.setPassword(dbInfoModel.getDbPassword());
                dynamicDateSource.setUrl(dbInfoModel.getDbUrl().replace("jdbc:", "jdbc:p6spy:"));
                dynamicDateSource.setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");
                dynamicDateSource.setDbType(dbInfoModel.getDbType());
                // druid 会先判断driver是否为空再去类加载DriverClassName
                dynamicDateSource.setDriver(null);
                dynamicDateSource.setDriverClassLoader(null);
                dynamicDateSource.setMaxActive(100);
                dynamicDateSource.setInitialSize(20);
                dynamicDateSource.setMaxWait(10000);
                dynamicDateSource.setConnectionErrorRetryAttempts(3);
                dynamicDateSource.setBreakAfterAcquireFailure(true);
                dynamicDateSource.setTimeBetweenEvictionRunsMillis(60000);
                dynamicDateSource.setMinIdle(2);
                dynamicDateSource.init();
                resolvedDataSources.put(lookupKey, dynamicDateSource);
                dataSource = dynamicDateSource;
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        } else {
            dataSource = defaultDateSource;
        }
        return dataSource;
    }

    public void setResolvedDataSources(Map<Object, Object> resolvedDataSources) {
        this.resolvedDataSources = resolvedDataSources;
    }

    public void setDefaultDateSource(DataSource defaultDateSource) {
        this.defaultDateSource = defaultDateSource;
    }
}
