package com.heaven.palace.jasperpalace.base.response.auth;

import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import com.heaven.palace.jasperpalace.base.response.BaseResponse;

/**
 * @Author: zhoushengen
 * @Description: token不存在或过期
 * @DateTime: 2024/1/9 17:11
 **/
public class TokenExpireResponse extends BaseResponse {

    public TokenExpireResponse() {
        super(CommonExceptionEnum.AUTH_TOKEN_EXPIRE_ERROR.getStatusCode()
                , CommonExceptionEnum.AUTH_TOKEN_EXPIRE_ERROR.getMessage());
    }
}
