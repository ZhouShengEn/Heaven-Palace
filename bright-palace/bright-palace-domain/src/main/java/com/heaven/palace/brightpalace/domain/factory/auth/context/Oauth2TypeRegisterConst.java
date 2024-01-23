package com.heaven.palace.brightpalace.domain.factory.auth.context;

/**
 * @Author: zhoushengen
 * @Description: 统一认证认证类型注册枚举类
 * @DateTime: 2024/1/23 17:11
 **/
public interface Oauth2TypeRegisterConst {

    /**
     * 统一认证注册名后缀
     */
    String OAUTH2_REGISTER_SUFFIX = "oauth2";


    enum ResponseTypeEnum {

        /**
         * 授权码模式
         */
        CODE("code", "code" + OAUTH2_REGISTER_SUFFIX)
        ;


        private final String type;

        private final String beanName;

        ResponseTypeEnum(String type, String beanName) {
            this.type = type;
            this.beanName = beanName;
        }

        public String getType() {
            return type;
        }

        public String getBeanName() {
            return beanName;
        }
    }
}
