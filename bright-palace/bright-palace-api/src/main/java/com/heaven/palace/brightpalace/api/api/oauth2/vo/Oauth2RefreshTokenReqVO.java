package com.heaven.palace.brightpalace.api.api.oauth2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 统一认证授权码模式获取token-入参
 * @DateTime: 2024/1/24 17:07
 **/
@ApiModel(value = "统一认证刷新token-入参")
@Data
@Accessors(chain = true)
public class Oauth2RefreshTokenReqVO implements Serializable {

    private static final long serialVersionUID = -4972565051857502738L;

    @ApiModelProperty(notes = "客户端id")
    @NotBlank(message = "客户端不能为空！")
    private String clientId;

    @ApiModelProperty(notes = "授权类型")
    @NotBlank(message = "授权类型不能为空！")
    private String responseType;

    @ApiModelProperty(notes = "加密refreshToken")
    private String encryptRefreshToken;

}
