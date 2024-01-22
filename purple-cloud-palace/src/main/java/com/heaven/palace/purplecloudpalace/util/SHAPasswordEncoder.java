package com.heaven.palace.purplecloudpalace.util;

import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @Author: zhoushengen
 * @Description: sha-256 密码加密
 * @DateTime: 2024/1/17 17:35
 **/
@Slf4j
public class SHAPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte digest[] = md.digest(charSequence.toString().getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(digest));
        } catch (Exception e) {
            log.error("encode password failed.", e);
            throw new BusinessException(CommonExceptionEnum.AUTH_PASSWORD_ENCODE_ERROR);
        }
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return this.encode(charSequence.toString()).equals(s);
    }
}
