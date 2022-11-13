package com.bushro.oauth2.server.controller;

import cn.hutool.core.util.StrUtil;
import com.bushro.common.core.util.R;
import com.bushro.oauth2.server.model.domain.Oauth2TokenDto;
import com.bushro.oauth2.server.model.domain.UserResource;
import com.bushro.oauth2.server.model.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * Oauth2 控制器
 */
@Api(tags = "认证中心")
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final TokenEndpoint tokenEndpoint;

    private final RedisTokenStore redisTokenStore;


    @ApiOperation("Oauth2登录认证")
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public R<Oauth2TokenDto> postAccessToken(HttpServletRequest request, Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        return R.ok(oauth2TokenDto);
    }

    /**
     * 获取当前用户
     *
     * @param request        请求
     * @param authentication 身份验证
     * @return {@link R}<{@link UserVo}>
     */
    @ApiOperation("获取当前用户")
    @GetMapping("/userInfo")
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
     * @return
     */
    @ApiOperation("退出")
    @GetMapping("/logout")
    public R logout(String access_token) {
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
