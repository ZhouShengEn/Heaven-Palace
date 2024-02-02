package com.heaven.palace.jasperpalace.base.exception;

import com.heaven.palace.jasperpalace.base.response.BaseResponse;
import lombok.NoArgsConstructor;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/20 10:31
 */
@NoArgsConstructor
public class BusinessException extends RuntimeException implements BaseResult {
    private static final long serialVersionUID = -2320033886855622442L;
    /**
     * 状态码
     */
    protected int statusCode;

    /**
     * 异常描述
     */
    protected String message;

    public BusinessException(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public BusinessException(BaseResult baseResult) {
        this.message = baseResult.getMessage();
        this.statusCode = baseResult.getStatusCode();
    }

    public BusinessException(BaseResponse baseResponse) {
        this.message = baseResponse.getMessage();
        this.statusCode = baseResponse.getStatusCode();
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
