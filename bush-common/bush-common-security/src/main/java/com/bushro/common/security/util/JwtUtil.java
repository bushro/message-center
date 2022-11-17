package com.bushro.common.security.util;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.bushro.common.core.constant.BaseConstant;
import com.bushro.common.core.constant.SecurityConstants;
import com.bushro.common.core.context.AuthSourceContext;
import com.bushro.common.core.context.JwtCustomUserContext;
import com.bushro.common.core.context.SysUserContext;
import com.bushro.common.core.context.UmsUserContext;
import com.bushro.common.core.enums.AuthSourceEnum;
import com.bushro.common.core.model.dto.JwtCustomUserDto;
import com.bushro.common.core.util.MyDateUtil;
import com.bushro.common.security.bo.JwtUserBO;
import com.bushro.common.security.enums.AuthGrantTypeEnum;
import com.nimbusds.jose.JWSObject;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * <p>
 * jwt工具类
 * </p>
 *
 * @author luo.qiang
 * @description
 * @date 2022/8/1 21:52
 */
@Slf4j
public class JwtUtil {


    /**
     * 解析token
     *
     * @param token jwt
     * @return 用户信息
     * @author luo.qiang
     * @date 2022/6/14 10:35 PM
     */
    @SneakyThrows(Exception.class)
    public static JwtUserBO parse(String token) {
        token = StrUtil.replaceIgnoreCase(token, SecurityConstants.JWT_PREFIX, Strings.EMPTY);
        String payload = StrUtil.toString(JWSObject.parse(token).getPayload());
        Assert.notBlank(payload, "token失效");
        JwtUserBO jwtUserBO = JSONObject.parseObject(payload, JwtUserBO.class);
        jwtUserBO.setExpireTime(MyDateUtil.dateToStr(new Date(jwtUserBO.getExp() * 1000)));
        return jwtUserBO;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     * @author luo.qiang
     * @date 2022/6/15 13:03
     */
    public static String getUserId() {
        JwtCustomUserDto jwtCustomUserDto = JwtCustomUserContext.get();
        if (jwtCustomUserDto == null) {
            return BaseConstant.DEFAULT_CONTEXT_KEY_USER_ID;
        }
        return String.valueOf(
                AuthSourceEnum.B.getValue().equals(jwtCustomUserDto.getAuthSource())
                        ? jwtCustomUserDto.getSysUserId()
                        : jwtCustomUserDto.getUmsUserId()
        );
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     * @author luo.qiang
     * @date 2022/6/15 13:03
     */
    public static String getUsername() {
        String authType = AuthSourceContext.get();
        if (StringUtils.isBlank(authType)) {
            return BaseConstant.DEFAULT_CONTEXT_KEY_USERNAME;
        }
        return String.valueOf(
                AuthSourceEnum.B.getValue().equals(authType)
                        ? SysUserContext.getUsername()
                        : UmsUserContext.getUsername()
        );
    }

    /**
     * 获取客户端ID
     *
     * @return 客户端ID
     * @author luo.qiang
     * @date 2022/6/16 14:14
     */
    @SneakyThrows
    public static String getClientId() {
        // 方式一：从请求路径中获取
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String clientId = request.getParameter(SecurityConstants.CLIENT_ID_KEY);
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }

        // 方式二：从请求头获取 -- tips:swagger集成认证时会进行base64加密
        String token = request.getHeader(SecurityConstants.AUTHORIZATION_KEY);
        if (token.startsWith(SecurityConstants.BASIC_PREFIX)) {
            return getClientIdForBasic();
        }
        // 如果从请求头获取的是认证过后的token，则解析token中的数据
        return parse(token).getClientId();
    }

    /**
     * 从请求头中获取clientId
     *
     * @return clientId
     * @author luo.qiang
     * @date 2022/6/27 17:49
     */
    public static String getClientIdForBasic() {
        return getClientIdAndSecret().split(":")[0];
    }

    /**
     * 从请求头中获取clientSecret
     *
     * @return clientSecret
     * @author luo.qiang
     * @date 2022/6/27 17:49
     */
    public static String getClientSecretForBasic() {
        return getClientIdAndSecret().split(":")[1];
    }

    /**
     * 从请求头中获取clientId和clientSecret
     * headers: Authorization: "Basic d2ViOjEyMzQ1Ng=="   [ base64解密后为web:123456 ]
     *
     * @return clientId:clientSecret
     * @author luo.qiang
     * @date 2022/6/27 17:48
     */
    private static String getClientIdAndSecret() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String basic = request.getHeader(SecurityConstants.AUTHORIZATION_KEY);
        basic = basic.replace(SecurityConstants.BASIC_PREFIX, Strings.EMPTY);
        return new String(Base64.getDecoder().decode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * 获取认证身份标识
     *
     * @return {@link AuthGrantTypeEnum}
     * @author luo.qiang
     * @date 2022/6/16 14:14
     */
    @SneakyThrows
    public static String getAuthGrantType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String refreshToken = request.getParameter(SecurityConstants.REFRESH_TOKEN_KEY);
        String payload = StrUtil.toString(JWSObject.parse(refreshToken).getPayload());
        JSONObject jsonObject = JSONObject.parseObject(payload);
        String authGrantType = jsonObject.getString(SecurityConstants.GRANT_TYPE);
        if (StrUtil.isBlank(authGrantType)) {
            authGrantType = AuthGrantTypeEnum.USERNAME.getValue();
        }
        return authGrantType;
    }

}
