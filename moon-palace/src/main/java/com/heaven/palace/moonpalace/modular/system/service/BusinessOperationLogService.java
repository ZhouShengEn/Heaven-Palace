package com.heaven.palace.moonpalace.modular.system.service;

import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.base.response.PageResultResponse;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageReqVO;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageResVO;

/**
 * @author :zhoushengen
 * @date : 2022/9/6
 */
public interface BusinessOperationLogService {


    /**
     * 分页查询
     * @param logPageReqVO
     * @return
     */
    PageResultResponse<BusinessLogPageResVO> page(BusinessLogPageReqVO logPageReqVO);

    /**
     * 操作同步
     * @param logId
     * @param targetDbId
     * @return
     */
    ObjectRestResponse<Void> synchronize(Integer logId, Integer targetDbId);

    /**
     * 同步操作删除
     * @param logId
     * @return
     */
    ObjectRestResponse<Void> synchronizeDelete(Integer logId);

}
