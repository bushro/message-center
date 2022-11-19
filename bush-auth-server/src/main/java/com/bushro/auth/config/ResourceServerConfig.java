//package com.bushro.oauth2.server.com.bushro.common.redis.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.com.bushro.common.redis.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.com.bushro.common.redis.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.com.bushro.common.redis.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.com.bushro.common.redis.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//import javax.annotation.Resource;
//
//
///**
// * 资源服务器配置
// *
// * @author bushro
// * @date 2022/11/17
// */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Resource
//    private MyAuthenticationEntryPoint authenticationEntryPoint;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        // 配置放行的资源
//        http.authorizeRequests()
////                .antMatchers("/oauth/**", "actuator/**").permitAll()
////                .antMatchers("/doc.html**", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**")
////                .permitAll()
//                .antMatchers("/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }
//
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.authenticationEntryPoint(authenticationEntryPoint);
//    }
//
//}
