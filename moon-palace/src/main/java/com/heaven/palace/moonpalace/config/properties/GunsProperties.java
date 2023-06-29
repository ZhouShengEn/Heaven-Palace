package com.heaven.palace.moonpalace.config.properties;

import com.heaven.palace.moonpalace.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


/**
 * guns项目配置
 *
 * @author ZhouShengEn
 * @Date 2017/5/23 22:31
 */
@Component
@ConfigurationProperties(prefix = GunsProperties.PREFIX)
@Slf4j
public class GunsProperties {

    public static final String PREFIX = "guns";   
    public static final String UPLOAD_ABSOLUTE_PATH 
            = "static".concat(File.separator).concat("img").concat(File.separator).concat("temp").concat(File.separator);

    private Boolean kaptchaOpen = false;

    private Boolean swaggerOpen = true;

    private String fileUploadPath;

    private Boolean haveCreatePath = false;

    private Boolean springSessionOpen = false;

    private Boolean shareOpen = false;

    private Boolean genLocal = false;

    private Integer sessionInvalidateTime = 30 * 60;  //session 失效时间（默认为30分钟 单位：秒）

    private Integer sessionValidationInterval = 15 * 60;  //session 验证失效时间（默认为15分钟 单位：秒）

    public String getFileUploadPath(HttpServletRequest request) {
        log.info("fileUploadPath: {}", fileUploadPath);

        //如果没有写文件上传路径,保存到临时目录
        if (ToolUtil.isEmpty(fileUploadPath)) {
            log.info("null_path:{}", request.getServletContext().getRealPath(""));
            log.info("UPLOAD_ABSOLUTE_PATH:{}", request.getServletContext().getRealPath(UPLOAD_ABSOLUTE_PATH));
            String realPath = request.getServletContext().getRealPath(UPLOAD_ABSOLUTE_PATH);
            //判断有没有结尾符,没有得加上
            if (!realPath.endsWith(File.separator)) {
                realPath = realPath + File.separator;
            }
            return realPath;
        } else {
            //判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            //判断目录存不存在,不存在得加上
            if (haveCreatePath == false) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Boolean getKaptchaOpen() {
        return kaptchaOpen;
    }

    public void setKaptchaOpen(Boolean kaptchaOpen) {
        this.kaptchaOpen = kaptchaOpen;
    }

    public Boolean getSwaggerOpen() {
        return swaggerOpen;
    }

    public void setSwaggerOpen(Boolean swaggerOpen) {
        this.swaggerOpen = swaggerOpen;
    }

    public Boolean getGenLocal() {
        return genLocal;
    }

    public void setGenLocal(Boolean genLocal) {
        this.genLocal = genLocal;
    }

    public Boolean getSpringSessionOpen() {
        return springSessionOpen;
    }

    public void setSpringSessionOpen(Boolean springSessionOpen) {
        this.springSessionOpen = springSessionOpen;
    }

    public Integer getSessionInvalidateTime() {
        return sessionInvalidateTime;
    }

    public void setSessionInvalidateTime(Integer sessionInvalidateTime) {
        this.sessionInvalidateTime = sessionInvalidateTime;
    }

    public Integer getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(Integer sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public Boolean getShareOpen() {
        return shareOpen;
    }

    public void setShareOpen(Boolean shareOpen) {
        this.shareOpen = shareOpen;
    }
}
