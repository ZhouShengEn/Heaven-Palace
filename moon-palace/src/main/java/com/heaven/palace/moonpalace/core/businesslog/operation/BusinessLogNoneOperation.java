package com.heaven.palace.moonpalace.core.businesslog.operation;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import org.springframework.stereotype.Component;

@Component
public class BusinessLogNoneOperation extends BusinessLogOperationFactory {

    @Override
    public void writeBusinessLog() {
    }

    @Override
    public void executeSql(BusinessOperationLog businessOperationLog) {
    }
}
