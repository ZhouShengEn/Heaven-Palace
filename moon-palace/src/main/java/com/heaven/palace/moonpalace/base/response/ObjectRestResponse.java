package com.heaven.palace.moonpalace.base.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "泛型响应对象")
public class ObjectRestResponse<T> extends BaseResponse {
    @ApiModelProperty(notes = "响应数据，泛型")
    T data;

    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public ObjectRestResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    public ObjectRestResponse statusCode(int code) {
        this.setStatusCode(code);
        return this;
    }
    public T getData() {
        return data;
    }

    public ObjectRestResponse setData(T data) {
        this.data = data;
        return this;
    }


}
