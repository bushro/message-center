package com.bushro.gateway.component;

import cn.hutool.core.util.StrUtil;
import com.bushro.common.core.enums.MessageEnum;
import com.bushro.common.core.util.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * 处理异常
 *
 * @author luo.qiang
 * @date 2022/11/07
 */
@Component
public class HandleException {

    @Resource
    private ObjectMapper objectMapper;

    public Mono<Void> writeError(ServerWebExchange exchange, String error) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        R resultInfo = null;
        if (StrUtil.isEmpty(error)) {
            resultInfo = R.failed(MessageEnum.UN_AUTHORIZED.message());
        } else {
            resultInfo = R.failed(error);
        }
        String resultInfoJson = null;
        DataBuffer buffer = null;
        try {
            resultInfoJson = objectMapper.writeValueAsString(resultInfo);
            buffer =  response.bufferFactory().wrap(resultInfoJson.getBytes(Charset.forName("UTF-8")));
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return response.writeWith(Mono.just(buffer));
    }

}
