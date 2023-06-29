package com.heaven.palace.moonpalace;

import com.mzt.logapi.starter.annotation.EnableLogRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@SpringBootApplication
@EnableLogRecord(tenant = "com.heaven.palace.moonpalace")
@EnableTransactionManagement
@ServletComponentScan
public class MoonPalaceApplication {

    protected final static Logger logger = LoggerFactory.getLogger(MoonPalaceApplication.class);

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(MoonPalaceApplication.class, args);
        logger.info("Application start success!");
    }

}
