package com.heaven.palace.peakcloudpalace.config;


import com.heaven.palace.purplecloudpalace.interceptor.LogInterceptor;
import com.heaven.palace.purplecloudpalace.interceptor.UserAuthInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

/**
 * @Author: zhoushengen
 * @Description: web配置
 * @DateTime: 2024/1/10 16:40
 **/
@AutoConfiguration
@Primary
public class WebConfiguration implements WebMvcConfigurer {

    public static final String PATH_PATTERN_ALL = "/**";


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置用户认证拦截器
        registry.addInterceptor(getUserAuthInterceptor()).addPathPatterns(PATH_PATTERN_ALL).excludePathPatterns(
            new ArrayList<String>() {{
            add("/swagger**/**");
            add("/webjars/**");
            add("/v3/**");
            add("/doc.html");
        }});
        // 全局日志链路追踪
        registry.addInterceptor(getLogInterceptor()).addPathPatterns(PATH_PATTERN_ALL);
    }

    @Bean
    UserAuthInterceptor getUserAuthInterceptor() {
        return new UserAuthInterceptor();
    }

    @Bean
    LogInterceptor getLogInterceptor() {
        return new LogInterceptor();
    }


}
