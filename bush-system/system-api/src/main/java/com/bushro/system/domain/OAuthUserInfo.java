package com.bushro.system.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OAuthUserInfo implements Serializable {

    private static final long serialVersionUID = 3637647988219388919L;
    private String nickname;
    private String avatarUrl;
    private String accessToken;
    private String expireIn;
    private List<String> scopes;
    private String refreshToken;

}
