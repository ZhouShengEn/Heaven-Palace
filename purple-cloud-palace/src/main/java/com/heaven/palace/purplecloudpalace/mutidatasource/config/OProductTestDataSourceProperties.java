package com.heaven.palace.purplecloudpalace.mutidatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 默认多数据源配置
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.business.product.test")
public class OProductTestDataSourceProperties {

    //默认的数据源名称
    private String defaultDataSourceName = "dataSourceProductTest";

    //默认多数据源的链接
    private String url = "jdbc:mysql://127.0.0.1:8080/DATESOURCE?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

    //默认多数据源的数据库账号
    private String username = "qinwanli";

    //默认多数据源的数据库密码
    private String password = "qinwanli";

    public void config(DruidDataSource dataSource) {
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    public void setDefaultDataSourceName(String defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }
}
