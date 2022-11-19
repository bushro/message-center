package com.bushro.auth.core.userdetails.b;

import cn.hutool.core.util.ObjectUtil;
import com.bushro.common.core.util.AssertUtil;
import com.bushro.auth.constant.MessageConstant;
import com.bushro.auth.mapper.OauthUserMapper;
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
 * web端登陆校验
 *
 * @author luo.qiang
 * @date 2022/11/09
 */
@Service
public class SysUserServiceImpl implements UserDetailsService {

    @Resource
    private OauthUserMapper oauthUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AssertUtil.isNotEmpty(username, "请输入用户名");
        SysUserDetails resource = oauthUserMapper.getUserByName(username);
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
