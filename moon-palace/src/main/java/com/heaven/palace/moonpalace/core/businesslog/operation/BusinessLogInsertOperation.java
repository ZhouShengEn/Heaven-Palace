package com.heaven.palace.moonpalace.core.businesslog.operation;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.core.businesslog.BusinessLogContextHolder;
import com.heaven.palace.moonpalace.core.businesslog.context.BusinessLogTempObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class BusinessLogInsertOperation extends BusinessLogOperationFactory {

    private final String INSERT_ID = "id";

    private final String CONTENT_INSERT_ID_PATTERN = "id=(?<id>\\d+)";

    private final String SQL_TEXT_INSERT_ID_PATTERN = "[( ,]id[,]";

    @Override
    public void writeBusinessLog() {
        BusinessLogTempObject businessLogTempObject = BUSINESS_LOG_TEMP_OBJECT.get();
        if (null != businessLogTempObject) {
            BusinessOperationLog businessOperationLog = BusinessLogContextHolder.get();
            super.execute(() -> {
                String name = StringUtils.isNotEmpty(businessLogTempObject.getTargetName())
                    ? "'" + businessLogTempObject.getTargetName() + "', " : "";
                businessOperationLog.setContent(name + INSERT_ID + "=" + businessLogTempObject.getTargetId() + ";");
                return businessOperationLog;
            });
        } else {
            log.error("insert BusinessLog businessLogTempObject is null!");
        }

    }

    @Override
    public void executeSql(BusinessOperationLog businessOperationLog) throws SQLException, ParseException {

        // 判断sql语句中是否指定了id
        if (!Pattern.compile(SQL_TEXT_INSERT_ID_PATTERN).matcher(businessOperationLog.getSqlText()).find()) {
            // 同步插入操作时，id主键必须一致
            Matcher matcher = Pattern.compile(CONTENT_INSERT_ID_PATTERN).matcher(businessOperationLog.getContent());
            if (matcher.find()) {
                String id = matcher.group(INSERT_ID);
                String sqlText = businessOperationLog.getSqlText();
                String[] sqlSharding = sqlText.split("\\(");
                sqlText =  sqlText.replace(sqlSharding[1], INSERT_ID.concat(", ").concat(sqlSharding[1]));
                sqlText = sqlText.replace(sqlSharding[2], id.concat(", ").concat(sqlSharding[2]));
                businessOperationLog.setSqlText(sqlText);
            }
        }

        super.executeSql(businessOperationLog);
    }
}
