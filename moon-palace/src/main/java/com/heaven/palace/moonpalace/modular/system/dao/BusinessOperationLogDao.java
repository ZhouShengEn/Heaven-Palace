package com.heaven.palace.moonpalace.modular.system.dao;


import com.heaven.palace.moonpalace.common.IMapper;
import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageReqVO;
import com.heaven.palace.moonpalace.modular.system.vo.BusinessLogPageResVO;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * BusinessOperationLogDao
 *
 * @author ZhouShengEn
 * @Date 2022-09-06 15:42
 */
@Repository
public interface BusinessOperationLogDao extends IMapper<BusinessOperationLog> {

    List<BusinessLogPageResVO> page(BusinessLogPageReqVO logPageReqVO);

}
