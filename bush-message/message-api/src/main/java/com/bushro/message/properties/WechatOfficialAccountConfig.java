package com.bushro.message.properties;

import lombok.*;
import com.bushro.message.base.Config;

/**
 * 微信公众号配置
 *
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WechatOfficialAccountConfig extends Config {
    private static final long serialVersionUID = -9206902816158196669L;
    /**
     * AppID
     */
    private String appId;
    /**
     * 密钥，在公众号开发者那边可以查看
     */
    private String secret;

}
