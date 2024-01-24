package com.heaven.palace.jasperpalace.base.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :zhoushengen
 * @date : 2023/2/2
 */
@ApiModel(description = "基本响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    protected static final int SUCCESS_RESPONSE_CODE = 200;

    @ApiModelProperty(required = true, notes = "响应状态码：200-成功")
    private int statusCode = SUCCESS_RESPONSE_CODE;
    @ApiModelProperty(required = true, notes = "响应消息")
    private String message;
}
