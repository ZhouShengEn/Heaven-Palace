package com.heaven.palace.brightpalace.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@ComponentScan({"com.heaven.palace"})
@EnableFeignClients
@MapperScan({"com.heaven.palace.brightpalaceinfrastructure.mapper"})
public class BrightPalaceStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrightPalaceStartApplication.class, args);
    }

}
