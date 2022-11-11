package com.bushro.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


/**
 * 全局配置解决跨域
 *
 * @author luo.qiang
 * @date 2022/11/08
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        // 设置你要允许的网站域名，如果全允许则设为 *
        //corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedOriginPattern("*");
        // corsConfig.addAllowedOrigin("http://www.xxx.com");
        // 如果要限制 HEADER 或 METHOD 请自行更改
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        // 是否允许携带cookie跨域
        corsConfig.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }

}
