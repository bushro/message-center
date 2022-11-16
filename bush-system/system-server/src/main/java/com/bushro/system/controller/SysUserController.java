package com.bushro.system.controller;

import com.bushro.common.core.util.R;
import com.bushro.system.domain.Oauth2TokenDto;
import com.bushro.system.form.LoginForm;
import com.bushro.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统用户控制器
 *
 * @author luo.qiang
 * @date 2022/11/06
 */
@RestController
@Api(tags = "用户")
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @ApiOperation(value = "登陆")
    @PostMapping("/login")
    public R<Oauth2TokenDto> login(@RequestBody LoginForm loginForm) {
        return sysUserService.login(loginForm);
    }
}
