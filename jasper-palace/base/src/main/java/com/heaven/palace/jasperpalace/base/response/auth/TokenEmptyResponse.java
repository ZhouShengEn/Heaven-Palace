package com.heaven.palace.jasperpalace.base.response.auth;

import com.heaven.palace.jasperpalace.base.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.response.BaseResponse;

/**
 * @Author: zhoushengen
 * @Description: token空返回
 * @DateTime: 2024/1/9 17:11
 **/
public class TokenEmptyResponse extends BaseResponse {

    public TokenEmptyResponse() {
        super(BusinessExceptionEnum.TOKEN_EMPTY_ERROR.getCode(), BusinessExceptionEnum.TOKEN_EMPTY_ERROR.getMessage());
    }
}
