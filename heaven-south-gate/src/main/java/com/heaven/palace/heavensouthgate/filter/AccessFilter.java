package com.heaven.palace.heavensouthgate.filter;

import com.alibaba.fastjson.JSON;
import com.heaven.palace.brightpalace.api.api.permission.vo.CheckPermissionVO;
import com.heaven.palace.brightpalace.api.enums.ApiConst;
import com.heaven.palace.heavensouthgate.enums.GlobalFilterConst;
import com.heaven.palace.heavensouthgate.util.GatewayUtil;
import com.heaven.palace.jasperpalace.base.constant.CommonConst;
import com.heaven.palace.jasperpalace.base.constant.CommonConst.Header;
import com.heaven.palace.jasperpalace.base.response.BaseResponse;
import com.heaven.palace.jasperpalace.base.response.auth.AuthorityNoPermissionResponse;
import com.heaven.palace.jasperpalace.base.response.auth.TokenEmptyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;

/**
 * @Author: zhoushengen
 * @Description: 访问入口filter
 * @DateTime: 2024/1/9 9:46
 **/
@Configuration
@Slf4j
public class AccessFilter implements GlobalFilter {

    @Resource
    private WebClient.Builder webClientBuilder;

    @Value("${gate.permission.checkType}")
    private String checkType;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 过滤无需认证授权的请求
        if (null != exchange.getAttribute(GlobalFilterConst.ATTRIBUTE_IGNORE_REQUEST)) {
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest();
        LinkedHashSet<URI> uriLinkedHashSet = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        // 获取原请求路径
        String path = uriLinkedHashSet.iterator().next().getPath();

        // 校验token是否携带
        if (null == GatewayUtil.obtainAuthorization(request)) {
            return getVoidMono(exchange, new TokenEmptyResponse(), HttpStatus.UNAUTHORIZED);
        }

        // token认证校验 -> 嵌入在每个微服务里的UserAuthInterceptor中进行

        // 授权校验
       return checkPermission(request, path).doOnError(error -> {
           log.error("check permission error!", error);
       }).flatMap(checkPermissionVO -> {
           if (null != checkPermissionVO.getErrorResult()) {
               return getVoidMono(exchange, new BaseResponse(checkPermissionVO.getErrorResult()),
                   HttpStatus.INTERNAL_SERVER_ERROR);
           }
            if (checkPermissionVO.getHasPermission()) {
                return chain.filter(exchange);
            } else {
                return getVoidMono(exchange, new AuthorityNoPermissionResponse(), HttpStatus.FORBIDDEN);
            }
            });
    }

    private Mono<CheckPermissionVO> checkPermission(ServerHttpRequest request, String path) {
        return webClientBuilder
            .build().get().uri(ApiConst.HTTP_LOAD_BALANCE_SERVICE_PREFIX
                .concat(ApiConst.USER_CHECK_PERMISSION_PREFIX)
                .concat("?resourceValue=").concat(path)
                .concat("&resourceType=").concat(checkType))
                .headers(headers
                    -> headers.add(Header.AUTH_HEADER, request.getHeaders().getFirst(CommonConst.Header.AUTH_HEADER)))
                .retrieve().bodyToMono(CheckPermissionVO.class);
    }


    /**
     * 网关异常返回
     * @param exchange
     * @param body
     * @param status
     * @return
     */
    private Mono<Void> getVoidMono(ServerWebExchange exchange, BaseResponse body, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        byte[] bytes = JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
