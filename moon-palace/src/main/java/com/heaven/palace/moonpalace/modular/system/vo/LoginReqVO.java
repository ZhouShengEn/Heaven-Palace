package com.heaven.palace.moonpalace.modular.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "登录请求-入参")
public class LoginReqVO implements Serializable {
    private static final long serialVersionUID = 5172491130220141086L;

    @ApiModelProperty(notes = "用户名", required = true)
    private String userName;

    @ApiModelProperty(notes = "密码", required = true)
    private String password;

    @ApiModelProperty(notes = "是否记住登录", required = true)
    private Boolean remember;

    @ApiModelProperty(notes = "验证码")
    private String kaptcha;
}
