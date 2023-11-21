package com.heaven.palace.heavensouthgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class HeavenSouthGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeavenSouthGateApplication.class, args);
    }

}
