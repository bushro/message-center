package com.bushro.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
 * 全局过滤器 - 拦截http请求做逻辑处理
 *
 * 删除请求头中的`Expect` （主要解决三方服务带此请求头请求接口时无法响应问题）
 *
 * @author luo.qiang
 * @date 2022/11/08
 */
@Component
@Order(1)
public class HeaderGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(httpHeaders -> httpHeaders.remove("Expect"))
                .build();
        return chain.filter(exchange.mutate().request(request.mutate().build()).build());
    }

}
