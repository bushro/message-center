package com.bushro.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.common.core.util.R;
import com.bushro.user.domain.OAuthUserInfo;
import com.bushro.user.domain.Oauth2TokenDto;
import com.bushro.user.entity.SysUser;
import com.bushro.user.form.LoginForm;


public interface ISysUserService extends IService<SysUser> {

    /**
     * 登录
     *
     * @return {@link R}<{@link OAuthUserInfo}>
     */
    R<Oauth2TokenDto> login(LoginForm loginForm);
}
