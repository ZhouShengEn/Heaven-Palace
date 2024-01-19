package com.heaven.palace.brightpalace.api.api.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 用户登录——入参
 * @DateTime: 2024/1/19 10:21
 **/
@Data
@ApiModel(value = "用户账户密码登录——入参")
public class UserLoginPhoneAndPasswordVO implements Serializable {
    private static final long serialVersionUID = 2903095010012723026L;

    @ApiModelProperty(notes = "用户名")
    @NotBlank(message = "用户名不能为空！")
    private String userName;

    @ApiModelProperty(notes = "账户密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(notes = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobilePhone;

    @ApiModelProperty(notes = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String verCode;



}
