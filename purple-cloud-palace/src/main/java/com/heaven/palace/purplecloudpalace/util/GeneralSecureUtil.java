package com.heaven.palace.purplecloudpalace.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * @Author: zhoushengen
 * @Description: 通用加密工具
 * @DateTime: 2024/1/24 15:39
 **/
public class GeneralSecureUtil {

    /**
     * aes加密
     * @param key
     * @param content
     * @return
     */
    public static String aesEncrypt(String key, String content) {
        AES aes = SecureUtil.aes(key.getBytes());
        return aes.encryptBase64(content);
    }

    /**
     * aes解密
     * @param key
     * @param content
     * @return
     */
    public static String aesDecrypt(String key, String content) {
        AES aes = SecureUtil.aes(key.getBytes());
        return aes.decryptStr(content);
    }
}
