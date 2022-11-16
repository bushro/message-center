package com.bushro.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.common.core.util.R;
import com.bushro.system.domain.OAuthUserInfo;
import com.bushro.system.domain.Oauth2TokenDto;
import com.bushro.system.entity.SysUser;
import com.bushro.system.form.LoginForm;


public interface ISysUserService extends IService<SysUser> {

    /**
     * 登录
     *
     * @return {@link R}<{@link OAuthUserInfo}>
     */
    R<Oauth2TokenDto> login(LoginForm loginForm);
}
