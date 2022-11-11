package com.bushro.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关白名单配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "gateway.ignore")
public class IgnoreUrlsConfig {

    private List<String> urls;

}
