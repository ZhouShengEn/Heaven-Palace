package com.heaven.palace.brightpalace.api.enums.user;

import lombok.Getter;

/**
 * @Author: zhoushengen
 * @Description: 基础用户枚举
 * @DateTime: 2024/1/18 15:54
 **/
public interface BaseUserConst {

    @Getter
    enum Status {
        ACTIVE(0, "使用中"),
        ;


        private final Integer code;
        private final String desc;

        Status(Integer code, String desc) {
            this.desc = desc;
            this.code = code;
        }
    }

}
