package com.heaven.palace.purplecloudpalace.modular.system.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heaven.palace.purplecloudpalace.common.persistence.model.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 日志记录dao
 *
 * @author ZhouShengEn
 * @Date 2017/4/16 23:44
 */
public interface LogDao {

    /**
     * 获取操作日志
     *
     * @author ZhouShengEn
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getOperationLogs(@Param("page") Page<OperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 获取登录日志
     *
     * @author ZhouShengEn
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getLoginLogs(@Param("page") Page<OperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
