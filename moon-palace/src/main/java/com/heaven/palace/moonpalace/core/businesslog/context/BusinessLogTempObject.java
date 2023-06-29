package com.heaven.palace.moonpalace.core.businesslog.context;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/15 15:12
 */
@Data
@Builder
public class BusinessLogTempObject implements Serializable {
    private static final long serialVersionUID = -4672645026702716738L;

    /**
     * 操作对象id
     */
    private Integer targetId;

    /**
     * 操作对象名称
     */
    private String targetName;

    /**
     * 修改前对象
     */
    private Object oldObject;

    /**
     * 修改后对象
     */
    private Object newObj;
}
