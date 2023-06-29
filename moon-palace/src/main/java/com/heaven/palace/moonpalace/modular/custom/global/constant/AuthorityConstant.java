package com.heaven.palace.moonpalace.modular.custom.global.constant;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/23 10:23
 */
public interface AuthorityConstant {

    enum RESOURCE_TYPE{
        // 接口权限
        BUTTON("button"),

        ;
        private final String type;

        RESOURCE_TYPE(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
