package com.heaven.palace.purplecloudpalace.modular.system.service;

/**
 * 字典服务
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public interface IDictService {

    /**
     * 添加字典
     *
     * @author ZhouShengEn
     * @Date 2017/4/27 17:01
     */
    void addDict(String dictName, String dictValues);

    /**
     * 编辑字典
     *
     * @author ZhouShengEn
     * @Date 2017/4/28 11:01
     */
    void editDict(Integer dictId, String dictName, String dicts);

    /**
     * 删除字典
     *
     * @author ZhouShengEn
     * @Date 2017/4/28 11:39
     */
    void delteDict(Integer dictId);

}
