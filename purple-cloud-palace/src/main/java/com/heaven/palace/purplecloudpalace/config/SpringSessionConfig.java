package com.heaven.palace.purplecloudpalace.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * spring session配置
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)  //session过期时间  如果部署多机环境,需要打开注释
@ConditionalOnProperty(prefix = "guns", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {


}
