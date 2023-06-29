package com.heaven.palace.moonpalace.component.p6spy;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.core.businesslog.BusinessLogContextHolder;
import com.p6spy.engine.spy.appender.StdoutLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

@Slf4j
public class BusinessLogStdoutLogger extends StdoutLogger {

    private final static String SQL_PREFIX = "Execute SQL：";

    private final static String SQL_IGNORE_SELECT = "select";

    @Override
    public void logText(String text) {
        BusinessOperationLog businessOperationLog = BusinessLogContextHolder.get();
        if (null != businessOperationLog && ObjectUtils.allNotNull(businessOperationLog.getDbId(), businessOperationLog.getType())) {
            String sql = text.split(SQL_PREFIX)[1].trim();
            // druid 测试连接的sql排除,查询语句排除
            if (!sql.toLowerCase().startsWith(SQL_IGNORE_SELECT)) {
                businessOperationLog.setSqlText(sql);
                BusinessLogContextHolder.set(businessOperationLog);
            }
        }

    }

}
