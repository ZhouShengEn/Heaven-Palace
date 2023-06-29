package com.heaven.palace.moonpalace.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: linzhe
 * @date: 2022/6/15 8:55
 */
@Configuration
public class MinioConfig {
    @Value("${minio.apiUrl}")
    private String url;
    @Value("${minio.username}")
    private String username;
    @Value("${minio.password}")
    private String password;


    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(this.url)
                .credentials(this.username, this.password)
                .build();
    }
}
