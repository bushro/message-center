package com.bushro.gateway.filter;


import cn.hutool.core.util.StrUtil;
import com.bushro.common.core.constant.BaseConstant;
import com.bushro.common.core.constant.SecurityConstants;
import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.security.bo.JwtUserBO;
import com.bushro.common.security.util.JwtUtil;
import com.bushro.gateway.component.HandleException;
import com.bushro.gateway.config.GatewayProperty;
import com.bushro.gateway.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.bushro.common.redis.util.RedisUtil;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.List;

/**
 * 网关全局过滤器
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private GatewayProperty gatewayProperty;

    @Resource
    private HandleException handleException;

    @Value("${service.name.oauth2}")
    private String oauthServerName;

    /**
     * 身份校验处理
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 判断当前的请求是否在白名单中
        String path = exchange.getRequest().getURI().getPath();
        // 白名单放行
        if (this.isSkip(path)) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst(SecurityConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token)) {
            return handleException.writeError(exchange, "登录信息过期，请重新登录");
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "").replace("bearer ", "");
            JwtUserBO userBO = JwtUtil.parse(realToken);
            String userJson = RedisUtil.get(SecurityConstants.JWT_CUSTOM_USER + userBO.getJti());
            if (StrUtil.isBlank(userJson)) {
                // 校验redis中的token是否过期 -- 即是否注销或其他原因...
                return ResponseUtil.writeErrorInfo(response, MessageEnum.TOKEN_EXPIRED);
            }

            HttpHeaders headers = new HttpHeaders();
            // URLEncoder.encode：解决下游获取中文乱码问题
            headers.add(BaseConstant.REQUEST_HEADER_JWT_USER, URLEncoder.encode(userJson, "UTF-8"));
            ServerHttpRequest nowRequest = request.mutate().headers(header -> header.addAll(headers)).build();
            // 将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(nowRequest).build();

//            JWSObject jwsObject = JWSObject.parse(realToken);
//            String userStr = jwsObject.getPayload().toString();
//            log.info("AuthGlobalFilter.filter() user:{}", userStr);
//            // 校验 token 是否有效
//            String checkTokenUrl = oauthServerName + "/oauth/check_token?token=".concat(realToken);
//            // 发送远程请求，验证 token
//            ResponseEntity<String> entity = restTemplate.getForEntity(checkTokenUrl, String.class);
//            // token 无效的业务逻辑处理
//            if (entity.getStatusCode() != HttpStatus.OK) {
//                log.error("Token was not recognised, token: {}", realToken);
//                return handleException.writeError(exchange, "登录信息过期，请重新登录");
//            }
//            if (StrUtil.isBlank(entity.getBody())) {
//                log.error("This token is invalid: {}", realToken);
//                return handleException.writeError(exchange, "登录信息过期，请重新登录");
//            }
            // 放行
            return chain.filter(build);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtil.writeErrorInfo(response, MessageEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 安全认证
     *
     * @param requestUrl 请求url
     * @return boolean
     */
    private boolean isSkip(String requestUrl) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> ignoreUrls = gatewayProperty.getIgnoreUrls();
//        List<String> openApiUrls = gatewayProperty.getOpenApiUrls();
//        List<String> webApiUrls = gatewayProperty.getWebApiUrls();

        boolean ifIgnoreUrl = ignoreUrls.stream().anyMatch(requestUrl::contains);
//        boolean ifOpenApiUrl = openApiUrls.stream().anyMatch(requestUrl::contains);
//        boolean ifWebApiUrl = webApiUrls.stream().anyMatch(requestUrl::contains);

        return ifIgnoreUrl;
    }

    public static void main(String[] args) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(pathMatcher.match("/**/oauth/token", "/auth-server/we/oauth/token"));
        System.out.println(pathMatcher.match("/*/oauth/token/*", "/auth-server/oauth/token/werw"));
        System.out.println(pathMatcher.match("/*/oauth/token", "/oauth/token"));
    }

    /**
     * 网关过滤器的排序，数字越小优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
