package com.heaven.palace.jasperpalace.base.exception.auth;

import com.heaven.palace.jasperpalace.base.exception.BusinessException;

/**
 * @Author: zhoushengen
 * @Description: 认证异常
 * @DateTime: 2024/1/9 17:11
 **/
public class AuthenticationException extends BusinessException {

    private static final long serialVersionUID = 1117836506049981575L;

    public AuthenticationException(int statusCode, String message) {
        super(statusCode, message);
    }
}
