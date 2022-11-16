package com.bushro.system.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LoginUserVo implements Serializable {

    private static final long serialVersionUID = 5416914445350300883L;
    private String nickname;
    private String token;
    private String avatarUrl;

}
