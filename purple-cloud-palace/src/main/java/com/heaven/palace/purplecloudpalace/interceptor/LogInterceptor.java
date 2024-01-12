package com.heaven.palace.purplecloudpalace.interceptor;

import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: zhoushengen
 * @Description: 日志全链路追踪
 * @DateTime: 2024/1/10 17:25
 **/
public class LogInterceptor implements AsyncHandlerInterceptor {

    public final static String DEFAULT_SPAN = "1";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(CommonConst.LOG_TRACE_ID_HEADER);
        String spanId = request.getHeader(CommonConst.LOG_SPAN_ID_HEADER);
        MDC.put(CommonConst.LOG_TRACE_ID_HEADER, StringUtils.isEmpty(traceId) ? UUID.randomUUID().toString() : traceId);
        MDC.put(CommonConst.LOG_SPAN_ID_HEADER, StringUtils.isEmpty(spanId) ? DEFAULT_SPAN :
            String.valueOf(Integer.parseInt(spanId) + 1));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(CommonConst.LOG_TRACE_ID_HEADER);
        MDC.remove(CommonConst.LOG_SPAN_ID_HEADER);
        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
