package com.bushro.oauth2.server.service;

import cn.hutool.core.util.ObjectUtil;
import com.bushro.common.core.util.AssertUtil;
import com.bushro.oauth2.server.mapper.OauthUserMapper;
import com.bushro.oauth2.server.model.domain.UserResource;
import com.bushro.oauth2.server.model.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登录校验
 */
@Service
public class UserService implements UserDetailsService {

    @Resource
    private OauthUserMapper oauthUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AssertUtil.isNotEmpty(username, "请输入用户名");
        UserDto user = oauthUserMapper.getUserByName(username);
        if (ObjectUtil.isEmpty(user)) {
            throw new UsernameNotFoundException("用户名或密码错误，请重新输入");
        }
        // 初始化登录认证对象
        UserResource userResource = new UserResource();
        // 拷贝属性
        BeanUtils.copyProperties(user, userResource);
        return userResource;
    }

}
