package com.heaven.palace.brightpalace.api.enums.permission;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: zhoushengen
 * @Description: 授权属性枚举
 * @DateTime: 2024/1/31 16:09
 **/
public interface AuthorityElementConst {


    /**
     * 资源枚举
     */
    interface Resource {

        @Getter
        enum Type {
            URI(0, "uri"),
            ;
            private final Integer code;
            private final String display;

            Type(Integer code, String display) {
                this.code = code;
                this.display = display;
            }

            public static Type getByCode(Integer code) {
                return Arrays.stream(Type.values()).filter(type -> Objects.equals(type.getCode(), code)).findFirst().orElse(null);
            }

        }

        @Getter
        enum Status {
            // 禁用
            DISABLE(0, "禁用"),
            // 启用
            ENABLE(1, "启用"),
            ;
            private final Integer code;
            private final String display;

            Status(Integer code, String display) {
                this.code = code;
                this.display = display;
            }
        }
    }
}
