package com.heaven.palace.peakcloudpalace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@ComponentScan({"com.heaven.palace"})
public class PeakCloudPalaceApplication {

    protected final static Logger logger = LoggerFactory.getLogger(PeakCloudPalaceApplication.class);

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(PeakCloudPalaceApplication.class, args);
        logger.info("Application start success!");
    }

}
