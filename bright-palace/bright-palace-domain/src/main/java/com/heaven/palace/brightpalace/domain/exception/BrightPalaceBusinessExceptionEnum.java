package com.heaven.palace.brightpalace.domain.exception;

/**
 * 错误码规范：系统编码（两位） + 业务场景编码（三位） + 错误类型编码（三位）
 * 示例：10001001
 *
 * @author ZhouShengEn
 * @Description 所有业务异常的枚举
 * @date 2022年8月25日
 */
public enum BrightPalaceBusinessExceptionEnum {

    /**
     * 错误的请求
     */
    REQUEST_NULL(400, "请求有错误"),
    SERVER_ERROR(500, "服务器异常"),

    /**
     * 注册错误
     */
    REGISTER_USERNAME_NULL(11001001, "用户名不能为空!"),
    REGISTER_USERNAME_VALID_ERROR(11001002, "用户名必须符合字符长度为6~20，可包含数字、中文、字母、连字符(-)、下划线(_)"),


    ;


    BrightPalaceBusinessExceptionEnum(int code, String message) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
    }

    BrightPalaceBusinessExceptionEnum(int code, String message, String urlPath) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
        this.urlPath = urlPath;
    }

    private int friendlyCode;

    private String friendlyMsg;

    private String urlPath;

    public int getCode() {
        return friendlyCode;
    }

    public void setCode(int code) {
        this.friendlyCode = code;
    }

    public String getMessage() {
        return friendlyMsg;
    }

    public void setMessage(String message) {
        this.friendlyMsg = message;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

}
