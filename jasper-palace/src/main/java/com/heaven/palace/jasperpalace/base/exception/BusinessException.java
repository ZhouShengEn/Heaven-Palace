package com.heaven.palace.jasperpalace.base.exception;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/20 10:31
 */
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -2320033886855622442L;
    //友好提示的code码
    protected int statusCode;

    //友好提示
    protected String message;

    public BusinessException(BusinessExceptionEnum exceptionEnum) {
        this.message = exceptionEnum.getMessage();
        this.statusCode = exceptionEnum.getCode();
    }

    public BusinessException(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
