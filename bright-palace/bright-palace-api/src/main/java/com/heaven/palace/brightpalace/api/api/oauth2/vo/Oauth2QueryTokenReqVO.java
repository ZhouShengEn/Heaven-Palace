package com.heaven.palace.brightpalace.api.api.oauth2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: zhoushengen
 * @Description: 统一认证授权码模式获取token-入参
 * @DateTime: 2024/1/24 17:07
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "统一认证获取token-入参")
@Data
@Accessors(chain = true)
public class Oauth2QueryTokenReqVO extends Oauth2TokenReqVO {
    private static final long serialVersionUID = 1666429436058255537L;

    @ApiModelProperty(notes = "加密授权码")
    private String encryptCode;

}
