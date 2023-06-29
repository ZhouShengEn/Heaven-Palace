package com.heaven.palace.moonpalace.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/27 15:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseModel<T extends Model<?>> extends Model<T> {

    private static final long serialVersionUID = -4755901819028385835L;

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;
}
