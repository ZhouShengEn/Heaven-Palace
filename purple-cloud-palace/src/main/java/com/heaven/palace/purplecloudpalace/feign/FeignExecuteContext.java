package com.heaven.palace.purplecloudpalace.feign;

import lombok.Data;

@Data
public class FeignExecuteContext {
    private static ThreadLocal<FeignExecuteContext> localFeignExecuteContext =
        ThreadLocal.withInitial(() -> new FeignExecuteContext());
    // 当前feign调用的目标微服务名称
    private String currentApplicationName;

    public static FeignExecuteContext getLocalInstance() {
        return localFeignExecuteContext.get();
    }

    // 避免内存泄露
    public static void removeLocalInstance() {
        localFeignExecuteContext.remove();
    }
}
