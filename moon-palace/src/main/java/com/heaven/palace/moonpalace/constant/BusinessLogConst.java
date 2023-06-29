package com.heaven.palace.moonpalace.constant;

import java.util.Arrays;

/**
 * @author zhoushengen
 * @version 1.0
 * @date 2023/2/20 15:23
 */
public interface BusinessLogConst {

    /**
     * sql操作类型枚举
     */
    enum SqlTypeEnum {

        NONE("", "businessLogNoneOperation"),
        INSERT("Add", "businessLogInsertOperation"),
        UPDATE("Edit", "businessLogUpdateOperation"),
        DELETE("Delete", "businessLogDeleteOperation");

        // 操作名称
        private final String operation;

        private final String logOperateBeanName;

        SqlTypeEnum(String operation, String logOperateBeanName) {
            this.operation = operation;
            this.logOperateBeanName = logOperateBeanName;
        }

        public String getLogOperateBeanName() {
            return logOperateBeanName;
        }

        public String getOperation() {
            return operation;
        }

        public static String getOperationBeanNameByOperate(String operation) {
            return Arrays.stream(values()).filter(sqlType -> operation.equals(sqlType.getOperation())).findFirst().orElse(NONE).logOperateBeanName;
        }
    }

    /**
     * 日志操作状态枚举
     */
    enum SyncStatusEnum {
        // 正常状态
        Normal(0),
        // 需要同步状态
        Need_Synchronize(1),
        // 已同步状态
        Synchronized(2)
        ;


        private final Integer status;

        SyncStatusEnum(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }
    }
}
