package com.heaven.palace.jasperpalace.base.exception.auth;

import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.exception.BusinessExceptionEnum;

/**
 * @Author: zhoushengen
 * @Description: 认证异常
 * @DateTime: 2024/1/9 17:11
 **/
public class AuthenticationException extends BusinessException {

    public AuthenticationException(BusinessExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }

    public AuthenticationException(int statusCode, String message) {
        super(statusCode, message);
    }
}
