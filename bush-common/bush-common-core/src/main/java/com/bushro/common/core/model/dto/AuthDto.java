package com.bushro.common.core.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * auth dto
 *
 * @author bushro
 * @date 2022/11/16
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    @ApiModelProperty(value = "Oauth2客户端ID", required = true, example = "web")
    private String client_id;

    @ApiModelProperty(value = "Oauth2客户端秘钥", required = true, example = "123456")
    private String client_secret;

    @ApiModelProperty(value = "授权模式", required = true, example = "password")
    private String grant_type;

    @ApiModelProperty(value = "刷新token")
    private String refresh_token;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "用户密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "验证码模式所需code", example = "666")
    private String code;

    @ApiModelProperty(value = "uuid")
    private String uuid;
}
