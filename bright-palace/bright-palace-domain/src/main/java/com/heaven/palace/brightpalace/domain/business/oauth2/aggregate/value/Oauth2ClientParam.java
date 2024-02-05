package com.heaven.palace.brightpalace.domain.business.oauth2.aggregate.value;

import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.ddd.Value.ValidValueObject;
import com.heaven.palace.jasperpalace.base.util.RandomAESEncryptUtils;

/**
 * @Author: zhoushengen
 * @Description: 值对象-统一认证客户端参数（目前包含授权码和refreshToken）
 * @DateTime: 2024/1/24 17:24
 **/
public class Oauth2ClientParam extends ValidValueObject<String> {

    private static final long serialVersionUID = -4890289015698199945L;

    public Oauth2ClientParam(String encryptCode, String clientSecret) {
        super(BusinessExceptionEnum.AUTH_OAUTH2_CLIENT_PARAM_NULL_ERROR
            , BusinessExceptionEnum.AUTH_OAUTH2_CLIENT_DB_SECRET_NULL_ERROR
            , BusinessExceptionEnum.AUTH_OAUTH2_CLIENT_SECRET_DECRYPT_ERROR
            , encryptCode, () -> RandomAESEncryptUtils.decryptForString(encryptCode, clientSecret), clientSecret);
    }


    @Override
    public Boolean isValid(String encryptCode, Object... validArgs) {
        return null != validArgs[0];
    }
}
