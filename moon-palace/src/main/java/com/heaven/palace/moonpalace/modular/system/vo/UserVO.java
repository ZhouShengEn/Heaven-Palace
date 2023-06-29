package com.heaven.palace.moonpalace.modular.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/3/6 10:09
 */
@Data
@ApiModel(value = "用户信息")
public class UserVO implements Serializable {
    private static final long serialVersionUID = 5695014768001594956L;

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 头像
     */
    @ApiModelProperty(notes = "头像")
    private String avatar;
    /**
     * 账号
     */
    @ApiModelProperty(notes = "账号")
    private String account;
    /**
     * 名字
     */
    @ApiModelProperty(notes = "名字")
    private String name;
    /**
     * 生日
     */
    @ApiModelProperty(notes = "生日")
    private Date birthday;
    /**
     * 性别（1：男 2：女）
     */
    @ApiModelProperty(notes = "性别（1：男 2：女）")
    private Integer sex;
    /**
     * 电子邮件
     */
    @ApiModelProperty(notes = "电子邮件")
    private String email;
    /**
     * 电话
     */
    @ApiModelProperty(notes = "电话")
    private String phone;
    /**
     * 角色id
     */
    @ApiModelProperty(notes = "角色id")
    private String roleId;
    /**
     * 部门id
     */
    @ApiModelProperty(notes = "部门id")
    private Integer deptId;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    @ApiModelProperty(notes = "状态(1：启用  2：冻结  3：删除）")
    private Integer status;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    @ApiModelProperty(notes = "部门全称")
    private String deptFullName;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    @ApiModelProperty(notes = "角色名称")
    private String roleName;


}
