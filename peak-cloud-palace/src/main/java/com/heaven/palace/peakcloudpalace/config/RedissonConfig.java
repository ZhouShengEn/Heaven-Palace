package com.heaven.palace.peakcloudpalace.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.HashMap;

/**
 * @author :zhoushengen
 * @date : 2022/8/11
 */
@Configuration
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
public class RedissonConfig {
    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.pingConnectionInterval:1000}")
    private int pingConnectionInterval;

    @Value("${spring.redis.retryAttempts:3}")
    private int retryAttempts;

    @Value("${spring.redis.timeout:3000}")
    private int timeout;

    @Value("${spring.redis.retryInterval:1500}")
    private int retryInterval;

    @Value("${spring.redis.connectionPoolSize:64}")
    private int connectionPoolSize;

    @Value("${spring.redis.connectionMinimumIdleSize:32}")
    private int connectionMinimumIdleSize;


    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        // redis单节点连接配置
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig
                .setAddress("redis://" + host + ":" + port)
                .setDatabase(database)
                // 以下配置是为了在单节点下保证生产环境Redis的稳定性并且动态控制其吞吐量
                // redis心跳检测
                .setPingConnectionInterval(pingConnectionInterval)
                // 命令等待超时
                .setTimeout(timeout)
                // 命令重试次数
                .setRetryAttempts(retryAttempts)
                // 命令重试发送时间间隔
                .setRetryInterval(retryInterval)
                // 连接池最大容量
                .setConnectionPoolSize(connectionPoolSize)
                // 最小保持连接数（长连接）
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize);

        if(StringUtils.isNotEmpty(password)){
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient){
        HashMap<String, CacheConfig> config = new HashMap<>(1);
        // 创建一个名称为"springCacheMap"的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
        config.put("springCacheMap", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }


}
