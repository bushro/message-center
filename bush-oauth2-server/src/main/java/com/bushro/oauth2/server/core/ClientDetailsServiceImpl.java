package com.bushro.oauth2.server.core;


import com.bushro.common.core.constant.CommonConstants;
import com.bushro.common.core.util.R;
import com.bushro.oauth2.server.constant.PasswordEncoderTypeEnum;
import com.bushro.system.feign.ISysOauthClientFeignApi;
import com.bushro.system.vo.SysOauthClientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;


/**
 * OAuth2 客户端信息
 *
 * 根据请求的clientId找对应的客户端，判断是否支持
 *
 * @author luo.qiang
 * @date 2022/11/17
 */
@Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final ISysOauthClientFeignApi sysOauthClientFeignApi;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(cacheNames = "auth", key = "'oauth-client:'+#clientId")
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            R<SysOauthClientVO> result = this.sysOauthClientFeignApi.getClient(clientId);
            if (CommonConstants.SUCCESS.equals(result.getCode())) {
                SysOauthClientVO client = result.getData();
                BaseClientDetails clientDetails = new BaseClientDetails(
                        client.getClientId(),
                        client.getResourceIds(),
                        client.getScope(),
                        client.getAuthorizedGrantTypes(),
                        client.getAuthorities(),
                        client.getWebServerRedirectUri()
                );
                clientDetails.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
                clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
                clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (EmptyResultDataAccessException var4) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }

}
