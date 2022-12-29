package com.heaven.palace.purplecloudpalace.common.exception;


import com.heaven.palace.purplecloudpalace.exception.GunsException;

/**
 * @author ZhouShengEn
 * @Description 业务异常的封装
 * @date 2022年8月25日
 */
public class BussinessException extends GunsException {

    public BussinessException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage(), bizExceptionEnum.getUrlPath());
    }
}
