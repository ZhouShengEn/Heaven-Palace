package com.heaven.palace.purplecloudpalace.core.util;

import com.heaven.palace.purplecloudpalace.config.properties.GunsProperties;
import com.heaven.palace.purplecloudpalace.util.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     *
     * @author ZhouShengEn
     * @Date 2017/5/23 22:34
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(GunsProperties.class).getKaptchaOpen();
    }
}