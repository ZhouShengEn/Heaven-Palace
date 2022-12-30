package com.heaven.palace.lingxiaopalace.dto;

import com.heaven.palace.lingxiaopalace.constant.CommonConst;
import com.heaven.palace.lingxiaopalace.context.CurrentBaseContext;
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

    public static <T extends BaseDTO> T assembleBaseInfo(T baseDTO) {
        Date date = Date.from(ZonedDateTime.now().toInstant());
        String userId = CurrentBaseContext.get(CommonConst.KEY_USER_ID).toString();
        baseDTO.setCreateBy(userId);
        baseDTO.setCreateTime(date);
        baseDTO.setUpdateBy(userId);
        baseDTO.setUpdateTime(date);
        return baseDTO;
    }
}
