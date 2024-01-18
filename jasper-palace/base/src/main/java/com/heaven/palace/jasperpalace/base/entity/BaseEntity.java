package com.heaven.palace.jasperpalace.base.entity;


import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
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
    private Long id;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;



    public static <T extends BaseEntity> T assembleBaseInfo(T baseEntity) {
        Date date = Date.from(ZonedDateTime.now().toInstant());
        Long userId = CurrentBaseContext.getUserId();
        baseEntity.setCreateBy(userId);
        baseEntity.setCreateTime(date);
        baseEntity.setUpdateBy(userId);
        baseEntity.setUpdateTime(date);
        return baseEntity;
    }

    public static <T extends BaseEntity> T assembleBaseInfo(T baseEntity, Date nowDate) {
        Long userId = CurrentBaseContext.getUserId();
        baseEntity.setCreateBy(userId);
        baseEntity.setCreateTime(nowDate);
        baseEntity.setUpdateBy(userId);
        baseEntity.setUpdateTime(nowDate);
        return baseEntity;
    }

}
