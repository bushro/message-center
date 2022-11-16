package com.bushro.system.domain;

import lombok.*;

import java.io.Serializable;


/**
 * Oauth2获取Token返回信息封装
 *
 * @author luo.qiang
 * @date 2022/11/09
 */
@Getter
@Setter
public class Oauth2TokenDto implements Serializable {
    private static final long serialVersionUID = -7863635254900221130L;
    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    private int expiresIn;
}
