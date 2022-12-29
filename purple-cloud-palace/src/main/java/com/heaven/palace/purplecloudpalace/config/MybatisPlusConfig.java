package com.heaven.palace.purplecloudpalace.config;

import com.heaven.palace.purplecloudpalace.mutidatasource.DynamicDataSource;
import com.heaven.palace.purplecloudpalace.mutidatasource.config.OProductDevDataSourceProperties;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.heaven.palace.purplecloudpalace.config.properties.DruidProperties;
import com.heaven.palace.purplecloudpalace.mutidatasource.config.OProductTestDataSourceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;

import com.heaven.palace.purplecloudpalace.common.constant.DSEnum;
import com.heaven.palace.purplecloudpalace.core.intercept.CodeInsertInterceptor;
import com.heaven.palace.purplecloudpalace.core.intercept.CodeUpdateInterceptor;
import com.heaven.palace.purplecloudpalace.core.datascope.DataScopeInterceptor;


/**
 * MybatisPlus配置
 *
 * @author ZhouShengEn
 * @Date 2017/5/20 21:58
 */
@Configuration
@EnableTransactionManagement(order = 2)//由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
@MapperScan(basePackages = {"com.heaven.palace.purplecloudpalace.modular.*.dao", "com.heaven.palace.purplecloudpalace.common.persistence.dao", "com.heaven.palace.purplecloudpalace.business.dao"})
public class MybatisPlusConfig {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    OProductDevDataSourceProperties productDevDataSourceProperties;
    
    @Autowired
    OProductTestDataSourceProperties productTestDataSourceProperties;


    /**
     * 另一个数据源
     */
    private DruidDataSource dataSourceProductDev() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        productDevDataSourceProperties.config(dataSource);
//        productTestDataSourceProperties.config(dataSource);
        return dataSource;
    }
    /**
     * 另一个数据源
     */
    private DruidDataSource dataSourceProductTest() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        productTestDataSourceProperties.config(dataSource);
        return dataSource;
    }

    
    private DruidDataSource dataSourceGuns() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return dataSourceGuns();
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource mutiDataSource() {

        DruidDataSource dataSourceGuns = dataSourceGuns();
        DruidDataSource dataSourceProductDev = dataSourceProductDev();
        DruidDataSource dataSourceProductTest = dataSourceProductTest();

        try {
            dataSourceGuns.init();
            dataSourceProductDev.init();
            dataSourceProductTest.init();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put(DSEnum.DATA_SOURCE_GENERATOR, dataSourceGuns);
        hashMap.put(DSEnum.DATA_SOURCE_PRODUCT_DEV, dataSourceProductDev);
        hashMap.put(DSEnum.DATA_SOURCE_PRODUCT_TEST, dataSourceProductTest);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(dataSourceGuns);
        return dynamicDataSource;
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据范围mybatis插件
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

    @Bean
    public CodeInsertInterceptor codeInsertInterceptor() {
        return new CodeInsertInterceptor();
    }

    @Bean
    public CodeUpdateInterceptor codeUpdateInterceptor() {
        return new CodeUpdateInterceptor();
    }
//    @Bean
//    public DbTypeInterceptor dbTypeInterceptor() {
//        return new DbTypeInterceptor();
//    }
}
