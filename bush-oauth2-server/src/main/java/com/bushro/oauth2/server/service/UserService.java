package com.bushro.oauth2.server.service;

import cn.hutool.core.util.ObjectUtil;
import com.bushro.common.core.util.AssertUtil;
import com.bushro.oauth2.server.constant.MessageConstant;
import com.bushro.oauth2.server.mapper.OauthUserMapper;
import com.bushro.oauth2.server.model.domain.UserResource;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 登陆校验
 *
 * @author luo.qiang
 * @date 2022/11/09
 */
@Service
public class UserService implements UserDetailsService {

    @Resource
    private OauthUserMapper oauthUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AssertUtil.isNotEmpty(username, "请输入用户名");
        UserResource resource = oauthUserMapper.getUserByName(username);
        if (ObjectUtil.isEmpty(resource)) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        } else if (!resource.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!resource.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!resource.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!resource.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return resource;
    }

}
