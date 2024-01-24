package com.heaven.palace.peakcloudpalace.business.oauth2.feign;

import com.heaven.palace.purplecloudpalace.feign.interceptor.HttpRequestFeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: zhoushengen
 * @Description: 这里以feign的http请求为例，展示客户端需要调用服务端openapi网关所暴露的接口来获取token，这里内部客户端则调用内部的网关
 * ，内部客户端最好直接调用nacos上注册的服务
 * @DateTime: 2024/1/24 15:51
 **/
@FeignClient(value = "oauth2-api-service", url = "${oauth2.server.brightPalaceUrl}", configuration =
    HttpRequestFeignInterceptor.class)
public class Oauth2UserFeign {

    @GetMapping


}
