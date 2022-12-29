package com.heaven.palace.purplecloudpalace.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通知dao
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public interface NoticeDao {

    List<Map<String, Object>> list(@Param("condition") String condition);
}
