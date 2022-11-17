package com.bushro.oauth2.server.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bushro.common.core.constant.SecurityConstants;
import com.bushro.common.core.context.JwtCustomUserContext;
import com.bushro.common.core.model.dto.AuthDto;
import com.bushro.common.core.model.dto.JwtCustomUserDto;
import com.bushro.common.core.util.MyBeanUtil;
import com.bushro.common.core.util.MyDateUtil;
import com.bushro.common.core.util.R;
import com.bushro.common.redis.util.RedisUtil;
import com.bushro.common.security.util.JwtUtil;
import com.bushro.oauth2.server.core.userdetails.b.SysUserDetails;
import com.bushro.oauth2.server.model.domain.Oauth2TokenDto;
import com.bushro.oauth2.server.model.vo.UserVo;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Oauth2 控制器
 */
@Api(tags = "认证中心")
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {

    private final TokenEndpoint tokenEndpoint;

    private final KeyPair keyPair;


    /**
     * headers: Authorization: "Basic d2ViOjEyMzQ1Ng=="   [ base64解密后为web:123456 ]
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
        authDto.setClient_id(JwtUtil.getClientId());
        authDto.setClient_secret(JwtUtil.getClientSecretForBasic());
        OAuth2AccessToken accessToken = this.accessToken(principal, authDto);
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(accessToken.getValue())
                .refreshToken(accessToken.getRefreshToken().getValue())
                .expiresIn(accessToken.getExpiresIn())
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
        authDto.setClient_id(JwtUtil.getClientId());
        authDto.setClient_secret(JwtUtil.getClientSecretForBasic());
        OAuth2AccessToken accessToken = this.accessToken(principal, authDto);
        return new HashMap<String, String>(2) {{
            this.put("token_type", accessToken.getTokenType());
            this.put("access_token", accessToken.getValue());
        }};
    }

    @SneakyThrows(Exception.class)
    private OAuth2AccessToken accessToken(@ApiIgnore Principal principal, @ModelAttribute AuthDto params) {
        log.info("OAuth认证授权 请求参数：{}", JSON.toJSONString(params));
        // 1、认证
        OAuth2AccessToken accessToken = this.tokenEndpoint.postAccessToken(principal, MyBeanUtil.objToMapStr(params)).getBody();
        // 2、拿到其中的自定义用户信息存入redis中
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        JwtCustomUserDto user = JSONObject.parseObject(JSON.toJSONString(additionalInformation), JwtCustomUserDto.class);
        user.setToken(accessToken.getTokenType() + " " + accessToken.getValue());
        user.setExpireTime(MyDateUtil.dateToStr(accessToken.getExpiration()));
        RedisUtil.setEx(SecurityConstants.JWT_CUSTOM_USER + user.getJti(), JSON.toJSONString(user), accessToken.getExpiresIn(), TimeUnit.SECONDS);
        return accessToken;
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
        SysUserDetails sysUserDetails = (SysUserDetails) authentication.getPrincipal();
        // 转为前端可用的视图对象
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUserDetails, userVo);
        return R.ok(userVo);
    }


    /**
     * 注销
     *
     * @return {@link R}
     */
    @ApiOperation("退出")
    @GetMapping("/logout")
    public R logout() {
        JwtCustomUserDto jwtUserBO = JwtCustomUserContext.get();
        if (ObjectUtil.isEmpty(jwtUserBO)) {
            return R.failed("未登陆, 不需要退出");
        }
        String jti = jwtUserBO.getJti();
        RedisUtil.delete(SecurityConstants.JWT_CUSTOM_USER + jti);
        return R.ok("退出成功");
    }

    @ApiOperation("获取RSA公钥")
    @GetMapping("/publicKey")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
