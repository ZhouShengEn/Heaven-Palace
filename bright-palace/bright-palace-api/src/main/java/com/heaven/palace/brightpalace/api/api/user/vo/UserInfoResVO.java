package com.heaven.palace.brightpalace.api.api.user.vo;

import com.heaven.palace.brightpalace.api.enums.user.BaseUserConst.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 用户信息
 * @DateTime: 2024/2/5 16:06
 **/
@Data
@ApiModel(value = "用户信息")
public class UserInfoResVO implements Serializable {
    private static final long serialVersionUID = -4011861479491499884L;

    @ApiModelProperty(notes = "用户id")
    private Long userId;
    /**
     * 用户名称
     *
     */
    @ApiModelProperty(notes = "用户名称")
    private String username;
    /**
     * 用户密码
     *
     */
    @ApiModelProperty(notes = "用户密码")
    private String password;
    /**
     * 移动手机号
     *
     */
    @ApiModelProperty(notes = "移动手机号")
    private String mobilePhone;
    /**
     * 邮箱
     *
     */
    @ApiModelProperty(notes = "邮箱")
    private String email;

    /**
     * @see Status#getCode()
     *
     */
    @ApiModelProperty(notes = "状态")
    private Integer status;
}
