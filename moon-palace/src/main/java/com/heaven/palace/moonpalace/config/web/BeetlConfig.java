package com.heaven.palace.moonpalace.config.web;

import com.heaven.palace.moonpalace.config.properties.BeetlProperties;
import com.heaven.palace.moonpalace.core.beetl.BeetlConfiguration;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * web 配置类
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
@Configuration
public class BeetlConfig {

    @Resource
    BeetlProperties beetlProperties;

    /**
     * beetl的配置
     */
    @Bean(initMethod = "init")
    public BeetlConfiguration beetlConfiguration() {
        BeetlConfiguration beetlConfiguration = new BeetlConfiguration();
        beetlConfiguration.setResourceLoader(new ClasspathResourceLoader(BeetlConfig.class.getClassLoader(), beetlProperties.getPrefix()));
        beetlConfiguration.setConfigProperties(beetlProperties.getProperties());
        return beetlConfiguration;
    }

    /**
     * beetl的视图解析器
     */
    @Bean
    public BeetlSpringViewResolver beetlViewResolver() {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setConfig(beetlConfiguration());
        // beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        // beetlSpringViewResolver.setOrder(0);
        return beetlSpringViewResolver;
    }
}
