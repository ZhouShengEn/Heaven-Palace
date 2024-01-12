package com.heaven.palace.heavensouthgate.filter;

import com.heaven.palace.heavensouthgate.enums.GlobalFilterConst;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: zhoushengen
 * @Description: 排除哪些路由不参与过滤
 * @DateTime: 2024/1/9 10:13
 **/
@Component("IgnoreGlobalFilter")
public class IgnoreGlobalFilter extends AbstractGatewayFilterFactory<IgnoreGlobalFilter.Config> {

    public IgnoreGlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            if (config.enable) {
                exchange.getAttributes().put(GlobalFilterConst.ATTRIBUTE_IGNORE_REQUEST, true);
            }
            return chain.filter(exchange);

        };
    }

    public static class Config {

        private Boolean enable;

        public Config() {
        }

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }
    }
}
