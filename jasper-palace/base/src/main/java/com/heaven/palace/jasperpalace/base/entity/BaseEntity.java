package com.heaven.palace.jasperpalace.base.entity;


import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
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
    @Id(keyType = KeyType.Auto)
    private long id;

    /**
     * 创建人
     */
    private long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private long updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    public static BaseEntity init(BaseEntity entity) {
        long userId = CurrentBaseContext.getUserId();
        Date nowDate = new Date();
        entity.setCreateBy(userId);
        entity.setCreateTime(nowDate);
        entity.setUpdateBy(userId);
        entity.setUpdateTime(nowDate);
        return entity;
    }

    public static BaseEntity init(BaseEntity entity, Date nowDate) {
        long userId = CurrentBaseContext.getUserId();
        entity.setCreateBy(userId);
        entity.setCreateTime(nowDate);
        entity.setUpdateBy(userId);
        entity.setUpdateTime(nowDate);
        return entity;
    }

}
