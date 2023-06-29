package com.heaven.palace.moonpalace.core.businesslog.operation;

import com.alibaba.fastjson.JSON;
import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.core.businesslog.BusinessLogContextHolder;
import com.heaven.palace.moonpalace.core.businesslog.context.BusinessLogTempObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/15 14:37
 */
@Component
@Slf4j
public class BusinessLogDeleteOperation extends BusinessLogOperationFactory {

    @Override
    public void writeBusinessLog() {
        BusinessLogTempObject businessLogTempObject = BUSINESS_LOG_TEMP_OBJECT.get();
        if (null != businessLogTempObject) {
            BusinessOperationLog businessOperationLog = BusinessLogContextHolder.get();
            super.execute(() -> {
                String name = StringUtils.isNotEmpty(businessLogTempObject.getTargetName())
                    ? "'" + businessLogTempObject.getTargetName() + "', " : "";
                businessOperationLog.setContent(name + "id=" + businessLogTempObject.getTargetId() + "', 数据："
                    + JSON.toJSONString(businessLogTempObject.getOldObject()) + ";");
                return businessOperationLog;
            });
        } else {
            log.error("delete BusinessLog businessLogTempObject is null!");
        }
    }

}
