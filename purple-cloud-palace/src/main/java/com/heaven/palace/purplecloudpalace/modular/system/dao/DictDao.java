package com.heaven.palace.purplecloudpalace.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import com.heaven.palace.purplecloudpalace.common.persistence.model.Dict;

/**
 * 字典的dao
 *
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public interface DictDao {

    /**
     * 根据编码获取词典列表
     *
     * @date 2022年8月25日
     */
    List<Dict> selectByCode(@Param("code") String code);

    /**
     * 查询字典列表
     *
     * @author ZhouShengEn
     * @Date 2017/4/26 13:04
     */
    List<Map<String, Object>> list(@Param("condition") String conditiion);
}
