package com.heaven.palace.brightpalace.api.api.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: zhoushengen
 * @Description: 用户注册类
 * @DateTime: 2024/1/15 14:23
 **/
@ApiModel(value = "用户注册类")
public class UserRegisterVO implements Serializable {
    private static final long serialVersionUID = -6393643173758422524L;

    /**
     * 用户名称
     *
     */
    @ApiModelProperty(notes = "用户名称")
    @NotBlank(message = "用户名称不能为空！")
    private String username;
    /**
     * 昵称
     *
     */
    @ApiModelProperty(notes = "昵称")
    private String nickname;
    /**
     * 用户密码
     *
     */
    @ApiModelProperty(notes = "用户密码")
    @NotBlank(message = "用户密码不能为空！")
    private String password;
    /**
     * 用户生日
     *
     */
    @ApiModelProperty(notes = "用户生日")
    private String birthday;
    /**
     * 地址
     *
     */
    @ApiModelProperty(notes = "地址")
    private String address;
    /**
     * 移动手机号
     *
     */
    @ApiModelProperty(notes = "移动手机号")
    private String mobilePhone;
    /**
     * 电话号码
     *
     */
    @ApiModelProperty(notes = "电话号码")
    private String telPhone;
    /**
     * 邮箱
     *
     */
    @ApiModelProperty(notes = "邮箱")
    private String email;
    /**
     * 头像图片地址
     *
     */
    @ApiModelProperty(notes = "头像图片地址")
    private String avatar;
    /**
     * 性别：0-男；1-女
     *
     */
    @ApiModelProperty(notes = "性别：0-男；1-女")
    private Integer sex;

}
