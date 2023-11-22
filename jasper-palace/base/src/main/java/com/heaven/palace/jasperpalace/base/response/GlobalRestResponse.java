package com.heaven.palace.jasperpalace.base.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
    public T getData() {
        return data;
    }

    public GlobalRestResponse setData(T data) {
        this.data = data;
        return this;
    }


}
