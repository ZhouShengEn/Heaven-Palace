package com.heaven.palace.brightpalace.api.api.oauth2.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 统一认证token请求
 * @DateTime: 2024/2/4 19:29
 **/
@Data
@Accessors(chain = true)
public class Oauth2TokenReqVO implements Serializable {
    private static final long serialVersionUID = -5601912973595297417L;

    @ApiModelProperty(notes = "客户端id")
    @NotBlank(message = "客户端不能为空！")
    private String clientId;

    @ApiModelProperty(notes = "授权类型")
    @NotBlank(message = "授权类型不能为空！")
    private String responseType;
}
