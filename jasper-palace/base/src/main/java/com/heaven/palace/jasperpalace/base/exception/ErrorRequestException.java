package com.heaven.palace.jasperpalace.base.exception;

/**
 * @Author: zhoushengen
 * @Description: 404错误请求异常
 * @DateTime: 2024/1/9 17:11
 **/
public class ErrorRequestException extends BusinessException {
    private static final long serialVersionUID = 1105285550768599239L;

    public ErrorRequestException(int statusCode, String message) {
        super(statusCode, message);
    }

    public ErrorRequestException(BaseResult baseResult) {
        super(baseResult);
    }
}
