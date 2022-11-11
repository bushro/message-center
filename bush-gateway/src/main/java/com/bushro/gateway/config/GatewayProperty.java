package com.bushro.gateway.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 网关属性
 *
 * @author luo.qiang
 * @date 2022/11/08
 */
@Data
@Component
@ConfigurationProperties(prefix = "gateway.auth")
public class GatewayProperty {
    /**
     * 忽略安全认证的url
     */
    private List<String> ignoreUrls;
    /**
     * Web前端Api需要拦截的url
     */
    private List<String> webApiUrls;
    /**
     * 开放接口Api需要拦截的url
     */
    private List<String> openApiUrls;

}
