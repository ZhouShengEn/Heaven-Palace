package com.heaven.palace.jasperpalace.base.exception;

/***
 *
 * @Description 加解密自定义异常
 * @author zhoushengen
 */
public class EncryptException extends Exception {
    private static final long serialVersionUID = 5658708798486506578L;
    private final Integer code;
    public EncryptException(int code, String message, Throwable e){
        super(message,e);
        this.code = code;
    }
}
