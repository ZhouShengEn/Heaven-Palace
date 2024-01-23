package com.heaven.palace.heavensouthgate.filter;

import com.alibaba.fastjson.JSON;
import com.heaven.palace.heavensouthgate.enums.GlobalFilterConst;
import com.heaven.palace.heavensouthgate.util.GatewayUtil;
import com.heaven.palace.jasperpalace.base.cache.constants.CommonCacheConst;
import com.heaven.palace.jasperpalace.base.context.CurrentBaseContext;
import com.heaven.palace.jasperpalace.base.response.BaseResponse;
import com.heaven.palace.jasperpalace.base.response.auth.TokenEmptyResponse;
import com.heaven.palace.jasperpalace.base.response.auth.TokenExpireResponse;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

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

    @Resource
    private RedissonClient redissonClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 过滤无需认证授权的请求
        if (null != exchange.getAttribute(GlobalFilterConst.ATTRIBUTE_IGNORE_REQUEST)) {
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest();

        // token获取
        String token;
        if (null == (token = GatewayUtil.obtainAuthorization(request))) {
            return getVoidMono(exchange, new TokenEmptyResponse(), HttpStatus.UNAUTHORIZED);
        }

        // 这里直接引入redis不使用防腐层是因为不想引入紫霄宫庞大的组件依赖影响到网关应用，但同时需要网关读取缓存分担用户中心的压力
        RBucket<CurrentBaseContext.UserCache> bucket = redissonClient
                .getBucket(CommonCacheConst.AUTH_TOKEN_KEY_PREFIX + token);
        CurrentBaseContext.UserCache userCache = bucket.get();
        if (null == userCache) {
            return getVoidMono(exchange, new TokenExpireResponse(), HttpStatus.UNAUTHORIZED);
        }

        // token认证+授权校验
        Mono<Boolean> booleanMono = webClientBuilder
            .build().get().uri("http://bright-palace/api/user/auth").headers(headers -> request.getHeaders())
            .retrieve().bodyToMono(Boolean.class);

        return chain.filter(exchange);
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
