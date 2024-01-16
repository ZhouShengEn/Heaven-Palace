package com.heaven.palace.jasperpalace.base.ddd;

/**
 * @Author: zhoushengen
 * @Description: 聚合根 标识领域的组织者
 * @DateTime: 2024/1/16 12:47
 **/
public interface AggregateRoot<T extends Identifier> extends Entity<T>{
}
