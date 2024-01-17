package com.heaven.palace.jasperpalace.base.exception;

/**
 * @Author: zhoushengen
 * @Description: 基础返回接口类
 * @DateTime: 2024/1/17 10:05
 **/
public interface BaseResult {

    int getStatusCode();

    String getMessage();
}
