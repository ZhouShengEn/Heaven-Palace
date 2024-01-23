package com.heaven.palace.jasperpalace.base.response.auth;

import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import com.heaven.palace.jasperpalace.base.response.BaseResponse;

/**
 * @Author: zhoushengen
 * @Description: token空返回
 * @DateTime: 2024/1/9 17:11
 **/
public class TokenEmptyResponse extends BaseResponse {

    public TokenEmptyResponse() {
        super(CommonExceptionEnum.AUTH_TOKEN_EMPTY_ERROR.getStatusCode()
                , CommonExceptionEnum.AUTH_TOKEN_EMPTY_ERROR.getMessage());
    }
}
