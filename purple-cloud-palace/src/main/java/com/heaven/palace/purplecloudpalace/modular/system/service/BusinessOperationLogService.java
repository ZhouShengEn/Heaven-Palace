package com.heaven.palace.purplecloudpalace.modular.system.service;

import com.heaven.palace.purplecloudpalace.common.constant.BusinessOperationLogConstant;
import com.heaven.palace.purplecloudpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.purplecloudpalace.common.persistence.vo.BusinessOperationLogVo;

import java.util.List;

/**
 * @author :zhoushengen
 * @date : 2022/9/6
 */
public interface BusinessOperationLogService {
    
    List<BusinessOperationLogVo> queryListByType(String businessDict, String descField, Integer env);
    
    void insert(BusinessOperationLog businessOperationLog, Integer env);
}
