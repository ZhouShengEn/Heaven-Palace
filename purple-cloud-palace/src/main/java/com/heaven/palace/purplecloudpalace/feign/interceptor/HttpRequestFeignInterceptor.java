package com.heaven.palace.purplecloudpalace.feign.interceptor;

import feign.Client;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * @Author: zhoushengen
 * @Description: http请求feign拦截器
 * @DateTime: 2024/1/24 16:21
 **/
public class HttpRequestFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

    }

    /**
     * 越过证书校验
     * @return
     */
    @Bean
    public Client feignClient() {
        return new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
    }

    private SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            return sslContext.getSocketFactory();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
