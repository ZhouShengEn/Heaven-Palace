package com.heaven.palace.brightpalace.api.api.oauth2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 统一认证获取token-出参
 * @DateTime: 2024/1/24 16:50
 **/
@ApiModel(value = "统一认证获取token-出参")
@Data
@Accessors(chain = true)
public class Oauth2QueryTokenResVO implements Serializable {
    private static final long serialVersionUID = 5800232005692155703L;

    @ApiModelProperty(notes = "访问令牌")
    private String accessToken;

    @ApiModelProperty(notes = "剩余过期时间,单位：毫秒")
    private Long expireTime;

    @ApiModelProperty(notes = "作用域：all-全部")
    private String scope = "all";

}
