package com.heaven.palace.jasperpalace.base.exception;

import lombok.Getter;

/**
 * 错误码规范：系统编码（两位） + 业务场景编码（三位） + 错误类型编码（三位）
 * 示例：10001001
 *
 * @author ZhouShengEn
 * @Description 所有业务异常的枚举
 * @date 2022年8月25日
 */
@Getter
public enum CommonExceptionEnum implements BaseResult {

    /**
     * 错误的请求
     */
    REQUEST_NULL(400, "请求有错误"),
    SERVER_ERROR(500, "服务器内部异常！请联系客服人员。"),

    /**
     * 认证错误
     */
    TOKEN_EMPTY_ERROR(10001001, "Token empty!"),
    TOKEN_EXPIRE_ERROR(10001002, "Token expired!"),
    PASSWORD_ENCODE_ERROR(10001003, "密码异常，请联系相关人员!"),


    ;


    CommonExceptionEnum(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    CommonExceptionEnum(int code, String message, String urlPath) {
        this.statusCode = code;
        this.message = message;
        this.urlPath = urlPath;
    }

    /**
     * 错误码
     */
    private final int statusCode;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 业务异常调整页面
     */
    private String urlPath;

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
