package com.heaven.palace.jasperpalace.base.dto;

import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author :zhoushengen
 * @date : 2022/12/29
 */
@Data
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 2089531940431767944L;

    /**
     * 主键id
     */
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

    public static <T extends BaseDTO> T assembleBaseInfo(T baseDTO) {
        Date date = Date.from(ZonedDateTime.now().toInstant());
        long userId = CurrentBaseContext.getUserId();
        baseDTO.setCreateBy(userId);
        baseDTO.setCreateTime(date);
        baseDTO.setUpdateBy(userId);
        baseDTO.setUpdateTime(date);
        return baseDTO;
    }
}
