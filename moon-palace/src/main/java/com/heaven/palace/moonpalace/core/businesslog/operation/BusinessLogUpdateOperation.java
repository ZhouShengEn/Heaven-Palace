package com.heaven.palace.moonpalace.core.businesslog.operation;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;
import com.heaven.palace.moonpalace.core.businesslog.BusinessLogContextHolder;
import com.heaven.palace.moonpalace.core.businesslog.context.BusinessLogTempObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Slf4j
public class BusinessLogUpdateOperation extends BusinessLogOperationFactory {

    private final String CHAR_TIME_PATTERN = "\\d+:\\d+:\\d+";

    @Override
    public void writeBusinessLog() {
        BusinessLogTempObject businessLogTempObject = BUSINESS_LOG_TEMP_OBJECT.get();
        if (null != businessLogTempObject) {
            BusinessOperationLog businessOperationLog = BusinessLogContextHolder.get();
            super.execute(() -> {
                Diff diff = javers.compare(businessLogTempObject.getOldObject(), businessLogTempObject.getNewObj());
                StringBuilder stringBuilder = new StringBuilder();
                if (diff.hasChanges()) {
                    for (Change change : diff.getChanges()) {
                        ValueChange valueChange;
                        if ((change instanceof ValueChange)
                            && !excludeInvalidValueChange(valueChange = (ValueChange)change)) {
                            stringBuilder.append("属性：").append((valueChange.getPropertyName())).append(" 由'")
                                .append(valueChange.getLeft()).append("'修改为'").append(valueChange.getRight())
                                .append("';\r\n");
                        }
                    }
                }
                businessOperationLog
                    .setContent(StringUtils.isNotEmpty(stringBuilder.toString()) ? stringBuilder.toString() : UNDO_LOG);
                return businessOperationLog;
            });

        } else {
            log.error("update BusinessLog businessLogTempObject is null!");
        }

    }

    private Boolean excludeInvalidValueChange(ValueChange valueChange) {
        Pattern compile = Pattern.compile(CHAR_TIME_PATTERN);
        boolean leftNullJudge = (null == valueChange.getLeft() || "null".equals(valueChange.getLeft()) || "".equals(valueChange.getLeft()));
        boolean rightNullJudge = (null == valueChange.getRight() || "null".equals(valueChange.getRight()) || "".equals(valueChange.getRight()));
        boolean nullValueChange = leftNullJudge && rightNullJudge;
        return nullValueChange || ((!leftNullJudge && compile.matcher(valueChange.getLeft().toString()).find()) && (!rightNullJudge && compile.matcher(valueChange.getRight().toString()).find()));
    }
    
}
