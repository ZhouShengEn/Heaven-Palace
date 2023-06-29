package com.heaven.palace.moonpalace.exception;

/**
 * 错误码规范：业务场景编码（两位） + 错误类型编码（两位）
 * 示例：1001
 *
 * @author ZhouShengEn
 * @Description 所有业务异常的枚举
 * @date 2022年8月25日
 */
public enum BusinessExceptionEnum {

    /**
     * 其他
     */
    WRITE_ERROR(500, "渲染界面错误"),


    /**
     * 错误的请求
     */
    REQUEST_NULL(400, "请求有错误"),
    SERVER_ERROR(500, "服务器异常"),


    /**
     * sql异常
     */
    SQL_ERROR(1001, "sql异常！"),


    /**
     * 业务日志
     */
    BUSINESS_LOG_SYNCHRONIZE_QUERY_ERROR(2001, "该操作不存在或已经同步！"),
    BUSINESS_LOG_SYNCHRONIZE_RUN_ERROR(2002, "同步操作执行失败！失败原因：%s"),
    BUSINESS_LOG_CHILD_SYNCHRONIZE_ERROR(2003, "子日志同步失败！"),


    /**
     * 权限、登录相关
     */
    AUTHORITY_QUERY_NULL_ERROR(3001, "未查询到权限！"),
    CREDENTIALS_EXCEPTION_NULL_ERROR(3002, "用户名密码错误！"),
    PASSWORD_UPDATE_REPEAT_ERROR(3003, "新密码和旧密码不能重复！"),
    OLD_PASSWORD_NOT_MATCH_ERROR(3004, "请检查原密码！"),
    USER_ALREADY_REG(3005, "账号已存在！"),
    NO_PERMITION(3006, "没有权限！"),
    CANT_DELETE_ADMIN(3007, "不能删除管理员账户！"),
    CANT_FREEZE_ADMIN(3008, "不能冻结管理员账户！"),
    CANT_CHANGE_ADMIN(3009, "不能修改管理员账户！"),

    /**
     * 文件上传
     */
    FILE_READING_ERROR(4001, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(4002, "FILE_NOT_FOUND!"),
    FILE_UPLOAD_ERROR(4003, "文件上传失败!"),
    FILE_UPLOAD_NULL(4004, "上传的文件有误，请检查上传文件!"),

    /**
     * 数据源相关
     */
    DB_INFO_ALIAS_REPEAT_ERROR(5001, "数据源别名不能重复!"),
    DB_INFO_ALIAS_NULL_ERROR(5002, "数据源别名不能为空!"),

    /**
     * 字典
     */
    DICT_EXISTED(6001, "字典已存在!"),
    DICT_MUST_BE_NUMBER(6002, "字典条目必须是数字!"),

    /**
     * 代码生成
     */

    SFTP_CONNECTED_ERROR(7001, "SFTP连接异常！"),

    ;


    BusinessExceptionEnum(int code, String message) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
    }

    BusinessExceptionEnum(int code, String message, String urlPath) {
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
