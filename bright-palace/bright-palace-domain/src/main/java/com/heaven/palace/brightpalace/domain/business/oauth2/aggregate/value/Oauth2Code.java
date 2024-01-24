package com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.value;

import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.ValidValueObject;
import com.heaven.palace.purplecloudpalace.util.GeneralSecureUtil;

/**
 * @Author: zhoushengen
 * @Description: 值对象-统一认证授权码
 * @DateTime: 2024/1/24 17:24
 **/
public class Oauth2Code extends ValidValueObject<String> {

    public Oauth2Code(String encryptCode, String clientSecret) {
        super(BusinessExceptionEnum.AUTH_OAUTH2_CODE_NULL_ERROR, BusinessExceptionEnum.AUTH_OAUTH2_CLIENT_DB_SECRET_NULL_ERROR
            , encryptCode, () -> GeneralSecureUtil.aesDecrypt(encryptCode, clientSecret));
    }


    @Override
    public Boolean isValid(String encryptCode, Object... validArgs) {
        return null != validArgs[0];
    }
}
