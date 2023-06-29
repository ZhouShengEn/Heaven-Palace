package com.heaven.palace.moonpalace.modular.code.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by ZhouShengEn on 2017/9/14.
 */
@ApiModel(description = "抽象实体类")
public abstract class CodeBaseModel<T> extends Model {
    /**
     * 创建人
     */
    @TableField(value = "CRT_USER_ID")
    @ApiModelProperty(notes = "创建人id")
    private Integer crtUserId;

    @TableField(exist = false)
    @ApiModelProperty(notes = "创建人名称")
    private String crtUserName;

    /**
     * 创建时间
     */
    @TableField(value = "CRT_TIME")
    @ApiModelProperty(notes = "创建时间")
    private Date crtTime;

    /**
     * 修改人
     */
    @TableField(value = "MDF_USER_ID")
    @ApiModelProperty(notes = "修改人id")
    private Integer mdfUserId;

    @TableField(exist = false)
    @ApiModelProperty(notes = "修改人名称")
    private String mdfUserName;

    /**
     * 修改时间
     */
    @TableField(value = "MDF_TIME")
    @ApiModelProperty(notes = "修改时间")
    private Date mdfTime;

    public Integer getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(Integer crtUserId) {
        this.crtUserId = crtUserId;
    }

    public String getCrtUserName() {
        return crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getMdfUserId() {
        return mdfUserId;
    }

    public void setMdfUserId(Integer mdfUserId) {
        this.mdfUserId = mdfUserId;
    }

    public String getMdfUserName() {
        return mdfUserName;
    }

    public void setMdfUserName(String mdfUserName) {
        this.mdfUserName = mdfUserName;
    }

    public Date getMdfTime() {
        return mdfTime;
    }

    public void setMdfTime(Date mdfTime) {
        this.mdfTime = mdfTime;
    }
}
