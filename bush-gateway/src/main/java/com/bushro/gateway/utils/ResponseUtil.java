package com.bushro.gateway.utils;


import cn.hutool.json.JSONUtil;
import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.core.util.R;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <p> 响应工具类 </p>
 *
 * @author luo.qiang
 * @description
 * @date 2022/6/11 7:40 PM
 */
public class ResponseUtil {

    /**
     * 异常响应处理
     *
     * @param response          响应
     * @param messageEnum 响应码枚举
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author luo.qiang
     * @date 2022/6/11 7:42 PM
     */
    public static Mono<Void> writeErrorInfo(ServerHttpResponse response, MessageEnum messageEnum) {
        switch (messageEnum) {
            case UNAUTHORIZED:
            case TOKEN_EXPIRED:
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                break;
            case TOKEN_ACCESS_FORBIDDEN:
                response.setStatusCode(HttpStatus.FORBIDDEN);
                break;
            default:
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                break;
        }
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String result = JSONUtil.toJsonStr(R.failed(messageEnum));
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer)).doOnError(error -> DataBufferUtils.release(buffer));
    }

}
