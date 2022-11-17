package com.bushro.oauth2.server.config;


import com.bushro.common.security.enums.AuthClientIdEnum;
import com.bushro.oauth2.server.core.ClientDetailsServiceImpl;
import com.bushro.oauth2.server.core.PreAuthenticatedUserDetailsService;
import com.bushro.oauth2.server.core.userdetails.b.SysUserServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 授权服务
 * @author bushro
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 认证管理对象
     */
    private final AuthenticationManager authenticationManager;
    /**
     * 登录校验
     */
    private final SysUserServiceImpl sysUserServiceImpl;

    private final CustomAdditionalInformation customAdditionalInformation;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final TokenStore tokenStore;

    private final ClientDetailsServiceImpl clientDetailsService;


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
        clients.withClientDetails(clientDetailsService);
//        clients.inMemory()
//                // 客户端标识 ID
//                .withClient(clientOAuth2DataProperties.getClientId())
//                // 客户端安全码
//                .secret(passwordEncoder.encode(clientOAuth2DataProperties.getSecret()))
//                // 授权类型
//                .authorizedGrantTypes(clientOAuth2DataProperties.getGrantTypes())
//                // token 有效期
//                .accessTokenValiditySeconds(clientOAuth2DataProperties.getTokenValidityTime())
//                // 刷新 token 的有效期
//                .refreshTokenValiditySeconds(clientOAuth2DataProperties.getRefreshTokenValidityTime())
//                // 客户端访问范围
//                .scopes(clientOAuth2DataProperties.getScopes());
    }

    /**
     * 配置授权以及令牌的访问端点和令牌服务
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 认证器
        endpoints.authenticationManager(authenticationManager)
                // 默认只有POST,这里要加入需要的请求方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT,HttpMethod.DELETE)
                // 配置令牌的存储
                .tokenServices(this.tokenServices());
    }

    /**
     * 配置 Token 的一些基本信息
     */
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 存储位置
        tokenServices.setTokenStore(this.tokenStore);
        // 是否支持刷新
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(this.clientDetailsService);
        // 配置JWT的内容增强器 注入“jwt添加额外信息”相关实例
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Lists.newArrayList(
                this.jwtAccessTokenConverter,
                this.customAdditionalInformation
        ));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        // 多用户体系下，刷新token再次认证客户端ID和 UserDetailService 的映射Map
        Map<AuthClientIdEnum, UserDetailsService> clientUserDetailsServiceMap = Maps.newHashMap();
        // 系统管理客户端
        clientUserDetailsServiceMap.put(AuthClientIdEnum.WEB, this.sysUserServiceImpl);

        // 刷新token模式下，重写预认证提供者替换其AuthenticationManager，可自定义根据客户端ID和认证方式区分用户体系获取认证用户信息
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedUserDetailsService<>(clientUserDetailsServiceMap));
        tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));

        /** refresh_token有两种使用方式：
         *  默认重复使用(true)：access_token过期刷新时， refresh_token过期时间未改变，仍以初次生成的时间为准
         *  非重复使用(false)：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新便永不失效达到无需再次登录的目的
         */
        tokenServices.setReuseRefreshToken(true);
        return tokenServices;

    }
}
