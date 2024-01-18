package com.heaven.palace.brightpalace.domain.exception;

import com.heaven.palace.jasperpalace.base.exception.BaseResult;
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
public enum BusinessExceptionEnum implements BaseResult {

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
    REGISTER_PASSWORD_NULL(11001003, "密码不能为空!"),
    REGISTER_PASSWORD_VALID_ERROR(11001004, "密码需要符合限制为8-15个字符，同时包含数字、英文、特殊符"),
    REGISTER_MOBILE_PHONE_NULL(11001005, "手机号不能为空！"),
    REGISTER_MOBILE_PHONE_VALID_ERROR(11001006, "手机号不符合规范！"),
    REGISTER_EMAIL_NULL(11001007, "邮箱不能为空！"),
    REGISTER_EMAIL_VALID_ERROR(11001008, "邮箱不符合规范！"),
    REGISTER_ROLE_OR_ORG_QUERY_NULL_ERROR(11001009, "未查询到相关信息，请联系工作人员！"),
    REGISTER_USER_NAME_REPEAT_ERROR(11001010, "名称已使用！"),
    REGISTER_USER_PHONE_REPEAT_ERROR(11001011, "手机号已使用！"),



    ;


    BusinessExceptionEnum(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    BusinessExceptionEnum(int code, String message, String urlPath) {
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
