package com.bushro.oauth2.server.controller;

import cn.hutool.core.util.StrUtil;
import com.bushro.common.core.model.dto.AuthDto;
import com.bushro.common.core.util.MyBeanUtil;
import com.bushro.common.core.util.R;
import com.bushro.oauth2.server.model.domain.Oauth2TokenDto;
import com.bushro.oauth2.server.model.domain.UserResource;
import com.bushro.oauth2.server.model.vo.UserVo;
import com.bushro.system.feign.ISysOauthClientFeignApi;
import com.bushro.system.vo.SysOauthClientVO;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
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

    private final ISysOauthClientFeignApi sysOauthClientFeignApi;


    /**
     * headers: Authorization: "Basic d2ViOjEyMzQ1Ng=="   [ base64解密后为web/123456 ]
     * auth服务直接调用登录 =》 http://127.0.0.1:6100/oauth/token?client_id=web&client_secret=123456&grant_type=password&username=admin&password=123456
     * 刷新令牌 =》 http://127.0.0.1:6100/auth/oauth/token?client_id=web&client_secret=123456&grant_type=refresh_token&refresh_token=xxx
     * 验证码模式 =》 http://127.0.0.1:6100/auth/oauth/token?client_id=web&client_secret=123456&username=admin&password=123456&grant_type=captcha&code=xxx&uuid=xxx
     */

    /**
     * 前端在请求的时候需要 添加请求头表名是哪个客户端 Authorization: "Basic d2ViOjEyMzQ1Ng=="
     */
    @ApiOperation("Oauth2 (系统登录使用)")
    @GetMapping("/token")
    public R<Oauth2TokenDto> getAccessToken(@ApiIgnore Principal principal, @ModelAttribute AuthDto authDto) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, MyBeanUtil.objToMapStr(authDto)).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        return R.ok(oauth2TokenDto);
    }

    /**
     * knife4j在请求的时候 会自己带上请求头
     * headers: Authorization: "Basic d2ViOjEyMzQ1Ng=="   [ base64解密后为web:123456 ]
     */
    @ApiOperation("Oauth2 (knife4j页面授权使用)")
    @PostMapping("/token")
    public Map postAccessToken(@ApiIgnore Principal principal,@ModelAttribute AuthDto authDto) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, MyBeanUtil.objToMapStr(authDto)).getBody();
        return new HashMap<String, String>(2) {{
            this.put("token_type", oAuth2AccessToken.getTokenType());
            this.put("access_token", oAuth2AccessToken.getValue());
        }};
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
