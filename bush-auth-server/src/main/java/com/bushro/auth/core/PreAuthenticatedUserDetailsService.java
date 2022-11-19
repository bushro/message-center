package com.bushro.auth.core;


import com.bushro.common.core.enums.IBaseEnum;
import com.bushro.common.security.enums.AuthClientIdEnum;
import com.bushro.common.security.enums.AuthGrantTypeEnum;
import com.bushro.common.security.util.JwtUtil;
import com.bushro.auth.config.AuthorizationServerConfig;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * <p> 刷新token再次认证 UserDetailsService </p>
 *
 * @author luo.qiang
 * @description 根据不同的客户端选择不同的认证方式进行认证处理
 * @date 2022/6/15 14:35
 */
@NoArgsConstructor
public class PreAuthenticatedUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    /**
     * 客户端ID和用户服务 UserDetailService 的映射
     *
     * @see AuthorizationServerConfig#tokenServices()
     */
    private Map<AuthClientIdEnum, UserDetailsService> userDetailsServiceMap;

    public PreAuthenticatedUserDetailsService(Map<AuthClientIdEnum, UserDetailsService> userDetailsServiceMap) {
        Assert.notNull(userDetailsServiceMap, "userDetailsService cannot be null.");
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsServiceMap, "UserDetailsService must be set");
    }

    /**
     * 重写PreAuthenticatedAuthenticationProvider 的 preAuthenticatedUserDetailsService 属性
     * 可根据客户端和认证方式选择用户服务 UserDetailService 获取用户信息 UserDetail
     */
    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        String clientId = JwtUtil.getClientId();
        // 获取认证身份标识，默认用户名认证
        AuthGrantTypeEnum authGrantTypeEnum = IBaseEnum.getEnumByValue(JwtUtil.getAuthGrantType(), AuthGrantTypeEnum.class);
        AuthClientIdEnum authClientIdEnum = IBaseEnum.getEnumByValue(clientId, AuthClientIdEnum.class);
        UserDetailsService userDetailsService = this.userDetailsServiceMap.get(authClientIdEnum);
        switch (authClientIdEnum) {
            case WEB:
                switch (authGrantTypeEnum) {
                    default:
                        // 用户名方式认证
                        return userDetailsService.loadUserByUsername(authentication.getName());
                }
            default:
                return userDetailsService.loadUserByUsername(authentication.getName());
        }
    }

}
