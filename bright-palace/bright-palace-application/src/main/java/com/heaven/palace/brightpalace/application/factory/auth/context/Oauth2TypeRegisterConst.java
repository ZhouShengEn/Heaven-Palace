package com.heaven.palace.brightpalace.application.factory.auth.context;

import com.heaven.palace.brightpalace.domain.exception.BusinessExceptionEnum;
import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: zhoushengen
 * @Description: 统一认证认证类型注册枚举类
 * @DateTime: 2024/1/23 17:11
 **/
public interface Oauth2TypeRegisterConst {

    /**
     * 统一认证注册名后缀
     */
    String OAUTH2_REGISTER_SUFFIX = "Oauth2";


    @Getter
    enum ResponseTypeEnum {

        /**
         * 授权码模式
         */
        CODE(CommonConst.Oauth2ResponseType.CODE, CommonConst.Oauth2ResponseType.CODE + OAUTH2_REGISTER_SUFFIX)
        ;

        /**
         * 类型
         */
        private final String type;

        /**
         * 身份标记
         */
        private final String markIdentify;

        ResponseTypeEnum(String type, String markIdentify) {
            this.type = type;
            this.markIdentify = markIdentify;
        }

        public static String markIdentifyByType(String type) {
            ResponseTypeEnum responseType= Arrays.stream(ResponseTypeEnum.values())
                    .filter(responseTypeEnum -> type.equals(responseTypeEnum.type)).findFirst().orElse(null);
            if (null == responseType) {
                throw new BusinessException(BusinessExceptionEnum.AUTH_RESPONSE_TYPE_MATCH_NULL_ERROR);
            }
            return responseType.getMarkIdentify();

        }

    }
}
