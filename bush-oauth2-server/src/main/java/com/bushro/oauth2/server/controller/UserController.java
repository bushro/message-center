package com.bushro.oauth2.server.controller;

import cn.hutool.core.util.StrUtil;
import com.bushro.common.core.util.R;
import com.bushro.oauth2.server.model.domain.UserResource;
import com.bushro.oauth2.server.model.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户中心
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RedisTokenStore redisTokenStore;


    @GetMapping("/me")
    public R<UserVo> getCurrentUser(HttpServletRequest request, Authentication authentication) {
        // 获取登录用户的信息，然后设置
        UserResource userResource = (UserResource) authentication.getPrincipal();
        // 转为前端可用的视图对象
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userResource, userVo);
        return R.ok(userVo);
    }

    /**
     * 退出
     *
     * @param access_token
     * @param authorization
     * @return
     */
    @GetMapping("/logout")
    public R logout(String access_token, String authorization) {
        // 判断 access_token 是否为空，为空将 authorization 赋值给 access_token
        if (StrUtil.isBlank(access_token)) {
            access_token = authorization;
        }
        // 判断 authorization 是否为空
        if (StrUtil.isBlank(access_token)) {
            return R.ok("退出成功");
        }
        // 判断 bearer token 是否为空
        if (access_token.toLowerCase().contains("bearer ".toLowerCase())) {
            access_token = access_token.toLowerCase().replace("bearer ", "");
        }
        // 清除 redis token 信息
        OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(access_token);
        if (oAuth2AccessToken != null) {
            redisTokenStore.removeAccessToken(oAuth2AccessToken);
            OAuth2RefreshToken refreshToken = oAuth2AccessToken.getRefreshToken();
            redisTokenStore.removeRefreshToken(refreshToken);
        }
        return R.ok("退出成功");
    }

}
