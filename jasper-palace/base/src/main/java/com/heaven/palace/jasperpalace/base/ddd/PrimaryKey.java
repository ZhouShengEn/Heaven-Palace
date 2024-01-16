package com.heaven.palace.jasperpalace.base.ddd;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: zhoushengen
 * @Description: 数据库主键可作为唯一标识
 * @DateTime: 2024/1/16 12:52
 **/
@Data
@AllArgsConstructor
public class PrimaryKey implements Identifier {
    private static final long serialVersionUID = -5659749638089325953L;

    /**
     * 数据库主键
     */
    Long id;


}
