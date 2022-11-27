package com.bushro.common.sentinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> sentinel获取流控应用值 -- 实现授权规则中的流控应用值解析 </p>
 *
 * @author bushro
 * @description 解析请求数据并返回
 * @date 2022/5/20 14:45
 */
@Component
public class SentinelRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        // 这里怎么去取值可自定义
        String origin = request.getParameter("origin");
        if (StringUtils.hasLength(origin)) {
            origin = " ";
        }
        return origin;
    }

}
