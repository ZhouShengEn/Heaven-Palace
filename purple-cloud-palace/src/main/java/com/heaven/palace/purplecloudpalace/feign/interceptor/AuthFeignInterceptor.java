package com.heaven.palace.purplecloudpalace.feign.interceptor;


import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.heaven.palace.jasperpalace.base.constant.CommonConst.Header;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.exception.BusinessException;
import com.heaven.palace.jasperpalace.base.exception.CommonExceptionEnum;
import com.heaven.palace.jasperpalace.base.exception.EncryptException;
import com.heaven.palace.jasperpalace.base.util.RandomAESEncryptUtils;
import com.heaven.palace.purplecloudpalace.feign.FeignExecuteContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author zhoushengen
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "inner.access")
@Data
@Validated
public class AuthFeignInterceptor implements RequestInterceptor {
    // 需要外部注入
    @NotNull
    private Map<String, String> keyMap;

    @Value("${header.context.secret}")
    private String secret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 添加上下文
        Map<String, Object> currentBaseContextMap = CurrentBaseContext.getAll();
        if (!MapUtil.isEmpty(currentBaseContextMap)) {
            try {
                requestTemplate.header(Header.BASE_CONTEXT_HEADER,
                    RandomAESEncryptUtils.encryptForString(JSON.toJSONString(currentBaseContextMap), secret));
            } catch (EncryptException e) {
                throw new RuntimeException(e);
            }
            requestTemplate.header(Header.AUTH_HEADER, Header.AUTH_HEADER_BEARER.concat(CurrentBaseContext.getUserToken()));
        }

        //添加全局traceId 用于LogInterceptor
        requestTemplate.header(Header.LOG_TRACE_ID_HEADER, MDC.get(Header.LOG_TRACE_ID_HEADER));
        //添加该服务的spanId 用于LogInterceptor
        requestTemplate.header(Header.LOG_SPAN_ID_HEADER, MDC.get(Header.LOG_SPAN_ID_HEADER));
        // 获取body体
        byte[] body = requestTemplate.body();

        // 获取目标微服务名称并设置到local中
        String applicationName = requestTemplate.feignTarget().name();
        FeignExecuteContext.getLocalInstance().setCurrentApplicationName(applicationName);

        // 验证之后发现如果mediaType=application/x-www-form-urlencoded,不管feign声明是post还是get
        // 此处body都是空的（改拦截器中暂时无法获取MediaType）
        if (null != body && body.length > 0) {
            // 如果配置了key就加密传输
            String key = keyMap.get(applicationName);
            if (StringUtils.isBlank(key)) {
                return;
            }
            // 加密
            try {
                String originString = new String(body, StandardCharsets.UTF_8);
                String encryptedString = RandomAESEncryptUtils.encryptForString(originString, key);
                byte[] encryptBytes = encryptedString.getBytes();
                requestTemplate.body(encryptBytes, StandardCharsets.UTF_8);
            } catch (EncryptException e) {
                log.error("feignConfig exception", e);
                FeignExecuteContext.removeLocalInstance();
                throw new BusinessException(CommonExceptionEnum.SERVER_ERROR);
            }
        }
    }
}