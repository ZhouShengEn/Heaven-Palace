package com.heaven.palace.moonpalace.modular.code.service;


/**
 * 从各种类型的数据转换成统一的数据
 *
 * @author ZhouShengEn on 2017/10/25.
 */
public interface ITableConvertServer {
    /**
     * 从数据库导入到配置中
     * @param json      详细信息
     * @param userId    当前用户
     */
    void importBean(String json, int userId);
}
