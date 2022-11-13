package com.bushro.oauth2.server.config;


import com.bushro.oauth2.server.component.JwtTokenEnhancer;
import com.bushro.oauth2.server.properties.ClientOAuth2DataProperties;
import com.bushro.oauth2.server.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务
 */
@Configuration
@EnableAuthorizationServer

public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // 认证管理对象
    @Resource
    private AuthenticationManager authenticationManager;
    // 密码编码器
    @Resource
    private PasswordEncoder passwordEncoder;
    // 客户端配置类
    @Resource
    private ClientOAuth2DataProperties clientOAuth2DataProperties;
    // 登录校验
    @Resource
    private UserService userService;

    @Resource
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Resource
    private RedisTokenStore redisTokenStore;


    /**
     * 配置令牌端点安全约束
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许访问 token 的公钥，默认 /oauth/token_key 是受保护的
        security.tokenKeyAccess("permitAll()")
                //https://blog.csdn.net/zjy660358/article/details/118794040
                //.allowFormAuthenticationForClients()
                // 允许检查 token 的状态，默认 /oauth/check_token 是受保护的
                .checkTokenAccess("permitAll()");
    }

    /**
     * 客户端配置 - 授权模型
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientOAuth2DataProperties.getClientId()) // 客户端标识 ID
                .secret(passwordEncoder.encode(clientOAuth2DataProperties.getSecret())) // 客户端安全码
                .authorizedGrantTypes(clientOAuth2DataProperties.getGrantTypes()) // 授权类型
                .accessTokenValiditySeconds(clientOAuth2DataProperties.getTokenValidityTime()) // token 有效期
                .refreshTokenValiditySeconds(clientOAuth2DataProperties.getRefreshTokenValidityTime()) // 刷新 token 的有效期
                .scopes(clientOAuth2DataProperties.getScopes()); // 客户端访问范围
    }

    /**
     * 配置授权以及令牌的访问端点和令牌服务
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(accessTokenConverter());
        //配置JWT的内容增强器
        enhancerChain.setTokenEnhancers(delegates);
        // 认证器
        endpoints.authenticationManager(authenticationManager)
                // 具体登录的方法
                .userDetailsService(userService)
                .tokenStore(redisTokenStore)
                // 令牌增强对象，增强返回的结果
                .tokenEnhancer(enhancerChain);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        return keyStoreKeyFactory.getKeyPair("mytest");
    }

}
