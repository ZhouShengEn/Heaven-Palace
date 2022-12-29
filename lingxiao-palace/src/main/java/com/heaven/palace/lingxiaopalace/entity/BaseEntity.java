package com.heaven.palace.lingxiaopalace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :zhoushengen
 * @date : 2022/12/29
 * 基础实体类
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 4201926857868984022L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

}
