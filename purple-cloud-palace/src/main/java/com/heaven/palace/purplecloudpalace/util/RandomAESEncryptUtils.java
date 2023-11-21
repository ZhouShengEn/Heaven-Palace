package com.heaven.palace.purplecloudpalace.util;

import com.heaven.palace.jasperpalace.base.exception.EncryptException;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * AES对称加密工具类
 */
public class RandomAESEncryptUtils {
    private static final String ALGORITHM = "AES";
    // AES，带向量模式，自动填充
    private static final String ALGORITHM_MODE = "AES/CBC/PKCS5Padding";
    // 随机算法
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";

    public static final String DATE_FORMATE_FULL = "yyyy-MM-dd HH:mm:ss";

    /***
     *
     * @Description 加密
     * @param origin 原文
     * @param key 密钥
     * @return {@link String}
     */
    public static String encryptForString(String origin,String key) throws EncryptException {
        if(StringUtils.isBlank(origin) || StringUtils.isBlank(key)){
            throw new EncryptException(7,"Param is null",null);
        }
        byte[] originBytes = origin.getBytes(StandardCharsets.UTF_8);
        byte[] retBytes = encrypt(originBytes,key);
        return Base64.getEncoder().encodeToString(retBytes);
    }

    /***
     *
     * @Description 加密
     * @param originBytes 原文字节数组
     * @param key 密钥
     * @return {@link String}
     */
    public static byte[] encrypt(byte[] originBytes,String key) throws EncryptException{
        // 加时间戳
        byte[] timeBytes = currentTimeStamp().getBytes(StandardCharsets.UTF_8);
        byte[] originTimeBytes = new byte[originBytes.length + timeBytes.length];
        System.arraycopy(originBytes,0,originTimeBytes,0,originBytes.length);
        System.arraycopy(timeBytes,0,originTimeBytes,originBytes.length,timeBytes.length);
        // 创建密钥生成器
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(1, "NoSuchAlgorithmException", e);
        }
        // 随机数
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance(RANDOM_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(1, "NoSuchAlgorithmException", e);
        }
        random.setSeed(key.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(128, random);
        // 创建密钥
        Key secretKey = keyGenerator.generateKey();
        Key paramKey = keyGenerator.generateKey();
        Cipher cipher = null;
        // 创建并初始化加密器
        try {
            cipher = Cipher.getInstance(ALGORITHM_MODE);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(1, "NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptException(2, "NoSuchPaddingException", e);
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(paramKey.getEncoded()));
        } catch (InvalidKeyException e) {
            throw new EncryptException(3, "InvalidKeyException", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new EncryptException(4, "InvalidAlgorithmParameterException", e);
        }
        // 加密
        byte[] ret = null;
        try {
            ret = cipher.doFinal(originTimeBytes);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptException(5, "IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            throw new EncryptException(6, "BadPaddingException", e);
        }
        return ret;
    }
    
    /***
     * 
     * @Description 
     * @param encryptedString 密文
     * @param key
     * @return {@link String}
     */
    public static String decryptForString(String encryptedString,String key) throws EncryptException{
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
        byte[] retBytes = decrypt(encryptedBytes,key);
        return new String(retBytes,StandardCharsets.UTF_8);
    }

    /***
     *
     * @Description 解密
     * @param encryptedBytes 密文字节数组
     * @param key 密钥
     * @return {@link String}
     */
    public static byte[] decrypt(byte[] encryptedBytes,String key) throws EncryptException{
        // 创建密钥生成器
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(1, "NoSuchAlgorithmException", e);
        }
        // 随机数
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance(RANDOM_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(1, "NoSuchAlgorithmException", e);
        }
        random.setSeed(key.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(128, random);
        // 创建密钥
        Key secretKey = keyGenerator.generateKey();
        Key paramKey = keyGenerator.generateKey();
        Cipher cipher = null;
        // 创建并初始化加密器
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(1, "NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptException(2, "NoSuchPaddingException", e);
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(paramKey.getEncoded()));
        } catch (InvalidKeyException e) {
            throw new EncryptException(3, "InvalidKeyException", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new EncryptException(4, "InvalidAlgorithmParameterException", e);
        }
        // 解密
        byte[] ret = null;
        try {
            ret = cipher.doFinal(encryptedBytes);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptException(5, "IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            throw new EncryptException(6, "BadPaddingException", e);
        }
        // 去掉后缀
        byte[] retSubSuffix = new byte[ret.length - DATE_FORMATE_FULL.getBytes(StandardCharsets.UTF_8).length];
        System.arraycopy(ret, 0, retSubSuffix, 0, retSubSuffix.length);
        return retSubSuffix;
    }



    // 时间字符串
    private static String currentTimeStamp(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMATE_FULL);
        return simpleDateFormat.format(new Date());
    }
}
