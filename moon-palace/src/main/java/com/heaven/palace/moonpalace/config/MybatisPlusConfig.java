package com.heaven.palace.moonpalace.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.heaven.palace.moonpalace.common.constant.DSEnum;
import com.heaven.palace.moonpalace.config.properties.DruidProperties;
import com.heaven.palace.moonpalace.core.datascope.DataScopeInterceptor;
import com.heaven.palace.moonpalace.intercept.CodeInsertInterceptor;
import com.heaven.palace.moonpalace.intercept.CodeUpdateInterceptor;
import com.heaven.palace.moonpalace.mutidatasource.DynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;


/**
 * MybatisPlus配置
 *
 * @author ZhouShengEn
 * @Date 2017/5/20 21:58
 */
@Configuration
@EnableTransactionManagement(order = 2)//由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
@MapperScan(basePackages = {"com.heaven.palace.moonpalace.modular.*.dao", "com.heaven.palace.moonpalace.common.persistence.dao", "com.heaven.palace.moonpalace.modular.custom.*.dao"})
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
public class MybatisPlusConfig {

    @Resource
    DruidProperties druidProperties;

    /**
     * generator的数据源
     */
    private DruidDataSource dataSourceGuns() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }


    /**
     * 多数据源连接池配置
     */
    @Bean(name = "dynamicDateSource")
    @Primary
    public DynamicDataSource dynamicDateSource() {
        DruidDataSource druidDataSource = dataSourceGuns();
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultDateSource(druidDataSource);
        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put(DSEnum.DATA_SOURCE_GENERATOR, druidDataSource);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(druidDataSource);
        dynamicDataSource.setResolvedDataSources(hashMap);
        return dynamicDataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dynamicDateSource") DataSource dateSource){
        return new JdbcTemplate(dateSource);
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

}
