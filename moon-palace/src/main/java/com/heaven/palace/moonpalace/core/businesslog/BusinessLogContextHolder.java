package com.heaven.palace.moonpalace.core.businesslog;

import com.heaven.palace.moonpalace.common.persistence.model.BusinessOperationLog;

import java.io.Serializable;


/**
 * 被修改的bean临时存放的地方
 *
 * @author ZhouShengEn
 * @date 2022-8-25
 */
public class BusinessLogContextHolder implements Serializable {

    private static final long serialVersionUID = 7380812710769796938L;

    private static final ThreadLocal<BusinessOperationLog> context = new ThreadLocal<>();

    public static void set(BusinessOperationLog obj) {
        context.set(obj);
    }

    public static BusinessOperationLog get() {
        return context.get();
    }

}
