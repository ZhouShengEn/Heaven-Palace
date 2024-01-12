package com.heaven.palace.purplecloudpalace.util;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: zhoushengen
 * @Description: Blowfish加密工具类
 * @DateTime: 2024/1/9 16:05
 **/
public class BlowfishUtils {
    /**
     * 算法名称
     */
    private static final String ALGORITHM_BLOWFISH = "BLOWFISH";

    /**
     * 密钥生成器
     *
     * @param keyLength 生成密钥长度
     * @return 生成的密钥
     * @throws NoSuchAlgorithmException 不支持的算法异常
     */
    public static Key keyGenerator(int keyLength) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_BLOWFISH);
        keyGenerator.init(keyLength);
        return keyGenerator.generateKey();
    }

    /**
     * Blowfish 加密
     *
     * @param key     密钥
     * @param content 待加密数据
     * @param mode    工作模式
     * @param padding 填充模式
     * @return 加密后的密文
     */
    public static byte[] encrypt(Key key, byte[] content, Mode mode, Padding padding) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_BLOWFISH + "/" + mode.value + "/" + padding.value);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (InvalidKeyException e) {
            throw new UnsupportedOperationException("Invalid Key");
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("No such algorithm");
        } catch (NoSuchPaddingException e) {
            throw new UnsupportedOperationException("No such padding");
        } catch (BadPaddingException e) {
            throw new UnsupportedOperationException("Bad padding");
        } catch (IllegalBlockSizeException e) {
            throw new UnsupportedOperationException("Illegal block size");
        }
    }

    /**
     * Blowfish 解密
     *
     * @param key     密钥
     * @param content 待解密的密文
     * @param mode    工作模式
     * @param padding 填充模式
     * @return 解密后的原文
     */
    public static byte[] decrypt(Key key, byte[] content, Mode mode, Padding padding) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_BLOWFISH + "/" + mode.value + "/" + padding.value);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (InvalidKeyException e) {
            throw new UnsupportedOperationException("Invalid Key");
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException("No such algorithm");
        } catch (NoSuchPaddingException e) {
            throw new UnsupportedOperationException("No such padding");
        } catch (BadPaddingException e) {
            throw new UnsupportedOperationException("Bad padding");
        } catch (IllegalBlockSizeException e) {
            throw new UnsupportedOperationException("Illegal block size");
        }
    }

    public static enum Mode {

        ECB("ECB");

        private String value;

        Mode(String value) {
            this.value = value;
        }
    }

    public static enum Padding {

        NO_PADDING("NoPadding"),
        PKCS5_PADDING("PKCS5Padding");

        private String value;

        Padding(String value) {
            this.value = value;
        }
    }
}
