package com.bushro.oauth2.server.config;

import cn.hutool.crypto.digest.DigestUtil;
import com.bushro.oauth2.server.core.userdetails.b.SysUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Security 配置类
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SysUserServiceImpl sysUserDetailsService;

    // 放行和认证规则
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                // 放行的请求
////                .antMatchers( "/oauth/**", "/actuator/**").permitAll()
//                .antMatchers( "/**").permitAll()
//                .and()
//                .authorizeRequests()
//                // 其他请求必须认证才能访问
//                .anyRequest().authenticated();
//    }

    /**
     * 不拦截静态资源
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/error");
    }

    // 初始化密码编码器，用 MD5 加密密码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            /**
             * 加密
             * @param rawPassword 原始密码
             * @return
             */
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtil.md5Hex(rawPassword.toString());
            }

            /**
             * 校验密码
             * @param rawPassword       原始密码
             * @param encodedPassword   加密密码
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return DigestUtil.md5Hex(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    /**
     * 初始化认证管理对象
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**
     * 用户名密码认证授权提供者
     */
    @Bean
    public DaoAuthenticationProvider passwordAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.sysUserDetailsService);
        provider.setPasswordEncoder(this.passwordEncoder());
        // 是否隐藏用户不存在异常，默认:true-隐藏；false-抛出异常；
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    /**
     * 配置用户 -- 校验用户
     * 校验客户端见 {@link AuthorizationServerConfig#configure(ClientDetailsServiceConfigurer)}
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        // 用户名密码认证
        auth.authenticationProvider(this.passwordAuthenticationProvider());
    }

//    /**
//     * 初始化 RedisTokenStore 用于将 token 存储至 Redis
//     */
//    @Bean
//    @SuppressWarnings("all")
//    public RedisTokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory) {
//        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
//        redisTokenStore.setPrefix("TOKEN:"); // 设置key的层级前缀，方便查询
//        return redisTokenStore;
//    }


}
