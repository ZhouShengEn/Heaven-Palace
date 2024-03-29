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
     * Value Object Valid Error
     */
    VALID_USERNAME_NULL_ERROR(11001001, "用户名不能为空!"),
    VALID_USERNAME_ERROR(11001002, "用户名必须符合字符长度为6~20，可包含数字、中文、字母、连字符(-)、下划线(_)"),
    VALID_PASSWORD_NULL_ERROR(11001003, "密码不能为空!"),
    VALID_PASSWORD_ERROR(11001004, "密码需要符合限制为8-15个字符，同时包含数字、英文、特殊符"),
    VALID_MOBILE_PHONE_NULL_ERROR(11001005, "手机号不能为空！"),
    VALID_MOBILE_PHONE_ERROR(11001006, "手机号不符合规范！"),
    VALID_EMAIL_NULL_ERROR(11001007, "邮箱不能为空！"),
    VALID_EMAIL_ERROR(11001008, "邮箱不符合规范！"),
    VALID_SYSTEM_RESOURCE_TYPE_NULL_ERROR(11001009, "未识别的资源类型！"),

    /**
     * 认证错误
     */
    AUTH_REQUEST_PARAM_NULL_ERROR(11002001, "认证失败，入参异常！"),
    AUTH_REQUEST_CLIENT_NULL_ERROR(11002002, "认证失败，无客户端标识！"),
    AUTH_CLIENT_QUERY_NULL_ERROR(11002003, "认证失败，无效的客户端标识！"),
    AUTH_REDIRECT_LOGIN_URL_ERROR(11002004, "认证失败，重定向统一认证登陆页面失败！"),
    AUTH_REDIRECT_CLIENT_AUTH_URL_ERROR(11002005, "认证失败，重定向至客户端失败！"),
    AUTH_RESPONSE_TYPE_MATCH_NULL_ERROR(11002006, "认证失败，不支持的认证类型！"),
    AUTH_RESPONSE_TYPE_SERVICE_NULL_ERROR(11002007, "认证失败，未查询到可用的认证服务！"),
    AUTH_OAUTH2_CLIENT_PARAM_NULL_ERROR(11002008, "客户端参数不能为空！"),
    AUTH_OAUTH2_CLIENT_DB_SECRET_NULL_ERROR(11002009, "客户端配置异常！"),
    AUTH_OAUTH2_TOKEN_CACHE_NOT_HIT_ERROR(11002010, "客户端未授权！"),
    AUTH_OAUTH2_CLIENT_SECRET_DECRYPT_ERROR(11002011, "客户端核验失败！"),

    /**
     * 登录错误
     */
    LOGIN_USER_QUERY_NULL_ERROR(11003001, "密码输入不正确！"),

    /**
     * 注册错误
     */
    REGISTER_ROLE_OR_ORG_QUERY_NULL_ERROR(11004001, "未查询到相关信息，请联系工作人员！"),
    REGISTER_USER_NAME_REPEAT_ERROR(11004002, "名称已使用！"),
    REGISTER_USER_PHONE_REPEAT_ERROR(11004003, "手机号已使用！"),

    /**
     * 权限相关
     */
    PERMISSION_UNKNOWN_EXCEPTION_ERROR(11005001, "权限校验流程异常！"),

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
