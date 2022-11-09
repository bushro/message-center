package com.bushro.oauth2.server.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户签证官
 *
 * @author luo.qiang
 * @date 2022/11/06
 */
@Getter
@Setter
@ApiModel(value = "UserVo", description = "登录用户信息")
public class UserDto implements Serializable {
    private static final long serialVersionUID = -238029134014283972L;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("角色")
    private String roles;
}
