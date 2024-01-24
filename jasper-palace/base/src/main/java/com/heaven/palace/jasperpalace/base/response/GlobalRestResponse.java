package com.heaven.palace.jasperpalace.base.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author 10733
 * @date 2024/1/14 22:38
 * @description: 全局通用泛型响应对象
 */
@Getter
@ApiModel(description = "全局通用泛型响应对象")
public class GlobalRestResponse<T> extends BaseResponse {
    @ApiModelProperty(notes = "响应数据，泛型")
    T data;


    public GlobalRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public GlobalRestResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    public GlobalRestResponse statusCode(int code) {
        this.setStatusCode(code);
        return this;
    }

    public GlobalRestResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static Boolean success(GlobalRestResponse response) {
        return SUCCESS_RESPONSE_CODE == response.getStatusCode();
    }


}
